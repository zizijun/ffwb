package com.ffwb.entity;

/**
 * Created by jinchuyang on 16/10/21.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    /**
     * id
     level等级
     nickname 昵称
     passWord密码
     address地址
     zip邮编
     tel电话
     headUrl头像
     money金额
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 级别
     */
    @Column
    private int lever;

    /**
     * 名字
     */
    @Column
    private String nickname;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 地址
     */
    @Column
    private String address;

    /**
     * 邮编
     */
    @Column
    private String zip;

    /**
     * 电话
     */
    @Column
    private String tel;

    /**
     * 系统账户总额
     */
    @Column
    private double money;

    /**
     * 头像
     */
    private String headUrl;
}
