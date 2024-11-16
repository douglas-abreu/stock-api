package br.com.stock.service;


import br.com.stock.entity.Category;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.PaginatedData;
import br.com.stock.model.Pagination;
import br.com.stock.repository.CategoryRepository;
import br.com.stock.service.criteria.CategoryCriteria;
import br.com.stock.util.Constants;
import br.com.stock.util.MsgSystem;
import br.com.stock.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;

	public ApiResponse<Category> saveCategory(Category category){
		return verification(category, HttpStatus.CREATED);
	}

	public ApiResponse<Category> updateCategory(Category category){
		return verification(category, HttpStatus.OK);
	}

	public ApiResponse<PaginatedData<Category>> categoryList(Pageable pageable, CategoryCriteria criteria) {
		ApiResponse<PaginatedData<Category>> response = new ApiResponse<>();

		pageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.ASC, "name")
		);
		Page<Category> efforts = repository.findAll(createSpecification(criteria), pageable);

		final Pagination pagination = Pagination.from(efforts, pageable);

		return response.of(HttpStatus.OK, MsgSystem.sucGet("Lista de categorias"), new PaginatedData<>(efforts.getContent(), pagination));
	}

	public ApiResponse<Category> deleteCategory(Integer id){
		ApiResponse<Category> response = new ApiResponse<>();
		repository.findById(id).ifPresentOrElse(
				it -> {
					response.of(HttpStatus.OK, MsgSystem.sucDelete(Constants.CATEGORIA));
					repository.deleteById(id);
				},
				() -> response.of(HttpStatus.NOT_FOUND, MsgSystem.errGet(Constants.CATEGORIA))
		);

		return response;
	}


	private Specification<Category> createSpecification(CategoryCriteria criteria){
		Specification<Category> specification = Specification.where(null);

		if (criteria.getKeyword() != null)
			specification = specification.and(CategoryCriteria.filterByName(criteria.getKeyword()));

		return specification;
	}


	public ApiResponse<Category> verification(Category category, HttpStatus status) {
		ApiResponse<Category> response = new ApiResponse<>();
		String msgSuc = MsgSystem.sucCreate(Constants.CATEGORIA);

		Object[][] args = {
				{category.getName(), "Nome"},
		};
		String msgErr = Validation.isEmptyFields(args);
		if(!msgErr.isEmpty())
			return response.of(HttpStatus.BAD_REQUEST, msgErr);

		if (status == HttpStatus.OK) {
			msgSuc = MsgSystem.sucUpdate(Constants.CATEGORIA);
			if (Validation.isEmptyOrNull(category.getId()) || !repository.existsById(category.getId()))
				return response.of(HttpStatus.NOT_FOUND, msgSuc = MsgSystem.errGet(Constants.CATEGORIA));
		}

		Category categorySaved = repository.save(category);
		return response.of(status, msgSuc, categorySaved);
	}

	
}
