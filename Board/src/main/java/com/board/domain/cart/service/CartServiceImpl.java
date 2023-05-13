package com.board.domain.cart.service;

import java.util.List;
import java.util.Set;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.cart.repository.CartRepository;
import com.board.domain.cartitem.CartItem;
import com.board.domain.cartitem.service.CartItemServiceImpl;
import com.board.domain.item.Item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService{
	private final CartRepository cartRepository;
	@Override
	public List<CartItem> getList() {
		// TODO Auto-generated method stub
		List<CartItem> item = cartRepository.findAll().stream().flatMap(cart->cart.getWishList().stream()).collect(Collectors.toList());
		return item;
	}

}
