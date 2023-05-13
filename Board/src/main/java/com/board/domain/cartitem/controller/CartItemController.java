package com.board.domain.cartitem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.board.domain.cartitem.service.CartItemService;
import com.board.domain.member.dto.MemberSessionDto;
import com.board.domain.post.controller.PostController;
import com.board.domain.post.service.PostService;
import com.board.file.board.service.boardFileService;
import com.board.global.Login.MemberDetailsService;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartItem/*")
@Slf4j
public class CartItemController {

	private final CartItemService cartItemService;
	
	@ResponseBody
	@PostMapping("/add")
	public ResponseEntity<String> addItem(@SessionAttribute("user") MemberSessionDto member,@RequestParam("ItemId") Long ItemId,@RequestParam("count") int count){
		cartItemService.addItem(ItemId, count, member);
		return ResponseEntity.ok("추가하였습니다.");
	}

	@ResponseBody
	@GetMapping("/update")
	public ResponseEntity<String> updateItem(@SessionAttribute("user") MemberSessionDto member,@RequestParam("ItemId") Long ItemId,@RequestParam("count") int count){
		cartItemService.updateItem(ItemId, count, member);
		return ResponseEntity.ok("추가하였습니다.");
	}
	
	@ResponseBody
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteItem(@SessionAttribute("user") MemberSessionDto member,@RequestParam("ItemId") Long ItemId,@RequestParam("count") int count){
		cartItemService.removeItem(ItemId);
		return ResponseEntity.ok("삭제하였습니다.");
	}
	
	@ResponseBody
	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteAllItem(@SessionAttribute("user") MemberSessionDto member){
		cartItemService.removeAllCartItem(member);
		return ResponseEntity.ok("삭제하였습니다.");
	}	
	
	
}
