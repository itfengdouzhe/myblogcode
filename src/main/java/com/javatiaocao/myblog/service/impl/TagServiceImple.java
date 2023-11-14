package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.mapper.TagMapper;
import com.javatiaocao.myblog.model.Tags;
import com.javatiaocao.myblog.service.TagService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author IT枫斗者
 * @ClassName TagServiceImple.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class TagServiceImple implements TagService {
    @Autowired
    TagMapper tagMapper;

    @Override
    public void insertTags(String[] newArticleTags, int tagSize) {
        for (String newArticleTag : newArticleTags) {
            //插入之前进行一个判断，去重标签
            if(tagMapper.findIsExistByTagName(newArticleTag)){
                Tags tag = new Tags(newArticleTag,tagSize);
                tagMapper.saveTags(tag);
            }
        }
    }

    @Override
    public Integer getTagsSizeByName(String tagName) {
        return tagMapper.getTagsSizeByName(tagName);
    }
}
