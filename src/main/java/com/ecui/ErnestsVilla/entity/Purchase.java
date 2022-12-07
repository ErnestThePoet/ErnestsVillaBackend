package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "purchases",indexes = {
        @Index(columnList = "customerAccount"),
        @Index(columnList = "sellerAccount")
})
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customerAccount",nullable = false)
    private String customerAccount;

    @Column(name = "sellerAccount",nullable = false)
    private String sellerAccount;

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

    public Long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Column(name = "paymentCents",nullable = false)
    private Integer paymentCents;

    @Column(name = "purchaseTime",nullable = false)
    private Long purchaseTime;
}
