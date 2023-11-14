package com.javatiaocao.myblog.service.impl;

//import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.CategoriesMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.model.LeaveMessage;
import com.javatiaocao.myblog.model.UserReadNews;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringAndArray;
import com.javatiaocao.myblog.utils.StringUtil;
import com.javatiaocao.myblog.utils.TimeUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName CategoriesServiceImpl.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private Userservice userservice;
    @Autowired
    private HashRedisServiceImpl hashRedisServiceImpl;

    @Override
    public DataMap getArticleCategories() {
//        List<CategoriesService> a;
        //查询分类数据
        List<Categories>  categories = categoriesMapper.getArticleCategories();
        //处理我们查询出来的数据
        JSONObject returnJson = new JSONObject();//作为最后返回前端的json格式
        JSONArray jsonArray = new JSONArray();
        for (Categories category : categories) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", category.getId());
            jsonObject.put("categoryName",category.getCategoryName());
            //将封装好的jsonobject放入到数组中
            jsonArray.add(jsonObject);
        }
        returnJson.put("result",jsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap updateCategory(String categoryName, int type) {
        int isExistCategoryName = categoriesMapper.findIsExisByCategoryName(categoryName);
        //判断是新增还是删除
        if(type == 1){
            //如果是1则为新增
            if(isExistCategoryName == 0){
               //说明没有查询到分类
                Categories categories = new Categories();
                categories.setCategoryName(categoryName);
                categoriesMapper.savaCategories(categories);
                int newCategoriesId = categoriesMapper.findIsExisByCategoryName(categoryName);
                return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS).setData(newCategoriesId);
            }
        }else {
            //删除
            if(isExistCategoryName != 0){
                //TODO 查询分类下面对应有多少遍文章
                //如果查询出来的文章数量不为空，则返回提示“分类下存在文章，删除失败"
                categoriesMapper.deteleCategory(categoryName);
                return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
            }
        }
        return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);
    }

    @Override
    public DataMap findCategoriesNames() {
        //下拉接口我们只要对应的一个一个字符串对象即可
        List<String> categorieNames = categoriesMapper.findCategoriesNames();
        return DataMap.success().setData(categorieNames);
    }

    @Override
    public DataMap getPageLeaveMessage(String pageName, String userName) {
        //获取留言
        List<LeaveMessage>  LeaveMessageList = categoriesMapper.getPageLeaveMessage(pageName,0);
        JSONObject leaveMessageJson;
        List<LeaveMessage> LeaveMessageReplies;
        JSONArray leaveMessageJsonArray = new JSONArray();
        //循环留言列表
        for (LeaveMessage leaveMessage : LeaveMessageList) {
             leaveMessageJson = new JSONObject();
            leaveMessageJson.put("id",leaveMessage.getId());
            leaveMessageJson.put("avatarImgUrl",userservice.findAvatarImgUrlByAnswereId(leaveMessage.getAnswererId()));
            leaveMessageJson.put("answerer",userservice.findUsernameByid(leaveMessage.getAnswererId()));
            leaveMessageJson.put("likes",leaveMessage.getLikes());
            leaveMessageJson.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
            leaveMessageJson.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
            if(null == userName){
               leaveMessageJson.put("isLiked",0);
            }else {
                leaveMessageJson.put("isLiked",1);
            }
            //查询回复留言集合
            JSONObject replyJson;
//            JSONArray replyJsonArray;
            JSONArray replyJsonArray = new JSONArray();
            LeaveMessageReplies = categoriesMapper.findLeaveMessageReplyByPageNameAndPid(pageName,leaveMessage.getId());
            for (LeaveMessage leaveMessageReply : LeaveMessageReplies) {
                replyJson = new JSONObject();
                replyJson.put("id",leaveMessageReply.getId());
                replyJson.put("answerer",userservice.findUsernameByid(leaveMessage.getAnswererId()));
                replyJson.put("respondent",userservice.findUsernameByid(leaveMessage.getRespondentId()));
                replyJson.put("leaveMessageContent",leaveMessageReply.getLeaveMessageContent());
                replyJson.put("leaveMessageDate",leaveMessageReply.getLeaveMessageDate());
                replyJsonArray.add(replyJson);
            }
            leaveMessageJson.put("replies",replyJsonArray);
            leaveMessageJsonArray.add(leaveMessageJson);
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("result", leaveMessageJsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap getCategoryArticle(String category, int rows, int pageNum) {
        List<Article> categoryList;
        //开启分页
        PageHelper.startPage(pageNum,rows);
        //当category为空时查询所有分类
        if(StringUtil.BLANK.equals(category)){
            categoryList = categoriesMapper.getCategoryArticles();
            category = "全部分类";
        }else {
            categoryList= categoriesMapper.getCategoryArticle(category);
        }
        PageInfo<Article> articlePageInfo = new PageInfo<Article>(categoryList);
        //封装数据
        JSONArray articelJsonArray = new JSONArray();
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",articlePageInfo.getPageNum());
        pageJson.put("pageSize",articlePageInfo.getPageSize());
        pageJson.put("total",articlePageInfo.getTotal());
        pageJson.put("pages",articlePageInfo.getPages());
        pageJson.put("isIsLastPage",articlePageInfo.isIsLastPage());
        pageJson.put("isIsFirstPage",articlePageInfo.isIsFirstPage());
        //封装时间线对应的数据成JsonArray格式
        articelJsonArray = timeLineToJsonArray(articelJsonArray,categoryList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",articelJsonArray);
        jsonObject.put("category",category);
        jsonObject.put("pageInfo",pageJson);
        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap findCategoriesNameAndArticleNum() {
        List<String> categorisName = categoriesMapper.findCategoriesNameAndArticleNum();
        //封装数据
        JSONArray categoryJsonArray = new JSONArray();
        JSONObject categoriJson;
        for (String categoryName : categorisName) {
            categoriJson =  new JSONObject();
            categoriJson.put("categoryName",categoryName);
            categoriJson.put("categoryArticleNum",articleMapper.countArticleNumByCategory(categoryName));
            categoryJsonArray.add(categoriJson);
        }
        JSONObject returnjsonObject = new JSONObject();
        returnjsonObject.put("result",categoryJsonArray);
        return DataMap.success().setData(returnjsonObject);
    }

    @Override
    public void publishLeaveMessage(String leaveMessageContent, String userName, String pageName) {
        //处理时间
        TimeUtil timeUtil = new TimeUtil();
        String newDateStr = timeUtil.getFormatDateForFive();
        //创建实体类
        LeaveMessage leaveMessage = new LeaveMessage(pageName,userservice.getUserIdByuserName(userName),userservice.getUserIdByuserName(userName),
                newDateStr,leaveMessageContent );
        categoriesMapper.publishLeaveMessage(leaveMessage);
        //更新redis中的数据
        addRedisNews(leaveMessage);
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //在redis中增加一个未读的评论数
     * @Param [leaveMessage]
     * @return void
     **/
    private void addRedisNews(LeaveMessage leaveMessage) {
        if(leaveMessage.getRespondentId() != leaveMessage.getAnswererId()){
            Boolean isExistKey = hashRedisServiceImpl.hasKey(leaveMessage.getRespondentId() + StringUtil.BLANK);
            if(!isExistKey){
                UserReadNews userReadNews = new UserReadNews(1,0,1);
                hashRedisServiceImpl.put(String.valueOf(leaveMessage.getRespondentId()),userReadNews,UserReadNews.class);
            }else {
                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+StringUtil.BLANK,"allNewsNum",1);
                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+StringUtil.BLANK,"leaveMessageNum",1);
            }

        }
    }

    private JSONArray timeLineToJsonArray(JSONArray articelJsonArray, List<Article> categoryList) {
        JSONObject articleJson;
        for (Article article : categoryList) {
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            articleJson = new JSONObject();
            articleJson.put("articleId",article.getArticleId());
            articleJson.put("articleTitle",article.getArticleTitle());
            articleJson.put("publishDate",article.getPublishDate());
            articleJson.put("articleCategories",article.getArticleCategories());
            articleJson.put("articleTags",tagsArray);
            articleJson.put("originalAuthor",article.getOriginalAuthor());
            articelJsonArray.add(articleJson);
        }
        return articelJsonArray;
    }


}
