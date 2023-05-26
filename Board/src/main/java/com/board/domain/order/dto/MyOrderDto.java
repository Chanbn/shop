package com.board.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyOrderDto {

	private Long itemId;
	private String itemName;
	private Long price;
	private Long count;
	private String fileName;
	private String fileCreatedDate;
	private String orderCreatedDate;
	
	public MyOrderDto(Long itemId, String itemName,Long price,
			Long count, String fileName,String fileCreatedDate,String orderCreatedDate) {
		this.itemId = itemId;
		this.itemName =itemName;
		this.price = price;
		this.count = count;
		this.fileName = fileName;
		this.fileCreatedDate = fileCreatedDate;
		this.orderCreatedDate = orderCreatedDate;
	}
}
