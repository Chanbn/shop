package com.board.domain.order.service;

import java.util.List;

import com.board.domain.order.Order;
import com.board.domain.order.OrderItem;
import com.board.domain.order.dto.MyOrderDto;
import com.board.domain.order.dto.MyOrderListDto;
import com.board.domain.order.dto.OrderDto;
import com.board.domain.order.dto.OrderResultDto;

public interface OrderService {
	Order orderItem(OrderDto items,String username);
	Order orderItemList(List<OrderDto> items,String username);
	OrderResultDto getOrder(Long id, String username);
	Long getTotalPrice(Long id);
	List<MyOrderListDto> myOrderList(String username);
}
