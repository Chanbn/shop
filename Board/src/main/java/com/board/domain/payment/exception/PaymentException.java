package com.board.domain.payment.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class PaymentException extends BaseException {

	private final BaseExceptionType baseExceptionType;
	
	public PaymentException(BaseExceptionType baseExceptionType){
		this.baseExceptionType = baseExceptionType;
	}
	
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return baseExceptionType;
	}

}
