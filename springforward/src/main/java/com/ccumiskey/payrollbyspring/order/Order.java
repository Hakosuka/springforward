package com.ccumiskey.payrollbyspring.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER") // plain old "ORDER" was taken
public class Order {
    private  @Id @GeneratedValue Long id;
    private String description;
    private OrderStatus status;

    public Order() { }

    public Order(String desc, OrderStatus status) {
        this.description = desc;
        this.status = status;
    }
}
