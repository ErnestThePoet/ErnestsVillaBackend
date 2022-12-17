package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "unpaid_purchases",indexes = {
        @Index(columnList = "customerAccount")
})
public class UnpaidPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customerAccount",nullable = false)
    private String customerAccount;

    @Column(name = "sellerAccount",nullable = false)
    private String sellerAccount;

    @Column(name = "itemId",nullable = false)
    private Integer itemId;

    @Column(name = "count",nullable = false)
    private Integer count;

    @Column(name = "paymentCents",nullable = false)
    private Integer paymentCents;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "createTime",nullable = false)
    private Long createTime;

    @Column(name = "expireTime",nullable = false)
    private Long expireTime;

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

    public String getSellerAccount() {
        return sellerAccount;
    }

    public void setSellerAccount(String sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPaymentCents() {
        return paymentCents;
    }

    public void setPaymentCents(Integer paymentCents) {
        this.paymentCents = paymentCents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
