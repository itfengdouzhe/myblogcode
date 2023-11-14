package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.utils.DataMap;

import java.util.Map;

/**
 * @author IT枫斗者
 * @ClassName ArticleService.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface ArticleService {
    DataMap insertArticle(Article article);

    DataMap getArticleManagement(int rows, int pageNum);

    DataMap deleteArticle(String id);

    Article getArticleByid(String id);

    DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName);

    DataMap updateArticleById(Article article);

    DataMap getMyArticles(int rows, int pageNum);

    Map<String, String> findArticleTitleByArticleId(long articleId);

    DataMap getArticleByArticleId(long articleId);
}
