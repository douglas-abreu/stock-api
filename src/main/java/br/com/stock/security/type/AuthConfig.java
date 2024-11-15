package br.com.stock.security.type;

import lombok.Getter;

@Getter
public enum AuthConfig {
	SECRET_KEY("UUtYGqgzPAoSZgFDIjNFdNky3l23XAZZ"),
	EXPIRE_TIME("14400000");
	
	private final String config;

	AuthConfig(String config) {
		 this.config = config;
	}

}
