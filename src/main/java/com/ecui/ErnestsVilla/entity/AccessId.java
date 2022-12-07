package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "access_ids", indexes = {
        @Index(columnList = "accessIdHashed", unique = true)
})
public class AccessId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "accessIdHashed", nullable = false)
    private String accessIdHashed;

    @Column(name = "accessIdExpire", nullable = false)
    private Long accessIdExpire;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccessIdHashed() {
        return this.accessIdHashed;
    }

    public void setAccessIdHashed(String accessIdHashed) {
        this.accessIdHashed = accessIdHashed;
    }

    public Long getAccessIdExpire() {
        return this.accessIdExpire;
    }

    public void setAccessIdExpire(Long accessIdExpire) {
        this.accessIdExpire = accessIdExpire;
    }
}
