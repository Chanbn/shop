package com.board.domain.item.dto;

import lombok.Data;

@Data
public class LastPageDto {
	int pageNum;
	
	public LastPageDto(){
		pageNum=0;
	}
	
	public void recentPageSet(int pageNum) {
		this.pageNum = pageNum;
	}
}
