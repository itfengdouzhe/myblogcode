package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.mapper.VisitorMapper;
import com.javatiaocao.myblog.model.Visitor;
import com.javatiaocao.myblog.redis.RedisService;
import com.javatiaocao.myblog.service.StatisticsService;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author IT枫斗者
 * @ClassName StatisticsImpl.java
 * @From www.javatiaozao.com
 * @Description 统计模块实现层
 */
@Service
public class StatisticsImpl implements StatisticsService {
    //总访问量
    private static final String TOTAL_VISITor = "totalVisitor";
    //当前页面访问量
    private static final String PAGE_VISITor = "pageVisitor";

    @Autowired
    private RedisServiceImpl redisServiceImpl;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StringRedisServiceImpl stringRedisServiceImpl;

    @Override
    public DataMap getVisitorNumByPageName(String pageName, HttpServletRequest request) {
        //创建一个Map
        HashMap<String, Object> dataMap = new HashMap<>();
        //获取当前访问页面的visitor
        String visitor = (String) request.getSession().getAttribute(pageName);
        //判断session生命周期中是否浏览过当前page，则增加访问量
        if (visitor == null) {
            request.getSession().setAttribute(pageName, "yes");
        }
        //增加当前页面的访问人数
        long pageVisitor = updatePageVisitor(visitor, pageName);
        //增加总访问人数
        long totalVisitor = redisServiceImpl.addVisitorNumOnRedis(StringUtil.VISITOR, TOTAL_VISITor, 1);
        if (totalVisitor == 0) {
            totalVisitor = visitorMapper.getTotalVisitor(); //为什么数据库中会有数据
            //redis中要去put一下
            totalVisitor = redisServiceImpl.putVisitorNumOnRedis(StringUtil.VISITOR, TOTAL_VISITor, totalVisitor + 1);
        }
        dataMap.put(TOTAL_VISITor, totalVisitor);
        dataMap.put(PAGE_VISITor, pageVisitor);
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getStatisticsInfo(HttpServletRequest request) {
        HashMap<String, Object> dataMap = new HashMap<>();
        //总访问量
        long allVisitor = redisServiceImpl.getVisitorOnRedis(StringUtil.VISITOR, "totalVisitor");
        //昨日访问量
        long yesterdayVisitor = redisServiceImpl.getVisitorOnRedis(StringUtil.VISITOR,"yesterdayVisitor");
        dataMap.put("allVisitor",allVisitor);
        dataMap.put("yesterdayVisitor",yesterdayVisitor);
        dataMap.put("allUser",userMapper.countUserNum());
        dataMap.put("articleNum",articleMapper.countArticleNum());
        //查询点赞量 TODO
        if(stringRedisServiceImpl.hasKey(StringUtil.ARTICLE_THUMBS_UP)){
           int thumsbsNum = (int) stringRedisServiceImpl.get(StringUtil.ARTICLE_THUMBS_UP);
           dataMap.put("articleThumbsUpNum",thumsbsNum);
        }else {
            dataMap.put("articleThumbsUpNum",0);
        }
        return DataMap.success().setData(dataMap);
    }

    /**
     * @return long
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description //更新当前页面访问量
     * @Param [visitor, pageName]
     **/
    private long updatePageVisitor(String visitor, String pageName) {

        Visitor thisVisitor;
        Long pageVistitor;
        //session生命周期内没有浏览器改page，则增加访问量
        if (visitor == null) {
            pageVistitor = redisServiceImpl.addVisitorNumOnRedis(StringUtil.VISITOR, pageName, 1);
            //如果redis中没有命中，则去数据库中取
            if (pageVistitor == 0) {
                thisVisitor = visitorMapper.getVisitorNumByPageName(pageName);
                if (thisVisitor != null) {
                    redisServiceImpl.putVisitorNumOnRedis(StringUtil.VISITOR, pageName, thisVisitor.getVisitorNum() + 1);
                    return redisServiceImpl.getVisitorOnRedis(StringUtil.VISITOR,pageName);
                } else {
                    return 0l;
                }
            }
        }
        return 0l;
    }
}
