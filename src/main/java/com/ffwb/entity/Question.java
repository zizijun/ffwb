package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jinchuyang on 2017/6/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 外键 上传试题的管理员
     */
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    /**
     * 题干
     */
    @Lob
    @Column(name="description",length=60000)
    private String description;

    /**
     * 答案
     */
    @Column
    private String solution;

    /**
     * 类型
     *   1.选择题
     *   2.填空题
     *   3.判断题
     *   4.编程题
     *   5.简答题
     */
    @Column
    private int type;

    /**
     * 标签
     */
    @Column
    private String label;

    /**
     * 选项
     */
    @Column(name="optionJson",length=10000)
    private String optionJson;

    /**
     * 分数
     */
    @Column
    private int score;

    /**
     * 难度
     */
    @Column
    private double difficulty;

    @Column
    private int alive;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "QUESTION_TAG", joinColumns = {
            @JoinColumn(name = "QUESTION_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "TAG_id", referencedColumnName = "id")})
    //关系维护端，负责多对多关系的绑定和解除
    //@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(Player)
    //inverseJoinColumns指定外键的名字，要关联的关系被维护端(Tag)
    //其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //即表名为pquestion_tag
    //关联到主表的外键名：主表名+下划线+主表中的主键列名,即question_id
    //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即tag_id"
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
    private Set<Tag> tags = new HashSet<Tag>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOptionJson() {
        return optionJson;
    }

    public void setOptionJson(String optionJson) {
        this.optionJson = optionJson;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }
}
