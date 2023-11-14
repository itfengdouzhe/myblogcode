package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.*;
import com.javatiaocao.myblog.model.Comment;
import com.javatiaocao.myblog.model.LeaveMessage;
import com.javatiaocao.myblog.model.Tags;
import com.javatiaocao.myblog.service.IndexService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName IndexServiceImpl.java
 * @From www.javatiaozao.com
 * @Description 首页的实现类
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private LeaveMessageMapper leaveMessageMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public DataMap getNewComment(int rows, int pageNum) {
        //开启分页
        PageHelper.startPage(pageNum,rows);
        //创建一个json对象
        JSONObject resultJson = new JSONObject();
        //查询评论
        List<Comment> comments = commentMapper.queryComment();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        JSONArray jsonArray = new JSONArray();
        //用于循环内部用
        JSONObject jsonObject;
        //遍历查询评论结果
        for (Comment comment : comments) {
            jsonObject = new JSONObject();
           jsonObject.put("commentContent",comment.getCommentContent());
           jsonObject.put("id",comment.getId());
           jsonObject.put("articleId",comment.getArticleId());
           jsonObject.put("answerer",userMapper.findUsernameByid(comment.getAnswererId()));
           jsonObject.put("commentDate",comment.getCommentDate());
           jsonObject.put("articleTitle",articleMapper.getArticleTitleByArtileId(comment.getArticleId()));
          jsonArray.add(jsonObject);
        }

        resultJson.put("result",jsonArray);
        //封装分页数据
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        //最后统一封装
        resultJson.put("pageInfo",pageJson);
        return DataMap.success().setData(resultJson);
    }

    @Override
    public DataMap getNewLeaveWord(int rows, int pageNum) {
        //开启分页
        PageHelper.startPage(pageNum,rows);
        //创建一个json对象
        JSONObject resultJson = new JSONObject();
        //查询最新留言
        List<LeaveMessage> leaveMessages = leaveMessageMapper.queryLeaveMessage();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessages);

        JSONArray jsonArray = new JSONArray();
        //用于循环内部用
        JSONObject jsonObject;
        //遍历查询留言
        for (LeaveMessage leaveMessage : leaveMessages) {
            jsonObject = new JSONObject();
            jsonObject.put("id",leaveMessage.getId());
            jsonObject.put("pagePath",leaveMessage.getPageName());
            jsonObject.put("leaveWordContent",leaveMessage.getLeaveMessageContent());
            jsonObject.put("answerer",userMapper.findUsernameByid(leaveMessage.getAnswererId()));
            jsonObject.put("leaveWordDate",leaveMessage.getLeaveMessageDate());
            jsonArray.add(jsonObject);
        }

        resultJson.put("result",jsonArray);
        //封装分页数据
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        //最后统一封装
        resultJson.put("pageInfo",pageJson);
        return DataMap.success().setData(resultJson);
    }

    @Override
    public DataMap getSiteInfo() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("articleNum",articleMapper.countArticleNum());
        map.put("tagsNum",tagMapper.countTagsNum());
        map.put("leaveWordNum",leaveMessageMapper.countLeaveMessageMapper());
        map.put("commentNum",commentMapper.countCommentMapper());
        return DataMap.success().setData(map);
    }

    @Override
    public DataMap findTagsCloud() {
        //查询标签
        List<Tags> tags =  tagMapper.findTagsCloud();
       //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",JSONArray.fromObject(tags));
        return DataMap.success(CodeType.FIND_TAGS_CLOUD).setData(map);
    }
}
