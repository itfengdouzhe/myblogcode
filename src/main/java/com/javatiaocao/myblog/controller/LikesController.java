package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.model.FriendLink;
import com.javatiaocao.myblog.service.LikesService;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.service.impl.RedisServiceImpl;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.StringUtil;
import com.javatiaocao.myblog.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author IT枫斗者
 * @ClassName LikesController.java
 * @From www.javatiaozao.com
 * @Description 点赞管理控制层
 */
@RestController
@Slf4j
public class LikesController {

    @Autowired
    LikesService likesService;
    @Autowired
    private Userservice userservice;
    @Autowired
    private RedisServiceImpl redisServiceImpl;

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description //新增点赞
     * @Param []
     **/
    @GetMapping(value = "/addArticleLike") //value=""  双引号里面一定不要又空格，否则接口到不了后端
    public String addArticleLike(@RequestParam("articleId") String articleId, @AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            //获取userName
            Principal userPrincipal = request.getUserPrincipal();
            String userName = userPrincipal.getName();
            //判断当前用户是否已经点赞
            if (likesService.isLiked(Long.valueOf(articleId), userName)) {
                return JsonResult.fail(CodeType.ARTICLE_HAS_THUMBS_UP).toJSON();
            }
            DataMap data = likesService.updateLikeByArticleId(articleId);
            ArticleLikesRecord articleLikesRecord = new ArticleLikesRecord(Long.valueOf(articleId),userservice.getUserIdByuserName(userName),new TimeUtil().getFormatDateForFive());
            likesService.inertArticleLikesRecord(articleLikesRecord);
            //更新redis中的数据
            redisServiceImpl.readThumbsUpOnRedis(StringUtil.ARTICLE_THUMBS_UP,1);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("addArticleLike Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 点赞列表接口
     * @Param [rows, pageNum]
     **/
    @PostMapping(value = "/getArticleThumbsUp")
    public String getArticleThumbsUp(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            DataMap data = likesService.getArticleThumbsUp(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController getArticleThumbsUp Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 更新全部已读消息
     * @Param []
     **/
    @GetMapping(value = "/readAllThumbsUp")
    public String readAllThumbsUp() {
        try {
            DataMap data = likesService.readAllThumbsUp();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController readAllThumbsUp Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 查询友链列表接口
     * @Param []
     **/
    @PostMapping(value = "/getFriendLink")
    public String getFriendLink() {
        try {
            DataMap data = likesService.getFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController getFriendLink Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //首页友情链接模块接口
     * @Param []
     * @return java.lang.String
     **/
    @PostMapping(value = "/getFriendLinkInfo")
    public String getFriendLinkInfo() {
        try {
            DataMap data = likesService.getFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController getFriendLink Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 根据友链id删除对应友链
     * @Param []
     **/
    @PostMapping(value = "/deleteFriendLink")
    public String deleteFriendLink(@RequestParam("id") String id) {
        try {
            DataMap data = likesService.deleteFriendLink(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController deleteFriendLink Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return java.lang.String
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 添加或者更新友链
     * @Param []
     **/
    @PostMapping(value = "/updateFriendLink")
    public String addOrUpdateFriendLink(@RequestParam("id") String id, @RequestParam("blogger") String blogger, @RequestParam("url") String url) {
        try {
            DataMap data;
            //创建一个友链对象
            FriendLink friendLink = new FriendLink(blogger, url);
            if (StringUtil.BLANK.equals(id)) {
                //新增友链
                data = likesService.addFriendLink(friendLink);
            } else {
                //编辑友链
                data = likesService.updateFriendLink(friendLink, id);
            }
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController addOrUpdateFriendLink Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
