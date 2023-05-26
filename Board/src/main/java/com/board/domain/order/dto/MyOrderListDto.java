package com.board.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.board.domain.order.OrderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class MyOrderListDto {
	private Long orderId;
	private Long amount;
	private List<MyOrderDto> myOrder;
	
	
	public MyOrderListDto(List<OrderItem> itemList) {

		List<MyOrderDto> orderList = itemList.stream()
				.map(index->
				new MyOrderDto(index.getItem().getId(),
						index.getItem().getItemname(),index.getItem().getPrice(),index.getCount(),
						index.getItem().getFileLists().get(0).getSaveName(),
						index.getItem().getFileLists().get(0).getCreatedDate().toString(),
						index.getCreatedDate().toString()))
				.collect(Collectors.toList());
		this.myOrder = orderList;
	}
}
