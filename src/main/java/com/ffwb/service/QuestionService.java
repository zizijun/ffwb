package com.ffwb.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by jinchuyang on 2017/6/22.
 */
public interface QuestionService {
    int upload(MultipartFile file, Long managerId) throws IOException;
}
