package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @ClassName CategoriesController.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@RestController
@Slf4j
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //查询分类列表接口
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/getArticleCategories")
    public String getArticleCategories(){
        try {
            DataMap data = categoriesService.getArticleCategories();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 根据分类名称，新增或者删除分类
     * @Param [categoryName, type]
     * @return java.lang.String
     **/
    @PostMapping(value = "/updateCategory")
    public String updateCategory(@RequestParam(value = "categoryName") String categoryName,@RequestParam(value = "type")int type){
        try {
            DataMap data = categoriesService.updateCategory(categoryName,type);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 查询分类下拉接口(发表文章页面)
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/findCategoriesName")
    public String findCategoriesName(){
        try {
            DataMap data = categoriesService.findCategoriesNames();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //博客文章分类列表
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/getCategoryArticle")
    public String getCategoryArticle(@RequestParam(value = "category") String category,@RequestParam(value = "rows") int rows,@RequestParam(value = "pageNum") int pageNum){
        try {
            if(!category.equals(StringUtil.BLANK)){
                category =  TransCodingUtil.unicodeToString(category);
            }
            DataMap data = categoriesService.getCategoryArticle(category,rows,pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getCategoryArticle Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description // 查询博客分类
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/findCategoriesNameAndArticleNum")
    public String findCategoriesNameAndArticleNum(){
        try {
            DataMap data = categoriesService.findCategoriesNameAndArticleNum();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController findCategoriesNameAndArticleNum Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //获取当前页面的留言
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/getPageLeaveMessage")
    public String getPageLeaveMessage(@RequestParam("pageName") String pageName, @AuthenticationPrincipal Principal principal, HttpServletRequest request){
        String userName = null;
        try {
            //问题一：需要登陆才能获取到principal
            //问题二：object Object封装的问题
//           1: userName = principal.getName();
//            Principal userPrincipal = request.getUserPrincipal();
            if(!Objects.isNull(request.getUserPrincipal())){
                userName = request.getUserPrincipal().getName();
            }
            DataMap data = categoriesService.getPageLeaveMessage(pageName,userName);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getPageLeaveMessage Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/publishLeaveMessage")
    public String publishLeaveMessage(@RequestParam(value = "leaveMessageContent") String leaveMessageContent,@RequestParam(value = "pageName") String pageName,@AuthenticationPrincipal Principal principal, HttpServletRequest request){
       String userName = null;
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if(!Objects.isNull(userPrincipal)){
                userName = request.getUserPrincipal().getName();
            }
            //提交留言
            categoriesService.publishLeaveMessage(leaveMessageContent,userName,pageName);
            //调用查询留言接口并且返回对应封装数据
            DataMap data = categoriesService.getPageLeaveMessage(pageName,userName);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getArticleCategories Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
