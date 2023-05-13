package com.board.domain.cart.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class CartException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BaseExceptionType baseExceptionType;
	public CartException(BaseExceptionType baseExceptionType) {
		this.baseExceptionType = baseExceptionType;		
	}
	
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return baseExceptionType;
	}

}
