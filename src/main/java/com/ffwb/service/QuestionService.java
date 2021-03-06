package com.ffwb.service;

import com.ffwb.DTO.AnswerDTO;
import com.ffwb.DTO.QuestionDTO;
import com.ffwb.entity.Question;
import com.ffwb.entity.Tag;
import com.ffwb.model.PageListModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
/**
 * Created by jinchuyang on 2017/6/22.
 */
public interface QuestionService {
    int upload(MultipartFile file, Long managerId) throws IOException;

    int uploadExcel(MultipartFile multipartFile, Long managerId) throws IOException;

    PageListModel getAllQuestions(int pageIndex, int pageSize, String sortField, String sortOrder);

    PageListModel getQuestionsByConditions(String label, int type, int pageIndex, int pageSize, String sortField, String sortOrder);

//    boolean labelQuestions(List<QuestionDTO> dto);

    int updateQuestions(List<QuestionDTO> dto);

    int addQuestions(List<QuestionDTO> dto, Long managerId);

    int deleteQuestions(List<QuestionDTO> dto);

    List<Question> getQuestionByTag(int type, List<Tag> tags, String label);

    void convertSolution();


    void addJudgeQuestion() throws IOException;

    int tagQuestion(List<QuestionDTO> dtoList);

    AnswerDTO getAnsAndSol(Long answerId);

}
