package com.board.domain.orderstate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.cart.repository.CartRepository;
import com.board.domain.cartitem.repository.CartItemRepository;
import com.board.domain.cartitem.service.CartItemServiceImpl;
import com.board.domain.item.Item;
import com.board.domain.item.exception.ItemException;
import com.board.domain.item.exception.ItemExceptionType;
import com.board.domain.item.repository.ItemRepository;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.order.Order;
import com.board.domain.order.OrderItem;
import com.board.domain.order.exception.OrderException;
import com.board.domain.order.exception.OrderExceptionType;
import com.board.domain.order.repository.OrderRepository;
import com.board.domain.orderstate.OrderState;
import com.board.domain.orderstate.State;
import com.board.domain.orderstate.repository.OrderStateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderStateServiceImpl implements OrderStateService {
	private final OrderRepository orderRepository;
	private final OrderStateRepository orderStateRepository;
	private final ItemRepository itemRepository;
	@Override
	public OrderState OrderReceived(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));

		
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.OrderReceived).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState PaymentCompleted(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		
		
		List<OrderItem> orderItemList = order.getOrderItems();
		
		for(OrderItem orderList : orderItemList) {
			Long itemId = orderList.getItem().getId();
			Long count = orderList.getCount();
			Item item =  itemRepository.findById(itemId).orElseThrow(()->new ItemException(ItemExceptionType.WRONG_ITEM));
			orderList.removeItemStock(item, count);
		}
		
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.PaymentCompleted).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState InPreparation(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.InPreparation).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState OnDelivery(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.OnDelivery).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState Delivered(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.Delivered).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState CancelRequested(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.CancelRequested).build();
		orderStateRepository.save(orderState);
		return orderState;
	}
	
	@Override
	public OrderState CancelCompleted(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.CancelCompleted).build();
		orderStateRepository.save(orderState);
		return orderState;
	}
	
	@Override
	public OrderState ReturnRequested(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.ReturnRequested).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState ReturnCompleted(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.ReturnCompleted).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public OrderState RefundCompleted(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderState orderState = OrderState.builder().progressDate(LocalDateTime.now()).order(order).state(State.RefundCompleted).build();
		orderStateRepository.save(orderState);
		return orderState;
	}

	@Override
	public List<OrderState> getOrderStateList(Long orderId) {
		// TODO Auto-generated method stub
		List<OrderState> orderStateList=orderStateRepository.findAllByOrderId(orderId);
		return orderStateList;
	}



}
