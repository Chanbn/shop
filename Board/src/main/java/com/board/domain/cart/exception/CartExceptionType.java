package com.board.domain.cart.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum CartExceptionType implements BaseExceptionType{
	WRONG_CART(901,HttpStatus.NOT_FOUND, "존재하지 않는 장바구니입니다.");
	
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;
	
	CartExceptionType(int errorCdoe, HttpStatus httpStatus, String errorMessage)
	{
		this.errorCode = errorCdoe;
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
