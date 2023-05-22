package com.board.domain.item.dto;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.item.Item;
import com.board.domain.member.dto.MemberInfoDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSaveDto {
	private Long id;
	private String itemname;
	private Long price;
	private Long stock;
	private String isSoldout;
	private String detail;
	private MultipartFile[] files; 
	private MemberInfoDto seller;
	
	public Item toEntity() {
		return Item.builder().itemname(itemname).detail(detail).price(price).stock(stock).build();
	}
}
