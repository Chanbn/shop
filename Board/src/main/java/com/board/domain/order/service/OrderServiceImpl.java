package com.board.domain.order.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.board.domain.address.Address;
import com.board.domain.address.repository.AddressRepository;
import com.board.domain.item.Item;
import com.board.domain.item.repository.ItemRepository;
import com.board.domain.member.Member;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.order.Order;
import com.board.domain.order.OrderItem;
import com.board.domain.order.dto.MyOrderDto;
import com.board.domain.order.dto.MyOrderListDto;
import com.board.domain.order.dto.OrderDto;
import com.board.domain.order.dto.OrderInfo;
import com.board.domain.order.dto.OrderResultDto;
import com.board.domain.order.exception.OrderException;
import com.board.domain.order.exception.OrderExceptionType;
import com.board.domain.order.repository.OrderRepository;
import com.board.domain.orderstate.service.OrderStateService;
import com.board.domain.payment.PaymentsInfo;
import com.board.domain.payment.repository.PaymentRepository;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

	private final OrderStateService orderStateService;
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final PaymentRepository paymentRepository;
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
													.orderId(order.getId())
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
	@Override
	public List<MyOrderListDto> myOrderList(String username) {
		// TODO Auto-generated method stub
		if(username==null) {
			throw new MemberException(MemberExceptionType.WRONG_USER);
		}
		Long userId = memberRepository.findByUsername(username).get().getId();
		
		List<Order> order = orderRepository.getOrderList(userId);

		List<MyOrderListDto> dtoList = new ArrayList<>();
		for(Order orderIdx : order) {
		       List<MyOrderDto> orderList = new ArrayList<>(); 
			for(OrderItem orderItem:orderIdx.getOrderItems()){
				MyOrderDto dto = new MyOrderDto(orderItem.getItem().getId(),orderItem.getItem().getItemname(),orderItem.getItem().getPrice(),orderItem.getCount(),orderItem.getFileName(),orderItem.getCreatedDate().toString(),orderIdx.getCreatedDate().toString());
				orderList.add(dto);

				}
			log.info("orderIde ??"+orderIdx.getId());
			PaymentsInfo payInfo = paymentRepository.findByOrderId(orderIdx.getId());
			MyOrderListDto temp= new MyOrderListDto();
			if(payInfo!=null){
			temp.setAmount(payInfo.getAmount());}
			else {
				temp.setAmount(0L);
			}
			temp.setOrderId(orderIdx.getId());
			temp.setMyOrder(orderList);
			dtoList.add(temp);
		}
		return dtoList;
	}

}
