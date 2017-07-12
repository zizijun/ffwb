package com.ffwb.DTO;

import com.ffwb.entity.Manager;
import com.ffwb.entity.Tag;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jinchuyang on 2017/6/26.
 */
public class QuestionDTO {

    private Long id;

    /**
     * 题干
     */
    private String description;

    /**
     * 答案
     */
    private String solution;

    /**
     * 类型
     */
    private String type;

    /**
     * 标签 java／前端
     */
    String label;

    /**
     * tag 数据库／网络／多线程
     */
    Set<Integer> tagIds;

    /**
     * 选项
     */
    Map optionJson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map getOptionJson() {
        return optionJson;
    }

    public void setOptionJson(Map optionJson) {
        this.optionJson = optionJson;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}
