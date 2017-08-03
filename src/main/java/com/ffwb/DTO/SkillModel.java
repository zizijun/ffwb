package com.ffwb.DTO;

import java.util.List;

/**
 * Created by jinchuyang on 2017/7/22.
 */
public class SkillModel {
    private String name;
    private double value;
    private int number;
    private double correct;
    private double difficult;
    private double average;
    private List<String> sonTagName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
        this.correct = correct;
    }

    public double getDifficult() {
        return difficult;
    }

    public void setDifficult(double difficult) {
        this.difficult = difficult;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public List<String> getSonTagName() {
        return sonTagName;
    }

    public void setSonTagName(List<String> sonTagName) {
        this.sonTagName = sonTagName;
    }
}
