package com.ecui.ErnestsVilla.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "account", unique = true),
        @Index(columnList = "sessionIdHashed")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @Column(name = "pwHashed", nullable = false)
    private String pwHashed;

    @Column(name = "sessionIdHashed", nullable = false)
    private String sessionIdHashed;

    @Column(name = "sessionIdExpire")
    private Long sessionIdExpire;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getSessionIdExpire() {
        return sessionIdExpire;
    }

    public void setSessionIdExpire(Long sessionIdExpire) {
        this.sessionIdExpire = sessionIdExpire;
    }
}
