package com.ecui.ErnestsVilla.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "purchases",indexes = {
        @Index(columnList = "customerAccount"),
        @Index(columnList = "sellerAccount"),
        @Index(columnList = "purchaseId")
})
@NoArgsConstructor
public class Purchase {
    public Purchase(UnpaidPurchase unpaidPurchase,Long purchaseTime){
        this.consigneeAddress=unpaidPurchase.getConsigneeAddress();
        this.consigneeName=unpaidPurchase.getConsigneeName();
        this.consigneePhoneNumber=unpaidPurchase.getConsigneePhoneNumber();
        this.count=unpaidPurchase.getCount();
        this.customerAccount=unpaidPurchase.getCustomerAccount();
        this.itemId=unpaidPurchase.getItemId();
        this.paymentCents=unpaidPurchase.getPaymentCents();
        this.purchaseId=unpaidPurchase.getPurchaseId();
        this.purchaseTime=purchaseTime;
        this.sellerAccount=unpaidPurchase.getSellerAccount();
    }
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

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

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

    public void setConsigneePhoneNumber(String consigneePhoneNumber) {
        this.consigneePhoneNumber = consigneePhoneNumber;
    }

    @Column(name = "purchaseTime",nullable = false)
    private Long purchaseTime;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
