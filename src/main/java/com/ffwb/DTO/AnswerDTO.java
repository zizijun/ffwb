package com.ffwb.DTO;

import com.ffwb.entity.Analysis;

import java.util.List;

/**
 * Created by lenovo on 2017/7/21.
 */
public class AnswerDTO {
    private String answer;
    private List<Analysis> analysisList;

    public String getAnswer(){
        return this.answer;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }

    public List<Analysis> getAnalysisList(){
        return this.analysisList;
    }

    public void setAnalysisList(List<Analysis> analysisList){
        this.analysisList=analysisList;
    }
}
