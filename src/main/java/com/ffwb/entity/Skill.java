package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jinchuyang on 2017/7/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "SKILL")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * tag
     */
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column
    private double point;

    @Column
    private int alive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
