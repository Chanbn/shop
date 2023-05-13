package com.board.domain.cartitem.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum CartItemExceptionType implements BaseExceptionType{
	WRONG_CARTITEM(951,HttpStatus.NOT_FOUND, "장바구니에 존재하지 않는 상품입니다.");
	
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;
	
	CartItemExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage){
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
