package com.ecui.ErnestsVilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "account", unique = true),
        @Index(columnList = "bank1Account", unique = true),
        @Index(columnList = "bank2Account", unique = true),
        @Index(columnList = "sessionIdHashed")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @Column(name = "bank1Account", nullable = false, unique = true)
    private String bank1Account;

    @Column(name = "bank2Account", nullable = false, unique = true)
    private String bank2Account;

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

    public String getBank1Account() {
        return bank1Account;
    }

    public void setBank1Account(String bank1Account) {
        this.bank1Account = bank1Account;
    }

    public String getBank2Account() {
        return bank2Account;
    }

    public void setBank2Account(String bank2Account) {
        this.bank2Account = bank2Account;
    }

    public void setSessionIdExpire(Long sessionIdExpire) {
        this.sessionIdExpire = sessionIdExpire;
    }
}
