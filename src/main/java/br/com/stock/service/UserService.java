package br.com.stock.service;


import br.com.stock.entity.Permission;
import br.com.stock.entity.User;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.PaginatedData;
import br.com.stock.model.Pagination;
import br.com.stock.repository.UserRepository;
import br.com.stock.security.jwt.JwtResponse;
import br.com.stock.security.jwt.JwtUtils;
import br.com.stock.security.services.UserDetailsImpl;
import br.com.stock.service.criteria.UserCriteria;
import br.com.stock.util.Constants;
import br.com.stock.util.MsgSystem;
import br.com.stock.util.Permissions;
import br.com.stock.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public ApiResponse<User> saveUser(User user){
		return verification(user, HttpStatus.CREATED);
	}

	public ApiResponse<User> updateUser(User user){
		return verification(user, HttpStatus.OK);
	}

	public ApiResponse<PaginatedData<User>> userList(Pageable pageable, UserCriteria criteria) {
		ApiResponse<PaginatedData<User>> response = new ApiResponse<>();

		pageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.ASC, "username", "permission")
		);
		Page<User> efforts = repository.findAll(createSpecification(criteria), pageable);

		final Pagination pagination = Pagination.from(efforts, pageable);

		return response.of(HttpStatus.OK, MsgSystem.sucGet("Lista de usuários"), new PaginatedData<>(efforts.getContent(), pagination));
	}


	public ApiResponse<JwtResponse> login(User loginRequest) {
		ApiResponse<JwtResponse> userApiResponse = new ApiResponse<>();
		Optional<User> user = repository.findUserByUsername(loginRequest.getUsername());

		if (user.isEmpty())
			return userApiResponse.of(HttpStatus.BAD_REQUEST, MsgSystem.errLogin());

		try {
			Authentication authentication =
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(
									loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			return userApiResponse.of(HttpStatus.OK, tokenResponse(authentication, user.get()));
		} catch (Exception e) {
			return userApiResponse.of(HttpStatus.BAD_REQUEST, MsgSystem.errLogin());
		}

	}


	public ApiResponse<User> getUserLogged() {
		ApiResponse<User> response = new ApiResponse<>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("ROLE_ANONYMOUS")){
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			response.of(HttpStatus.OK, "Usuário logado encontrado com sucesso", repository.findUserByUsername(userDetails.getUsername()).get());
			return response;
		}
		return response.of(HttpStatus.NOT_FOUND, "Usuário não está logado");
	}

	private Specification<User> createSpecification(UserCriteria criteria){
		Specification<User> specification = Specification.where(null);

		if (criteria.getKeyword() != null)
			specification = specification.and(UserCriteria.filterByUsername(criteria.getKeyword()));

		if (criteria.getPermissionId() != null)
			specification = specification.and(UserCriteria.filterByPermission(criteria.getPermissionId()));

		return specification;
	}


	public ApiResponse<User> verification(User user, HttpStatus status) {
		Permission permissionLogged = getUserLogged().getData().getPermission();
		ApiResponse<User> response = new ApiResponse<>();
		if(!permissionLogged.getName().equals(Permissions.ADMINISTRADOR))
			return response.of(HttpStatus.BAD_REQUEST,
					"Usuário "+user.getUsername()+" não possui permissão!");
		String msgSuc = MsgSystem.sucCreate(Constants.USUARIO);

		Object[][] args = {
				{user.getUsername(), "Usuário"},
				{user.getPermission(), "Permissão"},
				{user.getPassword(), "Senha"},
		};
		String msgErr = Validation.isEmptyFields(args);
		if(!msgErr.isEmpty())
			return response.of(HttpStatus.BAD_REQUEST, msgErr);
		if (repository.findUserByUsername(user.getUsername()).isPresent())
			return response.of(HttpStatus.BAD_REQUEST,
					"Usuário "+user.getUsername()+" já encontra-se cadastrado!");

		if (status == HttpStatus.OK)
			if (Validation.isEmptyOrNull(user.getId()) || !repository.existsById(user.getId()))
				return response.of(HttpStatus.NOT_FOUND,
						msgSuc = MsgSystem.sucCreate(Constants.USUARIO));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User userSaved = repository.save(user);
		return response.of(status, msgSuc, userSaved);
	}


	private JwtResponse tokenResponse(Authentication authentication, User user) {
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return new JwtResponse(
				jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				user.getPermission()
		);
	}
}
