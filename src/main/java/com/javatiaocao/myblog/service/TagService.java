package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Tags;

/**
 * @author IT枫斗者
 * @ClassName TagService.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface TagService {

    void insertTags(String[] newArticleTags, int parseInt);

    Integer getTagsSizeByName(String s);
}
