package com.board.domain.payment.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.order.dto.OrderInfo;
import com.board.domain.payment.dto.RequestPayDto;
import com.board.domain.payment.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@PropertySource("classpath:application-oauth.properties")
public class PaymentController {
	@Autowired 
	private PaymentService paymentService;
	
	private final IamportClient iamportClient;

	
    public PaymentController(@Value("${IamPort.apiKey}") String apiKey, @Value("${IamPort.apiSecret}") String apiSecret) {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }
	
//	public PaymentController() {
//		System.out.println("apikey"+apiKey+"123123123");
//		//String apiKey = "3220165732145473";
//		//String apiSecret = "bhfmXLonx862yTuMqFmtQGeXqGZCyji7uH9xVoAk34qf7ECridr4qwnAbXwHYdIkVsV3qsnZ1mGxBJdd";
//		this.iamportClient = new IamportClient(apiKey, apiSecret);
//	}
	
	public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException{
		return iamportClient.paymentByImpUid(impUid);
	}
	
	@PostMapping("/order/verifyIamport")
	public IamportResponse<Payment> verifyIamport(@RequestBody RequestPayDto requestPayDto) throws IamportResponseException, IOException{
		
		String impUid = requestPayDto.getImp_uid();
		List<OrderInfo> orderInfo = requestPayDto.getOrderInfo();
		Long amount = requestPayDto.getAmount();
		Long orderId = requestPayDto.getOrderId();
		
		IamportResponse<Payment> irsp = paymentLookup(impUid);
		
		paymentService.verifyIamport(irsp, amount, orderInfo, orderId);
		
		return irsp;
		
	}
	
	
	@PostMapping("/order/cancelPayments")
	public IamportResponse<Payment> cancelPayments(@RequestBody Map<String,String> map) throws IamportResponseException, IOException{
		IamportResponse<Payment> lookUp = null;
		if(map.containsKey("impUid")) lookUp = paymentLookup(map.get("impUid"));
		
		CancelData data = paymentService.cancelData(map, lookUp);
		IamportResponse<Payment> cancel =  iamportClient.cancelPaymentByImpUid(data);
		return cancel;
	}
}
