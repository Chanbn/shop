package com.board.domain.cart.service;

import java.util.List;
import java.util.Set;

import com.board.domain.cartitem.CartItem;
import com.board.domain.item.Item;

public interface CartService {
	List<CartItem> getList();
}
