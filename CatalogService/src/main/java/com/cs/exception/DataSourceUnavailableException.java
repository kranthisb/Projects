package com.cs.exception;

public class DataSourceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataSourceUnavailableException(String message) {
		super(message);
	}
}
