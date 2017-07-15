package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by dearlhd on 2017/7/4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ANSWER")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 关联的考试id
     */
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    /**
     * 答案内容
     */
    @Column
    private String answer;

    /**
     * 判定结果
     */
    @Column
    private boolean isRight;

    /**
     *
     */
    @Column
    private int alive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
