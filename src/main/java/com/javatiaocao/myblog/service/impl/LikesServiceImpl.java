package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.LikesMapper;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.model.FriendLink;
import com.javatiaocao.myblog.service.LikesService;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName LikesServiceImpl.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    LikesMapper likesMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        //开启分页插件
        PageHelper.startPage(pageNum, rows);
        List<ArticleLikesRecord> likesRecord = likesMapper.getArticleThumbsUp();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecord);
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject articleLikesJson;
        for (ArticleLikesRecord articleLikesRecord : likesRecord) {
            articleLikesJson = new JSONObject();
            articleLikesJson.put("id", articleLikesRecord.getId());
            articleLikesJson.put("articleId", articleLikesRecord.getArticleId());
            articleLikesJson.put("articleTitle", articleMapper.getArticleTitleByArtileId(articleLikesRecord.getArticleId()));
            articleLikesJson.put("likeDate", articleLikesRecord.getLikeDate());
            articleLikesJson.put("praisePeople", articleMapper.getArticleAuthorByArtileId(articleLikesRecord.getArticleId()));//查询点赞人？？？
            articleLikesJson.put("isRead", articleLikesRecord.getArticleId());
            jsonArray.add(articleLikesJson);
        }
        returnJson.put("result", jsonArray);
        returnJson.put("msgIsNotReadNum", likesMapper.getMsgIsNotReadNum());
        //封装最外层数据
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        returnJson.put("pageInfo", pageJson);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap readAllThumbsUp() {
        likesMapper.readAllThumbsUp();
        //TODO  删除redis中的点赞消息
        return DataMap.success();
    }

    @Override
    public DataMap getFriendLink() {
        List<FriendLink> friendLinks = likesMapper.getFriendLink();
        return DataMap.success().setData(friendLinks);
    }

    @Transactional
    @Override
    public DataMap addFriendLink(FriendLink friendLink) {
        //从严格意义来说这里可以加
//        int id = likesMapper.findIsExistByBlogger(friendLink.getBlogger());
        likesMapper.addFriendLink(friendLink);
        return DataMap.success(CodeType.ADD_FRIEND_LINK_SUCCESS).setData(friendLink.getId());
    }

    @Transactional
    @Override
    public DataMap updateFriendLink(FriendLink friendLink, String id) {
        likesMapper.updateFriendLink(friendLink, id);
        return DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public DataMap deleteFriendLink(String id) {
        likesMapper.deleteFriendLink(id);
        return DataMap.success(CodeType.DELETE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public boolean isLiked(Long articleId, String userName) {
        ArticleLikesRecord articleLikesRecord = likesMapper.isLiked(articleId, userMapper.getUserIdByuserName(userName));
        return articleLikesRecord != null;
    }

    @Override
    public DataMap updateLikeByArticleId(String articleId) {
        articleMapper.updateLikeByArticleId(articleId);
        int likes = articleMapper.queryLiksByarticle(articleId);
        return DataMap.success().setData(likes);
    }

    @Override
    public void inertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        likesMapper.inertArticleLikesRecord(articleLikesRecord);
    }
}
