package com.board.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.board.domain.member.Member;
import com.board.domain.order.Order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "payment")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String payMethod;
	private String impUid;
	private String merchantUid;
	private Long amount;
	private String buyerAddr;
	private String buyerPostcode;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
}
