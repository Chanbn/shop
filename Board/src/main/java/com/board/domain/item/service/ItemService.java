package com.board.domain.item.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.board.domain.item.Item;
import com.board.domain.item.dto.ItemInfoDto;
import com.board.domain.item.dto.ItemSaveDto;

public interface ItemService {

	ItemInfoDto getInfo(Long id); 
	Page<ItemInfoDto> getList(Pageable pageable, String type,String word);
	Item saveItem(ItemSaveDto itemSaveDto,List<Long> existIdx);
}
