package com.board.domain.order.dto;

import java.time.LocalDateTime;

import com.board.domain.order.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfo {

	private Long itemId;
	private String itemname;
	private Long price;
	private Long count;
	private String fileName;
	private String createdDate;
	private Long stock;
	
	public OrderInfo(Long itemId, String itemname, Long price, Long count, String fileName, String createdDate, Long stock) {
		this.itemId = itemId;
		this.itemname = itemname;
		this.price = price;
		this.count = count;
		this.fileName = fileName;
		this.createdDate = createdDate;
		this.stock = stock;
	}
}
