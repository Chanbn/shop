package com.board.domain.address.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.address.dto.addressDto;
import com.board.domain.address.service.AddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AddressController {

	private final AddressService addressService;
	@GetMapping("/order/address")
	@ResponseBody
	public ResponseEntity<addressDto> getAddress(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		addressDto dto = addressService.getAddress(username);
		return ResponseEntity.ok(dto);
	}
	@PostMapping(value =  "/order/address", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<String> setAddress(@RequestBody addressDto dto){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("dto :: "+dto.toString());
		addressService.setAddress(username, dto);
		return ResponseEntity.ok("저장되었습니다.");
	}
}
