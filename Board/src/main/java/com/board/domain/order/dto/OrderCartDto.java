package com.board.domain.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCartDto {
	private List<OrderInfo> orderInfo;
}
