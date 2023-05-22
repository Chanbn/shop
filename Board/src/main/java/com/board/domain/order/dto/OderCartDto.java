package com.board.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OderCartDto {
	private List<OrderInfo> orderInfo;
}
