package com.board.domain.address.exception;

import com.board.domain.BaseTimeEntity;
import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class AddressException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BaseExceptionType baseExceptionType;
	
	public AddressException(BaseExceptionType baseExceptionType) {
		this.baseExceptionType = baseExceptionType;
	}
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return baseExceptionType;
	}

}
