package com.board.domain.order.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfo {

	private Long id;
	private String itemname;
	private Long price;
	private Long count;
	private String fileName;
	private String createdDate;
	private Long stock;
	
	public OrderInfo(Long id, String itemname, Long price, Long count, String fileName, String createdDate, Long stock) {
		this.id = id;
		this.itemname = itemname;
		this.price = price;
		this.count = count;
		this.fileName = fileName;
		this.createdDate = createdDate;
		this.stock = stock;
	}
}
