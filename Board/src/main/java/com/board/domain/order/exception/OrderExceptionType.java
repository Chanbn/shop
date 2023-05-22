package com.board.domain.order.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum OrderExceptionType implements BaseExceptionType {
	WRONG_ORDER(1001,HttpStatus.NOT_FOUND, "주문 내역이 존재하지 않습니다.");
	
	int errorCode;
	HttpStatus httpStatus;
	String errorMessage;
	
	OrderExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage){
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
