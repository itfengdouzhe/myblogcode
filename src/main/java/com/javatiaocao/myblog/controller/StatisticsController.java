package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.StatisticsService;
import com.javatiaocao.myblog.service.impl.RedisServiceImpl;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author IT枫斗者
 * @ClassName StatisticsController.java
 * @From www.javatiaozao.com
 * @Description 统计模块
 */
@Slf4j
@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private RedisServiceImpl redisServiceImpl;
    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //记录网站的访问量以及访客量
     * @Param [request, pageName]
     * @return java.lang.String
     **/
    @GetMapping(value = "/getVisitorNumByPageName")
    public String getVisitorNumByPageName(HttpServletRequest request,@RequestParam("pageName") String pageName){
         try {
             int index = pageName.indexOf("/");
             if(index == -1){
                 pageName = "visitorVolume";
             }
            DataMap data = statisticsService.getVisitorNumByPageName(pageName,request);
             return JsonResult.build(data).toJSON();
         }catch (Exception e){
             log.error("getVisitorNumByPageName",pageName,e);
         }
         return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //仪表盘统计模块
     * @Param [request]
     * @return java.lang.String
     **/
    @GetMapping(value = "/getStatisticsInfo")
    public String getStatisticsInfo(HttpServletRequest request){
        try {
            DataMap data = statisticsService.getStatisticsInfo(request);
            return JsonResult.build(data).toJSON();
        }catch (Exception e){
            log.error("getStatisticsInfo",e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
