package com.board.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.board.domain.address.Address;
import com.board.domain.address.repository.AddressRepository;
import com.board.domain.item.Item;
import com.board.domain.item.exception.ItemException;
import com.board.domain.item.exception.ItemExceptionType;
import com.board.domain.item.repository.ItemRepository;
import com.board.domain.item.service.ItemServiceImpl;
import com.board.domain.member.Member;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.member.service.MemberService;
import com.board.domain.order.Order;
import com.board.domain.order.OrderItem;
import com.board.domain.order.dto.OrderDto;
import com.board.domain.order.dto.OrderItemDto;
import com.board.domain.order.dto.OrderResultDto;
import com.board.domain.order.exception.OrderException;
import com.board.domain.order.exception.OrderExceptionType;
import com.board.domain.order.repository.OrderRepository;
import com.board.domain.orderstate.repository.OrderStateRepository;
import com.board.domain.orderstate.service.OrderStateService;
import com.board.file.Item.service.itemFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

	private final OrderStateService orderStateService;
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final AddressRepository deliveryAddressRepository;
	@Override
	public Order orderItemList(List<OrderDto> items,String username) {
		// TODO Auto-generated method stub
		 //주문 접수, 구매자요청
		Member member = memberRepository.findByUsername(username).orElseThrow(()->new MemberException(MemberExceptionType.WRONG_USER));
		Address deliveryAddress = Address.builder().member(member).name(items.get(0).getName()).address(items.get(0).getAddress()).hp(items.get(0).getHp()).build();
		List<OrderItem> itemList = new ArrayList<>();
		for(OrderDto order:items) {
			Item item = itemRepository.findById(order.getItemId()).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
			OrderItem orderItem = OrderItem.createOrderItem(item, order.getCount());
			itemList.add(orderItem);
		}
		Order order = Order.createOrder(member, itemList,deliveryAddress); 

//    	orderStateService.OrderReceived(order.getId());

		return orderRepository.save(order);
	}
	@Override
	public Order orderItem(OrderDto items, String username) {
		// TODO Auto-generated method stub
		Member member = memberRepository.findByUsername(username).orElseThrow(()->new MemberException(MemberExceptionType.WRONG_USER));
		Item item = itemRepository.findById(items.getItemId()).orElseThrow(()->new OrderException(OrderExceptionType.WRONG_ORDER));
		OrderItem orderItem = OrderItem.createOrderItem(item, items.getCount());
		Order order = Order.createOrder(member, orderItem); 
		orderStateService.OrderReceived(order.getId());
		return orderRepository.save(order);
	}
	@Override
	public OrderResultDto getOrder(Long id, String username) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findByIdAndMemberUsername(id, username);
		OrderResultDto orderResultDto = OrderResultDto.builder()
													.deliveryAddress(order.getDeliveryAddress())
													.deliveryHp(order.getDeliveryHp())
													.deliveryName(order.getDeliveryName())
													.id(order.getId())
													.orderItems(order.getOrderItems())
													.build();
		return orderResultDto;
	}
	@Override
	public Long getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		Long totalPrice = 0L;
			List<Long> priceList = orderRepository.getPrice(id);			
			for(Long price:priceList) {
				totalPrice+=price;
			}
		return totalPrice;
	}

}
