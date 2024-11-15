package br.com.stock.service;

import br.com.stock.exceptions.RequestLoginException;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.JWTResponse;
import br.com.stock.model.RequestLogin;
import br.com.stock.security.jwt.JwtUtils;
import br.com.stock.security.type.AuthConfig;
import br.com.stock.validation.AuthValidations;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import static br.com.stock.validation.AuthValidations.*;


@Data
@RequiredArgsConstructor
@Service
public class UserService {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private static AuthValidations authValidations;

	public ApiResponse<JWTResponse> authenticateUser(RequestLogin requestLogin)
			throws RequestLoginException {

		validateRequestLogin(requestLogin);

		String jwt = jwtUtils.generateJwtTokenWithUsername(requestLogin.getUsername());
		JWTResponse.JWTResponseBuilder builder = partialJWTResponseBuilder(requestLogin);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer " + jwt);
		return ApiResponse.<JWTResponse>builder().status(HttpStatus.OK.value()).message("Usuário logado com sucesso.")
				.headers(responseHeaders).data(builder.authenticated(true).build()).build();

	}

	public ApiResponse<JWTResponse> getUserLogged(String token)
			throws RequestLoginException, JWTDecodeException {

		RequestLogin requestLogin = new RequestLogin();

		validateToken(token);

		requestLogin.setUsername(jwtUtils.validateJwtToken(token));
		JWTResponse.JWTResponseBuilder builder = partialJWTResponseBuilder(requestLogin);
		return ApiResponse.<JWTResponse>builder().status(HttpStatus.IM_USED.value())
				.message("Usuário logado encontrado!").data(builder.authenticated(true).build()).build();
	}

	public ApiResponse<Boolean> revokeToken() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "bearer ");
		return ApiResponse.<Boolean>builder().status(HttpStatus.OK.value()).headers(responseHeaders).data(true)
				.message("Usuário deslogado com sucesso!").build();
	}

	private JWTResponse.JWTResponseBuilder partialJWTResponseBuilder(RequestLogin requestLogin) {
		return JWTResponse.builder().login(requestLogin.getUsername());
	}




}
