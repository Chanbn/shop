package com.board.domain.cartitem.service;

import com.board.domain.cart.Cart;
import com.board.domain.cartitem.CartItem;
import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberSessionDto;

public interface CartItemService {

	CartItem addItem(Long itemId,int count, MemberSessionDto memberDto);
	
	CartItem updateItem(Long id, int count, MemberSessionDto memberDto);
	
	void removeItem(Long id);
	void removeAllCartItem(MemberSessionDto memberDto);
}
