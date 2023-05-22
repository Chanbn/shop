package com.board.domain.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    private List<OrderInfo> orderInfo;
    private AddressDto addressDto;
}
