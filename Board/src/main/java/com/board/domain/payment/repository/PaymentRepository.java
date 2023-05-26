package com.board.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.domain.payment.PaymentsInfo;

public interface PaymentRepository extends JpaRepository<PaymentsInfo, Long> {
	PaymentsInfo findByOrderId(Long id);

}
