package com.talia.coupons.exceptions;

import com.talia.coupons.enums.ErrorType;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	private ErrorType errorType;

	public ApplicationException(Exception innerException, ErrorType errorType, String message) {
		super(message, innerException);
		this.errorType = errorType;
	}

	public ApplicationException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return this.errorType;
	}
}

