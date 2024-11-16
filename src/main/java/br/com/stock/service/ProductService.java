package br.com.stock.service;


import br.com.stock.entity.Product;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.PaginatedData;
import br.com.stock.model.Pagination;
import br.com.stock.repository.ProductRepository;
import br.com.stock.service.criteria.ProductCriteria;
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
public class ProductService {

	private final ProductRepository repository;

	public ApiResponse<Product> saveProduct(Product product){
		return verification(product, HttpStatus.CREATED);
	}

	public ApiResponse<Product> updateProduct(Product product){
		return verification(product, HttpStatus.OK);
	}

	public ApiResponse<PaginatedData<Product>> productList(Pageable pageable, ProductCriteria criteria) {
		ApiResponse<PaginatedData<Product>> response = new ApiResponse<>();

		pageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.ASC, "name")
		);
		Page<Product> products = repository.findAll(createSpecification(criteria), pageable);

		final Pagination pagination = Pagination.from(products, pageable);

		return response.of(HttpStatus.OK, MsgSystem.sucGet("Lista de produtos"), new PaginatedData<>(products.getContent(), pagination));
	}

	public ApiResponse<Product> deleteProduct(Integer id){
		ApiResponse<Product> response = new ApiResponse<>();
		repository.findById(id).ifPresentOrElse(
				it -> {
					response.of(HttpStatus.OK, MsgSystem.sucDelete(Constants.PRODUTO));
					repository.deleteById(id);
				},
				() -> response.of(HttpStatus.NOT_FOUND, MsgSystem.errGet(Constants.PRODUTO))
		);

		return response;
	}


	private Specification<Product> createSpecification(ProductCriteria criteria){
		Specification<Product> specification = Specification.where(null);

		if (criteria.getKeyword() != null)
			specification = specification
					.and(ProductCriteria.filterByName(criteria.getKeyword()))
					.or(ProductCriteria.filterByDescription(criteria.getKeyword()))
					.or(ProductCriteria.filterByBarCode(criteria.getKeyword()));

		if (criteria.getCategoryId() != null)
			specification = specification.and(ProductCriteria.filterByCategoryId(criteria.getCategoryId()));


		return specification;
	}


	public ApiResponse<Product> verification(Product product, HttpStatus status) {
		ApiResponse<Product> response = new ApiResponse<>();
		String msgSuc = MsgSystem.sucCreate(Constants.PRODUTO);

		Object[][] args = {
				{product.getName(), "Nome"},
				{product.getBarCode(), "Código de barra"},
				{product.getCategory(), "Categoria"},
		};
		String msgErr = Validation.isEmptyFields(args);
		if(!msgErr.isEmpty())
			return response.of(HttpStatus.BAD_REQUEST, msgErr);


		if (status == HttpStatus.OK) {
			msgSuc = MsgSystem.sucUpdate(Constants.PRODUTO);
			if (Validation.isEmptyOrNull(product.getId()) || !repository.existsById(product.getId()))
				return response.of(HttpStatus.NOT_FOUND,
						msgSuc = MsgSystem.sucUpdate(Constants.PRODUTO));
		} else {
			if (repository.findProductByBarCode(product.getBarCode()).isPresent())
				return response.of(HttpStatus.BAD_REQUEST,
						"Produto com código de barra já encontra-se cadastrado!");
		}
		Product productSaved = repository.save(product);
		return response.of(status, msgSuc, productSaved);
	}

	
}
