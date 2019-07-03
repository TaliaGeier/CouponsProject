package com.talia.coupons.enums;

public enum ErrorType {
	GENERAL_ERROR(600, "General error"),
	INSERTION_ERROR(601, "Failed to insert to the database"),
	DELETE_ERROR(602, "Failed to delete"), 
	UPDATE_ERROR(603, "Failed to update data"),
	READ_ERROR(604, "Failed to read data from database"), 
	QUERY_ERROR(605, "Failed to perform query"),
	LOGIN_FAILED(606, "Login has failed"), 
	INVALID_INPUT(607, "User entered invalid input"),
	DUPLICATED_ENTRY(608, "input already exists");

	private int internalErrorCode;
	private String internalMessage;

	private ErrorType(int internalErrorCode, String internalMessage) {
		this.internalErrorCode = internalErrorCode;
		this.internalMessage = internalMessage;
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

}