package com.ffwb.dao;

import com.ffwb.entity.Exam;
import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dearlhd on 2017/7/3.
 */
public interface ExamDao extends JpaRepository<Exam, Long> {
    Exam findByUserAndName (User user, String name);
}