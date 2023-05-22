package com.board.domain.address.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum AddressExceptionType implements BaseExceptionType{
	WRONG_ORDER(1002,HttpStatus.NOT_FOUND, ".");
	int errorCode;
	HttpStatus httpStatus;
	String errorMessage;
	
	AddressExceptionType(int errorCode, HttpStatus httpStatus,String errorMessage) {
		// TODO Auto-generated constructor stub
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public int getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

	@Override
	public HttpStatus getHttpStatus() {
		// TODO Auto-generated method stub
		return httpStatus;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

}
