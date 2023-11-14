package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IT枫斗者
 * @ClassName StatisticsService.java
 * @From www.javatiaozao.com
 * @Description 统计模块接口
 */
public interface StatisticsService {
    DataMap getVisitorNumByPageName(String pageName, HttpServletRequest request);

    DataMap getStatisticsInfo(HttpServletRequest request);
}
