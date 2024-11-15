package br.com.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private Integer status = HttpStatus.OK.value();
	private String message = "";
	private T data = null;
	@JsonIgnore
	private HttpHeaders headers = new HttpHeaders();
	
	@Builder
	public ApiResponse(Integer status, String message, T data, HttpHeaders headers ){
		this.status = status;
		this.message = message;
		this.data = data;
		this.headers = headers;
	}
}
