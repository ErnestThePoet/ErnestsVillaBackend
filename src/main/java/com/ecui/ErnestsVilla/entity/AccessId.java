package com.ecui.ErnestsVilla.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "g_access_ids",indexes = {
        @Index(columnList = "accessIdHashed",unique = true)
})
public class AccessId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account",nullable = false)
    private String account;

    @Column(name = "accessIdHashed",nullable = false)
    private String accessIdHashed;

    @Column(name = "accessIdExpire",nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date accessIdExpire;

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public String getAccount(){
        return this.account;
    }

    public void setAccount(String account){
        this.account=account;
    }

    public String getAccessIdHashed(){
        return this.accessIdHashed;
    }

    public void setAccessIdHashed(String accessIdHashed){
        this.accessIdHashed=accessIdHashed;
    }

    public Date getAccessIdExpire(){
        return this.accessIdExpire;
    }

    public void setAccessIdExpire(Date accessIdExpire){
        this.accessIdExpire=accessIdExpire;
    }
}
