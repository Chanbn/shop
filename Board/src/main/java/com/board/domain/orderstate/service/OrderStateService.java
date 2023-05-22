package com.board.domain.orderstate.service;

import java.util.List;

import com.board.domain.orderstate.OrderState;

public interface OrderStateService {
	public List<OrderState> getOrderStateList(Long orderId);
	public OrderState OrderReceived(Long orderId); //주문 접수, 구매자요청
	public OrderState PaymentCompleted(Long orderId); //결제 완료, 판매자처리
	public OrderState InPreparation(Long orderId); //상품 준비, 판매자처리
	public OrderState OnDelivery(Long orderId); //배송 중, 판매자처리
	public OrderState Delivered(Long orderId); //배송 완료, 판매자처리
	public OrderState CancelRequested(Long orderId); //취소 요청, 구매자요청
	public OrderState CancelCompleted(Long orderId); //취소 완료, 판매자처리
	public OrderState ReturnRequested(Long orderId); //반품 요청, 구매자요청
	public OrderState ReturnCompleted(Long orderId); //반품 완료, 판매자처리
	public OrderState RefundCompleted(Long orderId); //환불 완료, 판매자처리
    
}
