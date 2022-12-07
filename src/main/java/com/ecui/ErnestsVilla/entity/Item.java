package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "items",indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "sellerAccount")
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sellerAccount",nullable = false)
    private String sellerAccount;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "previewImageFileName",nullable = false)
    private String previewImageFileName;

    @Column(name = "remaining",nullable = false)
    private Integer remaining;

    @Column(name = "priceCents",nullable = false)
    private Integer priceCents;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSellerAccount() {
        return sellerAccount;
    }

    public void setSellerAccount(String sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewImageFileName() {
        return previewImageFileName;
    }

    public void setPreviewImageFileName(String previewImageFileName) {
        this.previewImageFileName = previewImageFileName;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }
}
