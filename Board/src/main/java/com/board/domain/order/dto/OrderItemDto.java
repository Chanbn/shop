package com.board.domain.order.dto;

import com.board.domain.order.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String itemName; //상품명
    private Long count; //주문 수량

    private Long orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로

    public OrderItemDto(OrderItem orderItem, String imgUrl){ // 주문상품, 이미지경로를 파라미터로 받음
        this.itemName = orderItem.getItem().getItemname();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
