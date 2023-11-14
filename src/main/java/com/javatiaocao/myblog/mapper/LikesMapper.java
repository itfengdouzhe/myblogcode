package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.model.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName LikesMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface LikesMapper {

    List<ArticleLikesRecord> getArticleThumbsUp();

    Integer getMsgIsNotReadNum();

    void readAllThumbsUp();

    List<FriendLink> getFriendLink();

    void addFriendLink(FriendLink friendLink);

    void updateFriendLink(@Param("friendLink")FriendLink friendLink,@Param("id") String id);

    void deleteFriendLink(String id);

    ArticleLikesRecord isLiked(@Param("articleId") Long articleId,@Param("likerId")  int likerId);

    void inertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);
}
