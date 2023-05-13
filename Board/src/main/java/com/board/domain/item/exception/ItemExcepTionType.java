package com.board.domain.item.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum ItemExceptionType implements BaseExceptionType {
	WRONG_ITEM(801,HttpStatus.NOT_FOUND, "존재하지 않는 아이템입니다.");
	
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;

	ItemExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage){
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
