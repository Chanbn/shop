package com.board.domain.payment.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.board.domain.item.Item;
import com.board.domain.item.exception.ItemException;
import com.board.domain.item.exception.ItemExceptionType;
import com.board.domain.item.repository.ItemRepository;
import com.board.domain.member.Member;
import com.board.domain.member.repository.MemberRepository;
import com.board.domain.order.Order;
import com.board.domain.order.OrderItem;
import com.board.domain.order.dto.OrderInfo;
import com.board.domain.order.exception.OrderException;
import com.board.domain.order.exception.OrderExceptionType;
import com.board.domain.order.repository.OrderRepository;
import com.board.domain.orderstate.service.OrderStateService;
import com.board.domain.payment.PaymentsInfo;
import com.board.domain.payment.exception.PaymentException;
import com.board.domain.payment.exception.PaymentExceptionType;
import com.board.domain.payment.repository.PaymentRepository;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.MemberRemoval;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;
	private final OrderStateService orderStateService;
	
	@Override
	public void verifyIamport(IamportResponse<Payment> irsp, Long amount, List<OrderInfo> orderList,Long orderId) {
		// TODO Auto-generated method stub
		Long totalAmount = 0L;
		//아이템아이디, 아이템가격*수량
		Map<Long,Long> orderMap = new HashMap<>();
		
		//클라이언트 주문내역 검증 (db에 저장된 상품가격 * 주문수량)
		for(OrderInfo order : orderList) {
			Item item = itemRepository.getById(order.getItemId());
			if(item==null) {
				throw new ItemException(ItemExceptionType.WRONG_ITEM);
			}
			orderMap.put(order.getItemId(), order.getCount()*order.getPrice());
			totalAmount += order.getCount()*item.getPrice();
		}
		
		if(amount != totalAmount) {
			throw new PaymentException(PaymentExceptionType.WRONG_PAYINFO);
		}
		if(amount != irsp.getResponse().getAmount().longValue()) {
			throw new PaymentException(PaymentExceptionType.WRONG_PAYINFO);			
		}
		
		Member member = memberRepository.findByEmail(irsp.getResponse().getBuyerEmail());
		Order order = orderRepository.findByIdAndMemberUsername(orderId, member.getUsername());
		if(order==null) {
			throw new OrderException(OrderExceptionType.WRONG_ORDER);
		}
		
		List<OrderItem> orderItem = order.getOrderItems();
		
		//클라이언트 주문내역검증 (상품 지불금액)
		for(OrderItem item : orderItem) {
			Long itemPrice =  orderMap.get(item.getItem().getId());
			Long orderPrice = item.getCount()*item.getOrderPrice();
			log.info("orderPrice : '"+orderPrice+"' itemPrice : '"+itemPrice+"'" );
			if(itemPrice != orderPrice) {
				throw new PaymentException(PaymentExceptionType.WRONG_PAYINFO);
			}
		}
		
		
		
		PaymentsInfo paymentsInfo = PaymentsInfo.builder()
									.payMethod(irsp.getResponse().getPayMethod())
									.impUid(irsp.getResponse().getImpUid())
									.merchantUid(irsp.getResponse().getMerchantUid())
									.amount(irsp.getResponse().getAmount().longValue())
									.buyerAddr(irsp.getResponse().getBuyerAddr())
									.buyerPostcode(irsp.getResponse().getBuyerPostcode())
									.member(member)
									.order(order)
									.build();
		paymentRepository.save(paymentsInfo);
		orderStateService.PaymentCompleted(orderId);
		
	}

	@Override
	public CancelData cancelData(Map<String, String> map, IamportResponse<Payment> lookUp) {
		// TODO Auto-generated method stub
		if(!lookUp.getResponse().getAmount().equals(new BigDecimal(map.get("checksum"))))
		{
			throw new PaymentException(PaymentExceptionType.WRONG_REFUND);
		}
		
		CancelData data = new CancelData(lookUp.getResponse().getImpUid(),true);
		data.setReason(map.get("reason"));
		data.setChecksum(new BigDecimal(map.get("checksum")));
		data.setRefund_holder(map.get("refundHolder"));
		data.setRefund_bank(map.get("code"));
		data.setRefund_account("refundAccount");
		return data;
	}

}
