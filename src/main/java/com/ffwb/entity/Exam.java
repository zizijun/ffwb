package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by dearlhd on 2017/7/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "EXAM")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * 考试名称
     */
    @Column
    private String name;

    /**
     * 总时间，以分钟为单位
     */
    @Column
    private int totalTime;

    /**
     * 模式（整卷、逐题）
     */
    @Column
    private int mode;

    /**
     * 花费的时间
     */
    @Column
    private int costTime;

    /**
     * 总分
     */
    @Column
    private int totalScore;

    /**
     * 得分
     */
    @Column
    private int grade;

    /**
     * 用户id
     */
    @Column
    //@ManyToOne(targetEntity = User.class)
    private long userId;

    /**
     * 是否可用
     */
    @Column
    private int alive;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
