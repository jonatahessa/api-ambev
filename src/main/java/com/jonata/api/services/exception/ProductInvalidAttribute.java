package com.jonata.api.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductInvalidAttribute extends Exception {
	private static final long serialVersionUID = 1L;

	public ProductInvalidAttribute(String msg) {
		super(msg);
	}
}