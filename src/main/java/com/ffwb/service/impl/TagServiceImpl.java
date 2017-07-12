package com.ffwb.service.impl;

import com.ffwb.dao.TagDao;
import com.ffwb.entity.Tag;
import com.ffwb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinchuyang on 2017/7/12.
 */
@Service("TagService")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> findAllAlive(int alive) {
        return tagDao.findByAlive(1);
    }
}
