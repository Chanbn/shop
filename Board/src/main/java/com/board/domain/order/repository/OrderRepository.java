package com.board.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	Order findByIdAndMemberUsername(Long id, String username);
	
	@Query("SELECT i.orderPrice FROM OrderItem i WHERE i.order.id=:id")
	List<Long> getPrice(@Param("id") Long id);
	
	@Query("SELECT i FROM Order i WHERE i.member.id=:userId")
	List<Order> getOrderList(@Param("userId") Long userId);
}
