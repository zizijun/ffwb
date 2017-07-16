package com.ffwb.dao;

import com.ffwb.entity.Exam;
import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dearlhd on 2017/7/3.
 */
public interface ExamDao extends JpaRepository<Exam, Long> {
    Exam findByUserAndName (User user, String name);
    Exam findByIdAndAlive (long examId, int alive);
    List<Exam> findByUserAAndAlive (User user, int alive);
}