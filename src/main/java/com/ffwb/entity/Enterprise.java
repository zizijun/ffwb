package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by lenovo on 2017/8/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ENTERPRISE")
public class Enterprise {
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

    @Column
    private int alive;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel){
        this.tel=tel;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
