package com.ffwb.service;

import com.ffwb.entity.Answer;

/**
 * Created by dearlhd on 2017/7/3.
 */
public interface AnswerService {
    Answer addAnswer (Answer answer);
    Answer updateAnswer(Answer answer);
    boolean deleteAnswer(Answer answer);
}
