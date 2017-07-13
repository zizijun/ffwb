package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jinchuyang on 2017/7/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TAG")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 内容
     */
    @Column
    private String content;

    /**
     * 是否被删除
     */
    @Column
    private int alive = 1;

    //只需要设置mappedBy="games"表明Game实体是关系被维护端就可以了
    //级联保存、级联删除等之类的属性在多对多关系中是不需要设置
    //不能说删了游戏,把玩家也删掉,玩家还可以玩其他的游戏
    @ManyToMany(mappedBy="tags")
    private Set<Question> questions = new HashSet<Question>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    @JsonIgnore
    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
