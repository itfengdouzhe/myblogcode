package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.TagService;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

/**
 * @author IT枫斗者
 * @ClassName ArticleController.java
 * @From www.javatiaozao.com
 * @Description 文章模块控制层
 */
@RestController
@Slf4j
public class ArticleController {

    @Autowired
    TagService tagService;

    @Autowired
    ArticleService articleService;

    @Autowired
    Userservice userservice;


    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 获取文章列表接口
     * @Param [rows, pageNum]
     **/
    @PostMapping(value = "/getArticleManagement")
    public String getArticleManagement(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            DataMap data = articleService.getArticleManagement(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 获取项目首页文章分页列表接口
     * @Param [rows, pageNum]
     * @return java.lang.String
     **/
    @PostMapping(value = "/myArticles")
    public String getMyArticles(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            DataMap data = articleService.getMyArticles(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 获取草稿文章或者修改文章的内容
     * @Param [request]
     **/
    @GetMapping(value = "/getDraftArticle")
    public String getDraftArticle(HttpServletRequest request) {
        try {
            String id = (String) request.getSession().getAttribute("id");
            //判断文章是否是修改
            if (id != null) {
                Article article = articleService.getArticleByid(id);
                int lastIndexOf = article.getArticleTags().lastIndexOf(",");
                //解决思路，当前情况要分为两种。1：如果tags是,分割的。按照现在的写法。2：如果tags只一个值，没有,分割，这个时候需要判断
                if(lastIndexOf != -1){
                    String[] tagStr = StringAndArray.stringToArray(article.getArticleTags().substring(0, lastIndexOf));
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, tagService.getTagsSizeByName(tagStr[0]));
                    return JsonResult.build(dataMap).toJSON();
                }else {
                    String[] tagStr = StringAndArray.stringToArray(article.getArticleTags());
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, tagService.getTagsSizeByName(tagStr[0]));
                    return JsonResult.build(dataMap).toJSON();
                }
            }
            //判断是否写文章登陆超时
            if (request.getSession().getAttribute("article") != null) {
                Article article = (Article) request.getSession().getAttribute("article");
                String[] tagStr = (String[]) request.getSession().getAttribute("articleTags");
                String articleGrade = (String) request.getSession().getAttribute("articleGrade");
                if (!StringUtil.BLANK.equals(articleGrade) && articleGrade != null) {
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, Integer.parseInt(articleGrade));
                    request.getSession().removeAttribute("article");
                    request.getSession().removeAttribute("articleTags");
                    request.getSession().removeAttribute("articleGrade");
                    return JsonResult.build(dataMap).toJSON();
                }
            }
            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 删除文章
     * @Param [id]
     **/
    @GetMapping(value = "/deleteArticle")
    public String deleteArticle(@RequestParam(value = "id") String id) {
        try {
            if (StringUtil.BLANK.equals(id) || id == null) {
                return JsonResult.fail(CodeType.DELETE_ARTICLE_FAIL).toJSON();
            }
            DataMap data = articleService.deleteArticle(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 验证当前登陆用户是否有编辑或者发布文章权限
     * @Param [principal, request]
     * @return java.lang.String
     **/
    @GetMapping(value = "/canYouWrite")
    public String canYouWrite(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if(!Objects.isNull(userPrincipal)){
                String username = userPrincipal.getName();
                boolean b = userservice.userNameIsExist(username);
                if(b){//如果b为true则是当前系统注册用户，可以编写文章
                    return JsonResult.success().toJSON();
                }
            }
            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 发布博客文章/编辑文文章
     * @Param [article, articleGrade, principal, request]
     **/
    @PostMapping(value = "/publishArticle")
    public String publishArticle(Article article, @RequestParam("articleGrade") String articleGrade,
                                 @AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            //获取文章作者
//            String name = "IT枫斗者";
            Principal userPrincipal = request.getUserPrincipal();
            String name = userPrincipal.getName();
//            String name1 = principal.getName();
            //获取html代码中生成的文章摘要 根据关键字“articleHtmlContent”
            //赋值到文章对应的articleTabloid
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String articleHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
            article.setArticleTabloid(articleHtmlContent + "...");
            //处理前端传递过来的标签，比如对些特殊关键字、空格进行处理
//            String articleTagsValue = request.getParameter("articleTagsValue");//需要的是一个字符串数组，所以不能用字符串
            String[] articleTags = request.getParameterValues("articleTagsValue");
            String[] newArticleTags = new String[articleTags.length + 1];
            for (int i = 0; i < articleTags.length; i++) {
                //去除特殊关键字、空格进行处理
                newArticleTags[i] = articleTags[i].replaceAll("<br>", StringUtil.BLANK).replaceAll("$nbsp", StringUtil.BLANK)
                        .replaceAll("\\s", StringUtil.BLANK).trim();
            }
            newArticleTags[articleTags.length] = article.getArticleType();
            //插入对应的标签实体类
            tagService.insertTags(newArticleTags, Integer.parseInt(articleGrade));
            //通过是否有文章id判断当前接口是处理新增文章还是修改文章
            //有id是修改，没有id代表是新增
            String id = request.getParameter("id");
            if (!StringUtil.BLANK.equals(id) && id != null) {
                //是修改文章
                TimeUtil timeUtil = new TimeUtil();
                String updateDate = timeUtil.getFormatDateForThree();
                article.setArticleTags(StringAndArray.arrayToString(newArticleTags));//将数组转成string
                article.setUpdateDate(updateDate);
                article.setId(Integer.parseInt(id));
                article.setAuthor(name);
                DataMap data = articleService.updateArticleById(article);
                return JsonResult.build(data).toJSON();
            }
            //否则就是新增文章
            TimeUtil timeUtil = new TimeUtil();
            String formatDateForThree = timeUtil.getFormatDateForThree();
            long articleId = timeUtil.getLongTime();
            //封装实体类
            article.setArticleId(articleId);
            article.setAuthor(name);
            article.setArticleTags("StringAnd");//TODO
            article.setPublishDate(formatDateForThree);
            article.setUpdateDate(formatDateForThree);
            DataMap data = articleService.insertArticle(article);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

   ///getArticleByArticleId
    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //根据文章id查询文章内容
     * @Param [articleId]
     * @return java.lang.String
     **/
   @PostMapping(value = "/getArticleByArticleId")
   public String getArticleByArticleId(@RequestParam(value = "articleId") String articleId) {
       try {

           DataMap data = articleService.getArticleByArticleId(Long.parseLong(articleId));
           return JsonResult.build(data).toJSON();
       } catch (Exception e) {
           log.error("ArticleController getArticleByArticleId Exception", e);
       }
       //失败了
       return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
   }
}
