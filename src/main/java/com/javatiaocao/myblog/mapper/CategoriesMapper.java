package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.model.LeaveMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName CategoriesMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface CategoriesMapper {
    List<Categories> getArticleCategories();

    int findIsExisByCategoryName(String categoryName);

    void savaCategories(Categories categories);

    void deteleCategory(String categoryName);

    List<String> findCategoriesNames();

    List<LeaveMessage> getPageLeaveMessage(@Param("pageName") String pageName, @Param("pId") int pId);

//    List<Categories> getCategoryArticles();

    List<Article> getCategoryArticle(String category);

    List<Article> getCategoryArticles();

    List<String> findCategoriesNameAndArticleNum();

    List<LeaveMessage> findLeaveMessageReplyByPageNameAndPid(String pageName, int id);

    void publishLeaveMessage(LeaveMessage leaveMessage);
}
