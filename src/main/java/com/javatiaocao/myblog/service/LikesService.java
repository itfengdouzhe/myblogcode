package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.model.FriendLink;
import com.javatiaocao.myblog.utils.DataMap;

/**
 * @author IT枫斗者
 * @ClassName LikesService.java
 * @From www.javatiaozao.com
 * @Description 点赞管理接口层
 */
public interface LikesService {

    DataMap getArticleThumbsUp(int rows, int pageNum);

    DataMap readAllThumbsUp();

    DataMap getFriendLink();

    DataMap addFriendLink(FriendLink friendLink);

    DataMap updateFriendLink(FriendLink friendLink,String id);

    DataMap deleteFriendLink(String id);

    boolean isLiked(Long valueOf, String userName);

    DataMap updateLikeByArticleId(String articleId);

    void inertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);
}
