package com.ffwb.hello;

import com.ffwb.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jinchuyang on 2017/7/14.
 */
@Component
public class ScheduledTasks {
    @Autowired
    private SkillService skillService;
    @Scheduled(cron ="0/60 * * * * ?}")
    public void skillAnalyze() {
        skillService.analyzeSkill();
    }
}
