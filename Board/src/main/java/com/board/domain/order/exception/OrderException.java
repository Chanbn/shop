package com.board.domain.order.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class OrderException extends BaseException {

	private final BaseExceptionType baseExceptionType;
	
	public OrderException(BaseExceptionType baseExceptionType){
		this.baseExceptionType = baseExceptionType;
	}
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return baseExceptionType;
	}

}
