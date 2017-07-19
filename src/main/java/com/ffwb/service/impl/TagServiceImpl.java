package com.ffwb.service.impl;

import com.ffwb.dao.TagDao;
import com.ffwb.entity.Tag;
import com.ffwb.service.TagService;
import com.ffwb.utils.TagBuilt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return tagDao.findByAlive(alive);
    }

    @Override
    public Tag addTag(Tag tag) {
        Tag tag1 = tagDao.save(tag);
        return tag1;
    }

    @Override
    public int addTagByCategory(String category) {
        String[] tags= TagBuilt.getTagsByCategory(category);
        int size=tags.length;
        List<Tag> l=new ArrayList<>();
        for(int i=0;i<size;i++){
            if((tagDao.findByContentAndAlive(tags[i],1))==null){
                Tag tag=new Tag();
                tag.setAlive(1);
                tag.setContent(tags[i]);
                tagDao.save(tag);
                l.add(tag);
            }
        }
        return l.size();
    }


}
