package com.board.domain.order.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 999, message = "최대 주문 수량은 999개 입니다.")
    private Long count;
    private String itemname;
    
    private String name;
    private String address;
    private String hp;
    
    private String fileName;
    private String createdDate;
    
    
    
    @Builder
    public OrderDto(AddressDto addressDto, OrderInfo orderInfo) {
    	this.itemId = orderInfo.getItemId();
    	this.count = orderInfo.getCount();
    	this.name = addressDto.getName();
    	this.address = addressDto.getAddress();
    	this.hp = addressDto.getHp();
    	this.fileName = orderInfo.getFileName();
    	this.createdDate = orderInfo.getCreatedDate();
    	this.itemname = orderInfo.getItemname();
    }

}
