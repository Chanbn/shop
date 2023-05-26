package com.board.domain.payment.service;

import java.util.List;
import java.util.Map;

import com.board.domain.order.dto.OrderInfo;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

public interface PaymentService {

	public void verifyIamport(IamportResponse<Payment> irsp, Long amount, List<OrderInfo> orderList,Long orderId);
	
	public CancelData cancelData(Map<String,String> map, IamportResponse<Payment> lookUp); 
}
