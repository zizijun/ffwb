package com.ffwb.service;

import com.ffwb.entity.Tag;

import java.util.List;

/**
 * Created by jinchuyang on 2017/7/12.
 */
public interface TagService {
    List<Tag> findAllAlive(int alive);

    Tag addTag(Tag tag);

    int addTagByCategory(String category);
}
