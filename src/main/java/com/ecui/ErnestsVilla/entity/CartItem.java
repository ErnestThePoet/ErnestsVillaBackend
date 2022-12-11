package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_items",indexes = {
        @Index(columnList = "customerAccount"),
        @Index(columnList = "itemId")
})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customerAccount",nullable = false)
    private String customerAccount;

    @Column(name = "itemId",nullable = false)
    private Integer itemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "count",nullable = false)
    private Integer count;
}
