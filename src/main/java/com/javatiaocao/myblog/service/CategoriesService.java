package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

/**
 * @author IT枫斗者
 * @ClassName CategoriesService.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface CategoriesService {

    DataMap getArticleCategories();

    DataMap updateCategory(String categoryName, int type);

    DataMap findCategoriesNames();

    DataMap getPageLeaveMessage(String pageName, String userName);

    DataMap getCategoryArticle(String category, int rows, int pageNum);

    DataMap findCategoriesNameAndArticleNum();

    void publishLeaveMessage(String leaveMessageContent, String userName, String pageName);
}
