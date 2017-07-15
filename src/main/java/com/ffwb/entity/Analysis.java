package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinchuyang on 2017/7/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ANALYSIS")
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 问题
     */
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 内容
     */
    @Column
    private String content;

    /**
     * 加入时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date createdTime ;

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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
