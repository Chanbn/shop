package com.board.domain.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.address.Address;
import com.board.domain.item.dto.ItemInfoDto;
import com.board.domain.item.service.ItemService;
import com.board.domain.member.Member;
import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.member.service.MemberService;
import com.board.domain.order.Order;
import com.board.domain.order.dto.OrderDto;
import com.board.domain.order.dto.OrderInfo;
import com.board.domain.order.dto.OrderItemDto;
import com.board.domain.order.dto.AddressDto;
import com.board.domain.order.dto.MyOrderDto;
import com.board.domain.order.dto.MyOrderListDto;
import com.board.domain.order.dto.OrderCartDto;
import com.board.domain.order.dto.OrderRequestDto;
import com.board.domain.order.dto.OrderResultDto;
import com.board.domain.order.service.OrderService;
import com.board.domain.orderstate.OrderState;
import com.board.domain.orderstate.service.OrderStateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
	
	private final OrderStateService orderStateService;
	private final OrderService orderService;
	private final ItemService itemService;
	private final MemberService memberService;
	
	@GetMapping("/list")
	public ResponseEntity<List<OrderState>> getList(@RequestParam("orderId") Long orderId){
		List<OrderState> orderStateList =orderStateService.getOrderStateList(orderId);
		return ResponseEntity.ok(orderStateList);
	}

	@GetMapping("/form")
	public void getForm(Model model,@ModelAttribute("orderInfo") OrderInfo orderInfo){
		ItemInfoDto itemInfoDto = itemService.getInfo(orderInfo.getItemId());
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("item",itemInfoDto);
	}
	 
	
    @PostMapping("/form")
    public String processForm(Model model, OrderCartDto orderInfo) {
    	List<OrderInfo> orderInfoList=orderInfo.getOrderInfo(); 
    	log.info("길이 "+orderInfoList.get(0).getItemId());
		List<ItemInfoDto> itemInfoDto = new ArrayList<>();
		for(OrderInfo info : orderInfoList) {
			ItemInfoDto itemInfo = itemService.getInfo(info.getItemId());
			itemInfoDto.add(itemInfo);
		}

		model.addAttribute("orderInfo", orderInfoList);
		model.addAttribute("item",itemInfoDto);
        return "order/form"; 
    }

    @ResponseBody
    @PostMapping(value = "/submit", consumes = "application/json")
    public ResponseEntity<Long> submitOrder(Model model, @RequestBody OrderRequestDto orderRequestDto) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	List<OrderDto> orderDtoList = new ArrayList<>();
    	for(OrderInfo order : orderRequestDto.getOrderInfo()) {
        	OrderDto orderDto = OrderDto.builder().orderInfo(order).addressDto(orderRequestDto.getAddressDto()).build();
        	orderDtoList.add(orderDto);
    	}
    	Order order = orderService.orderItemList(orderDtoList, username);
    	
    	model.addAttribute("orderList", orderDtoList);
    	return ResponseEntity.ok(order.getId());
    }
    

    @GetMapping("/result")
    public String submitOrderResult(Model model, @RequestParam("orderid") Long orderid) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	OrderResultDto order = orderService.getOrder(orderid, username);
    	Long totalPrice = orderService.getTotalPrice(orderid);
    	model.addAttribute("order", order);
    	model.addAttribute("totalPrice",totalPrice);
        return "order/result";
    } 
    
    @GetMapping("/myList")
    public void myList(Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	List<MyOrderListDto> orderList=orderService.myOrderList(username);

    	model.addAttribute("orderList", orderList);
    } 
}
