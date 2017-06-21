package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jinchuyang on 2017/6/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 电话
     */
    @Column
    private String tel;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     *  职业
     */
    @Column
    private String profession;

    /**
     * 是否被删除
     */
    @Column
    private int alive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
