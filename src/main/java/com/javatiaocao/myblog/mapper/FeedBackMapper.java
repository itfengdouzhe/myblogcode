package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.FeedBack;
import com.javatiaocao.myblog.model.PrivateWord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName FeedBackMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface FeedBackMapper {
    List<FeedBack> getAllFeedback();

    List<PrivateWord> getAllPrivateWord();
}
