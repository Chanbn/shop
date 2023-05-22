package com.board.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.board.domain.BaseTimeEntity;
import com.board.domain.address.Address;
import com.board.domain.cart.Cart;
import com.board.domain.item.Item;
import com.board.domain.member.Member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@Table(name = "ordertable")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Order extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

	
	@ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
	
	
	private String deliveryAddress;
	private String deliveryHp;
	private String deliveryName;
	
	@Builder
	public Order(List<OrderItem> orderItems, Member member) {
		this.orderItems = orderItems;
		this.member = member;
	}
	
    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    
    public static Order createOrder(Member member, List<OrderItem> orderItem,Address address)
    {
    	Order order = new Order();
    	order.setMember(member);
    	order.setDeliveryAddress(address.getAddress());
    	order.setDeliveryHp(address.getHp());
    	order.setDeliveryName(address.getName());
    	for(OrderItem item:orderItem) {
    		order.addOrderItems(item);
    	}
    	return order;
    }
    public static Order createOrder(Member member, OrderItem orderItem)
    {
    	Order order = new Order();
    	order.setMember(member);
    	order.addOrderItems(orderItem);
    	return order;
    }
}
