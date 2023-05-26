package com.board.domain.cartitem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.cartitem.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByIdAndCartId(Long id, Long CartId);
	CartItem findByItemIdAndCartId(Long id, Long CartId);
	
}
