package com.board.domain.payment.dto;

import java.util.List;

import com.board.domain.order.dto.AddressDto;
import com.board.domain.order.dto.OrderInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestPayDto {
    private List<OrderInfo> orderInfo;
    private String imp_uid;
    private Long amount;
    private Long orderId;
}
