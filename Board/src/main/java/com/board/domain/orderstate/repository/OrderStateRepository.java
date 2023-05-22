package com.board.domain.orderstate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.orderstate.OrderState;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {

	List<OrderState> findAllByOrderId(Long id);
}
