package com.javatiaocao.myblog.controller;


import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.redis.RedisService;
import com.javatiaocao.myblog.service.IndexService;
import com.javatiaocao.myblog.service.impl.RedisServiceImpl;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author: IT枫斗者
 * @Date: 2018/6/16 16:01
 * Describe:
 */
@RestController
@Slf4j
public class IndexControl {

    @Autowired
    private IndexService indexService;
    @Autowired
    private RedisServiceImpl redisService;

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //首页获取最新的评论
     * @Param [rows, pageNum]
     * @return java.lang.String
     **/
    @GetMapping(value = "/newComment")
    public String getNewComment(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            DataMap data = indexService.getNewComment(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("IndexControl getNewComment Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //获取首页最新的留言
     * @Param [rows, pageNum]
     * @return java.lang.String
     **/
    @GetMapping(value = "/newLeaveWord")
    public String getNewLeaveWord(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            DataMap data = indexService.getNewLeaveWord(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("IndexControl getNewLeaveWord Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 查询首页网站基本信息
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/getSiteInfo")
    public String getSiteInfo() {
        try {
            DataMap data = indexService.getSiteInfo();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("IndexControl getSiteInfo Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 获取首页标签云
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/findTagsCloud")
    public String findTagsCloud() {
        try {
            DataMap data = indexService.findTagsCloud();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("IndexControl findTagsCloud Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 获得登录用户未读消息
     * @Param []
     * @return java.lang.String
     **/
    @PostMapping(value = "/getUserNews")
    public String getUserNews(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if(!Objects.isNull(userPrincipal)){
                String username = userPrincipal.getName();
                DataMap data = redisService.getUserNews(username);
                return JsonResult.build(data).toJSON();
            }else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("result",0);
                return JsonResult.build(DataMap.success().setData(map)).toJSON();
            }
        } catch (Exception e) {
            log.error("IndexControl getUserNews Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
