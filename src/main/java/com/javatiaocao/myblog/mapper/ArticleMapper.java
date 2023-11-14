package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName ArticleMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface ArticleMapper {
    void saveArticle(Article article);

    List<Article> getArticleManagement();

    Article getArticleById(String id);

    Article getArticleByIntId(Integer id);

    void updateLastNextId(@Param("lastOrNextStr") String lastOrNextStr, @Param("updateId") long updateId, @Param("articleId") long articleId);

    void deleteArticle(long articleId);

    void updateArticleById(Article article);

    String getArticleTitleByArtileId(long articleId);

    String getArticleAuthorByArtileId(long articleId);

    Article getArticleByAticleId(long articleId);

    int countArticleNum();

    void updateLikeByArticleId(String articleId);

    int queryLiksByarticle(String articleId);

    String countArticleNumByCategory(String categoryName);
}
