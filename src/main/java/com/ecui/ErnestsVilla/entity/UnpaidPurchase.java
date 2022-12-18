package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "unpaid_purchases",indexes = {
        @Index(columnList = "customerAccount"),
        @Index(columnList = "expireTime"),
        @Index(columnList = "purchaseId")
})
public class UnpaidPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchaseId",nullable = false)
    private Integer purchaseId;

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

    @Column(name = "consigneeAddress",nullable = false)
    private String consigneeAddress;

    @Column(name = "consigneeName",nullable = false)
    private String consigneeName;

    @Column(name = "consigneePhoneNumber",nullable = false)
    private String consigneePhoneNumber;

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhoneNumber() {
        return consigneePhoneNumber;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public void setConsigneePhoneNumber(String consigneePhoneNumber) {
        this.consigneePhoneNumber = consigneePhoneNumber;
    }

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
