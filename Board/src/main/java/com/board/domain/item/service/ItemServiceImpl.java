package com.board.domain.item.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.board.domain.item.Item;
import com.board.domain.item.dto.ItemInfoDto;
import com.board.domain.item.dto.ItemSaveDto;
import com.board.domain.item.exception.ItemExcepTionType;
import com.board.domain.item.exception.ItemException;
import com.board.domain.item.repository.ItemRepository;
import com.board.domain.member.Member;
import com.board.domain.member.exception.MemberException;
import com.board.domain.member.exception.MemberExceptionType;
import com.board.domain.member.repository.MemberRepository;
import com.board.file.Item.itemFile;
import com.board.file.Item.dto.FileDto;
import com.board.file.Item.service.itemFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final itemFileService fileService;
	@Override
	public ItemInfoDto getInfo(Long id) {
		// TODO Auto-generated method stub
		Item item = itemRepository.getInfo(id).orElseThrow(()->new ItemException(ItemExcepTionType.WRONG_ITEM));
		ItemInfoDto itemInfo = new ItemInfoDto(item,item.getFileLists());
		return itemInfo;
	}
	@Override
	public Page<ItemInfoDto> getList(Pageable pageable, String type, String word) {
		// TODO Auto-generated method stub
		Page<ItemInfoDto> pageList = null;
		switch (type) {
		case "seller":
			pageList = itemRepository.findBySellerAndIsSoldOut(pageable,word,"N").map(item -> new ItemInfoDto(item,item.getFileLists()));
			break;
		case "name":
			pageList = itemRepository.findByNameAndIsSoldOut(pageable,word,"N").map(item -> new ItemInfoDto(item,item.getFileLists()));
			break;
		default:
			pageList = itemRepository.findAllByIsSoldOut(pageable,"N").map(item -> new ItemInfoDto(item,item.getFileLists()));
			break;
		}
		return pageList;
	}
	
	@Override
	public Item saveItem(ItemSaveDto itemSaveDto,List<Long> existIdx) {
		// TODO Auto-generated method stub
		Item item;
		if(itemSaveDto.getId()!=null) {
		    Item existingItem = itemRepository.findById(itemSaveDto.getId()).orElseThrow(()-> new ItemException(ItemExcepTionType.WRONG_ITEM));
		    existingItem.update(itemSaveDto.getName(), itemSaveDto.getPrice(),itemSaveDto.getStock(),itemSaveDto.getDetail());
		    item = existingItem;
		} else {
		    item = itemSaveDto.toEntity();
		}

		Member seller = memberRepository.findByUsername(itemSaveDto.getSeller().getUsername()).orElseThrow(()-> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

		item.confirmSeller(seller);
		item.setIsSoldOut("N");
	    Item savedItem = itemRepository.save(item); 
	    if(savedItem.getId()!=null) {
	    List<FileDto> getfileList = fileService.getFileList(savedItem.getId());
	    if(getfileList!=null) {
	    	for(FileDto file : getfileList) {if(existIdx==null) {
	    		FileDto savedFile = fileService.getFileDetails(file.getIdx());
	    		savedFile.setDeleteYn("Y");
	    		fileService.saveFile(savedFile);     		
	    	}
	    	else if(existIdx!=null&&!existIdx.contains(file.getIdx())) {
	        		FileDto savedFile = fileService.getFileDetails(file.getIdx());
	        		savedFile.setDeleteYn("Y");
	        		fileService.saveFile(savedFile);    			
	    		}
	    	}
	    }
	    }
	    if (itemSaveDto.getFiles() != null) {
	        List<itemFile> fileList = fileService.save(itemSaveDto.getFiles(), savedItem.getId());
	        for (itemFile file : fileList) {
	            savedItem.addFile(file); // Post 엔티티에 첨부파일 추가
	        }
	    }
	    return savedItem;
	}

}
