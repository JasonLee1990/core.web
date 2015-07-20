package com.lee.core.web.mvc;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable throwable) {
		super(throwable);
	}
	
	public AppException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
