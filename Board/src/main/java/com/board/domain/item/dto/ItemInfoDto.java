package com.board.domain.item.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.board.domain.item.Item;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.file.Item.itemFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInfoDto {

	private Long id;
	private String name;
	private Long price;
	private Long stock;
	private String isSoldout;
	private String detail;
	private List<itemFile> fileLists = new ArrayList<>();
	private MemberInfoDto seller;
	private LocalDateTime createdDate;
	
	
	ItemInfoDto(){
		
	}
	
	public ItemInfoDto(Item item,List<itemFile> fileLists) {
		this.id = item.getId();
		this.name = item.getName();
		this.price = item.getPrice();
		this.stock = item.getStock();
		this.isSoldout = item.getIsSoldOut();
		this.detail = item.getDetail();
		this.seller = new MemberInfoDto(item.getSeller());
		this.fileLists= fileLists;
	}

}
