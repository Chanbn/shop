package com.board.domain.cartitem;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.board.domain.BaseTimeEntity;
import com.board.domain.cart.Cart;
import com.board.domain.item.Item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@Table(name = "cart_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CartItem extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	@ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    
    private int count;
    
    @Builder
    public CartItem(Cart cart, Item item, int count) {
    	this.cart = cart;
    	this.item = item;
    	this.count = count;
    }
    
    public void addCount(int count) {
    	this.count += count;
    }
}
