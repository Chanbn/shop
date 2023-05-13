package com.board.domain.cartitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.cartitem.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByIdAndCartId(Long id, Long CartId);
}
