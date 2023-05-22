package com.board.domain.order;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    
    private Long count;
    
    private Long orderPrice;
    
    private String fileName;
    
    private LocalDateTime createdDate;
    //Order 클래스에서 OrderItem을 cascade 설정해 두었기때문에, Order 객체를 저장할 때, 
    //OrderItem 객체는 물론 OrderItem과 관계를 맺고있는 Item 객체도 저장된다.
    public static OrderItem createOrderItem(Item item, Long count){

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); 
        orderItem.setCount(count); 
        orderItem.setOrderPrice(item.getPrice()); 
        orderItem.setFileName(item.getFileLists().get(0).getSaveName());
        orderItem.setCreatedDate(item.getFileLists().get(0).getCreatedDate());
        item.removeStock(count);

        return orderItem;
    }
}
