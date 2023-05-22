package com.board.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.board.domain.address.Address;
import com.board.domain.order.OrderItem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResultDto {

	private Long id;
	private List<OrderItem> orderItems;
	private String deliveryAddress;
	private String deliveryHp;
	private String deliveryName;

	@Builder
	public OrderResultDto(Long id, List<OrderItem> orderItems, String deliveryAddress, String deliveryHp, String deliveryName){
		this.id = id;
		this.orderItems = orderItems;
		this.deliveryAddress = deliveryAddress;
		this.deliveryHp = deliveryHp;
		this.deliveryName = deliveryName;
	}
}
