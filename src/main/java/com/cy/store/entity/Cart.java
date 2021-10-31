package com.cy.store.entity;

import lombok.Data;

@Data
//购物车数据的实体类
public class Cart extends BaseEntity {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
}
