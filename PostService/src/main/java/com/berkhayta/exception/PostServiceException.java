package com.berkhayta.exception;

import lombok.Getter;

@Getter
public class PostServiceException extends RuntimeException{
	private ErrorType errorType;
	
	public PostServiceException(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
	}
	
	public PostServiceException(ErrorType errorType, String customMessage) {
		super(customMessage);
		this.errorType = errorType;
	}
}