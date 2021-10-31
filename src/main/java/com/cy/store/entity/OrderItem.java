package com.cy.store.entity;

import lombok.Data;

@Data
//订单中的商品数据实体类
public class OrderItem extends BaseEntity {
    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;
}
