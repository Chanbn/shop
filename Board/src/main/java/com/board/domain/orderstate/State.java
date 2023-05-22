package com.board.domain.orderstate;

public enum State {
	OrderReceived("주문 접수"),
	PaymentCompleted("결제 완료"),
	InPreparation("상품 준비"),
	OnDelivery("배송 중"),
	Delivered("배송 완료"),
	CancelRequested("취소 요청"),
	CancelCompleted("취소 완료"),
	ReturnRequested("반품 요청"),
	ReturnCompleted("반품 완료"),
	RefundCompleted("환불 완료");
    
    private final String value;
    
    State(String value){
        this.value = value;
            
    }
    
    public String getValue(){
        return value;
    }
}
