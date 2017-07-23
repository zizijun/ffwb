package com.ffwb.service;

import com.ffwb.DTO.SkillModel;

import java.util.List;
import java.util.Map;

/**
 * Created by jinchuyang on 2017/7/14.
 */
public interface SkillService {
    void analyzeSkill();

    List<SkillModel> getSkillModel(Long userId);
}
