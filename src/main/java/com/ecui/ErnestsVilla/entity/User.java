package com.ecui.ErnestsVilla.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
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
}
