package com.board.domain.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.cart.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByMemberUsername(String username);
}
