package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.LikesMapper;
import com.javatiaocao.myblog.mapper.VisitorMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Visitor;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringAndArray;
import com.javatiaocao.myblog.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author IT枫斗者
 * @ClassName ArticleServiceImple.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class ArticleServiceImple implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public DataMap insertArticle(Article article) {
       //还要处理一下剩余非空字段
        if(StringUtil.BLANK.equals(article.getOriginalAuthor())){
            article.setOriginalAuthor(article.getAuthor());
        }
        if(StringUtil.BLANK.equals(article.getArticleUrl())){
            //拼接一个url，如果是生产环境：www.javatiaozao.com
            article.setArticleUrl("www.javatiaozao.com" + "/article/" + article.getArticleId());
        }
        //TODO  设置上一篇文章id
        articleMapper.saveArticle(article);
        //给前端响应，封装数据
        //articleTitle updateDate articleUrl author
        HashMap<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("articleUrl","/article/"+article.getArticleId());
        dataMap.put("author",article.getAuthor());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getArticleManagement(int rows, int pageNum) {
        //开启分页插件功能
        PageHelper.startPage(pageNum,rows);
        //查询文章并且存入集合中
        List<Article> articleList = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        //返回数据处理
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();
        //创建一个articleJson
        JSONObject articleJson;
        for (Article article : articleList) {
            articleJson = new JSONObject();
            articleJson.put("id",article.getId());
            articleJson.put("articleId",article.getArticleId());
            articleJson.put("originalAuthor",article.getOriginalAuthor());
            articleJson.put("articleTitle",article.getArticleTitle());
            articleJson.put("articleCategories",article.getArticleCategories());
            articleJson.put("publishDate",article.getPublishDate());
            String pageName = "article/" + article.getArticleId();
//            articleJson.put("visitorNum",getVisitorNum(pageName));//查询真实的 文章的浏览量
            returnJsonArray.add(articleJson);
        }
        returnJsonObject.put("result",returnJsonArray);
//        rows:data['data']['pageInfo']['pageSize'],//每页显示条数
//        pageNum:data['data']['pageInfo']['pageNum'],//当前所在页码
//        pages:data['data']['pageInfo']['pages'],//总页数
//        total:data['data']['pageInfo']['total'],//总记录数
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJsonObject.put("pageInfo",pageJson);
        return DataMap.success().setData(returnJsonObject);
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //查询文章浏览量
     * @Param [pageName]
     * @return long
     **/
    private long getVisitorNum(String pageName) {
//        Visitor visitorNumByPageName = visitorMapper.getVisitorNumByPageName(pageName);
//        if (visitorNumByPageName != null ){
//            return visitorNumByPageName.getVisitorNum();
//        }
        return 0l;
    }

    @Override
    @Transactional
    public DataMap deleteArticle(String id) {
        //我们需要处理文章相关联的所有东西
        //根据id查询文章信息
        Article article = articleMapper.getArticleById(id);
        //逻辑删除：实际数据并没有被删除，破坏查询条件。isDelete:0,1。
        //物理删除：直接将数据库中的数据删除
        articleMapper.updateLastNextId("lastArticleId",article.getLastArticleId(),article.getNextArticleId());
        articleMapper.updateLastNextId("nextArticleId",article.getLastArticleId(),article.getNextArticleId());
        //删除本篇文章
        articleMapper.deleteArticle(article.getArticleId());
        //文章对应的点赞记录、评论、喜欢的记录删除
        //TODO
        return DataMap.success();
    }

    @Override
    public Article getArticleByid(String id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("id",article.getId());
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("articleContent",article.getArticleContent());
        dataMap.put("articleType",article.getId());
        dataMap.put("articleGrade",article.getId());
        dataMap.put("articleUrl",article.getId());
        dataMap.put("originalAuthor",article.getId());
        dataMap.put("articleTags",tagStr); //TODO 待解决
        dataMap.put("articleCategories",article.getId());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap updateArticleById(Article article) {
        Article a =  articleMapper.getArticleByIntId(article.getId());
        if("原创".equals(article.getArticleType())){
             article.setOriginalAuthor(article.getAuthor());
             String url = "http://localhost:9003/" + "article/" + a.getArticleId();
             article.setArticleUrl(url);
        }
        articleMapper.updateArticleById(article);
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("author",article.getAuthor());
        dataMap.put("articleUrl",article.getArticleUrl());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getMyArticles(int rows, int pageNum) {
        //开启分页插件
        PageHelper.startPage(pageNum,rows);
        //查询文章并且存入集合中
        List<Article> articleList = articleMapper.getArticleManagement();
        PageInfo<Article> articlePageInfo = new PageInfo<>(articleList);
        //新建一个集合用户封装文章数据
        ArrayList<Map<String, Object>> newArtilces = new ArrayList<>();
        //循环遍历集合数据
        HashMap<String, Object> map;
        for (Article article : articleList) {
            map = new HashMap<>();
            map.put("thisArticleUrl","/article/"+article.getArticleId());
            map.put("articleTitle", article.getArticleTitle());
            map.put("articleType",article.getArticleType());
            map.put("publishDate",article.getPublishDate());
            map.put("originalAuthor",article.getOriginalAuthor());
            map.put("articleCategories",article.getArticleCategories());
            map.put("articleTabloid",article.getArticleTabloid());//文章摘要
            map.put("articleTags",StringAndArray.stringToArray(article.getArticleTags()));
            map.put("likes",article.getLikes());
            newArtilces.add(map);
        }
        JSONArray returnJsonArray = JSONArray.fromObject(newArtilces);
        HashMap<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageNum",articlePageInfo.getPageNum());
        pageInfoMap.put("pageSize",articlePageInfo.getPageSize());
        pageInfoMap.put("pages",articlePageInfo.getPages());
        pageInfoMap.put("total",articlePageInfo.getTotal());
        pageInfoMap.put("isIsLastPage",articlePageInfo.isIsLastPage());
        pageInfoMap.put("isIsFirstPage",articlePageInfo.isIsFirstPage());
        returnJsonArray.add(pageInfoMap);
        return DataMap.success().setData(returnJsonArray);
    }

    @Override
    public Map<String, String> findArticleTitleByArticleId(long articleId) {
        Article articleInfo =  articleMapper.getArticleByAticleId(articleId);
        HashMap<String, String> articleMap = new HashMap<>();
        if(articleInfo != null){
            articleMap.put("articleTitle", articleInfo.getArticleTitle());
            articleMap.put("articleTabloid", articleInfo.getArticleTitle());
        }
        return articleMap;
    }

    @Override
    public DataMap getArticleByArticleId(long articleId) {
        Article article = articleMapper.getArticleByAticleId(articleId);
        //当用户查看文章详情时候，我在visitor表中插入一条记录
        visitorMapper.insertVistorArticlePage("article/" + articleId);
        return DataMap.success().setData(article);
    }


}
