package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author IT枫斗者
 * @ClassName VisitorMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface VisitorMapper {

    Visitor getVisitorNumByPageName(@Param("pageName") String pageName);

    long getTotalVisitor();

    void insertVistorArticlePage(String s);

    void updateVisitorBypageName(@Param("pageName") String pageName, @Param("visitorNum") String visitorNum);
}
