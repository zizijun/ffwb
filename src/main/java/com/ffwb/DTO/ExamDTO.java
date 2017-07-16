package com.ffwb.DTO;

/**
 * Created by dearlhd on 2017/7/16.
 */
public class ExamDTO {
    private long examId;
    private long userId;
    private int totalScore;

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
