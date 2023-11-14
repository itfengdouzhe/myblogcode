package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Comment;
import com.javatiaocao.myblog.model.LeaveMessage;
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
public interface LeaveMessageMapper {


    List<LeaveMessage> queryLeaveMessage();

    Integer countLeaveMessageMapper();
}
