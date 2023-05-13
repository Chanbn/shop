package com.board.domain.cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.board.domain.cart.service.CartService;
import com.board.domain.cartitem.CartItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart/*")
@Slf4j
public class CartController {
	private final CartService cartService;
	@GetMapping("/list")
	public void getList(Model model) {
		List<CartItem> item =cartService.getList();
		model.addAttribute("itemList", item);
	}
}
