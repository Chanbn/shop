package com.board.domain.cartitem.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class CartItemException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BaseExceptionType baseExceptionType;
	public CartItemException(BaseExceptionType baseExceptionType)
	{
		this.baseExceptionType = baseExceptionType;
	}
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return baseExceptionType;
	}

}
