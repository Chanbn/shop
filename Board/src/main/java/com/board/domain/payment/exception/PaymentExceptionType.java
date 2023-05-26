package com.board.domain.payment.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum PaymentExceptionType implements BaseExceptionType {
	WRONG_PAYINFO(1111,HttpStatus.BAD_REQUEST, "주문 금액이 일치하지 않습니다."),
	WRONG_REFUND(1112,HttpStatus.BAD_REQUEST, "환불 금액이 일치하지 않습니다.");
	
	int errorCode;
	HttpStatus httpStatus;
	String errorMessage;
	
	PaymentExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage){
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
