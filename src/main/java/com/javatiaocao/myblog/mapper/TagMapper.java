package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName TagMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface TagMapper {
    void saveTags(Tags tag);

    boolean findIsExistByTagName(String newArticleTag);

    Integer getTagsSizeByName(String tagName);

    Integer countTagsNum();

    List<Tags> findTagsCloud();
}
