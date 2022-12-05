package com.ecui.ErnestsVilla.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "account",nullable = false,unique = true)
    private String account;

    @Column(name = "pwHashed",nullable = false)
    private String pwHashed;

    @Column(name = "sessionIdHashed",nullable = false)
    private String sessionIdHashed;

    @Column(name = "sessionIdExpire")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date sessionIdExpire;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwHashed() {
        return pwHashed;
    }

    public void setPwHashed(String pwHashed) {
        this.pwHashed = pwHashed;
    }

    public String getSessionIdHashed() {
        return sessionIdHashed;
    }

    public void setSessionIdHashed(String sessionIdHashed) {
        this.sessionIdHashed = sessionIdHashed;
    }

    public Date getSessionIdExpire() {
        return sessionIdExpire;
    }

    public void setSessionIdExpire(Date sessionIdExpire) {
        this.sessionIdExpire = sessionIdExpire;
    }
}
