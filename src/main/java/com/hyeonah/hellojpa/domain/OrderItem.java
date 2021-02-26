package com.hyeonah.hellojpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyeonah.hellojpa.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; //  주문 수량

    public static OrderItem createOrderItem(final Item item, final int orderPrice, final int count) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문 상품 전체 가격 조회
     *
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}