package com.codejava.blog.exceptions;

public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 69591217735936340L;

	public ApiException(String message) {
		super(message);

	}

	public ApiException() {
		super();

	}

}
