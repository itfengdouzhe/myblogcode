package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName CommentMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface CommentMapper {

    List<Comment> queryComment();

    Integer countCommentMapper();
}
