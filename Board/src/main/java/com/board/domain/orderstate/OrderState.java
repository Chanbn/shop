package com.board.domain.orderstate;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.board.domain.cart.Cart;
import com.board.domain.item.Item;
import com.board.domain.order.Order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@Table(name = "order_state")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderState {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	private LocalDateTime progressDate;
	
	@Builder
	public OrderState(Order order, State state, LocalDateTime progressDate){
		this.order = order;
		this.state = state;
		this.progressDate = progressDate;
	}
}
