package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

/**
 * @author IT枫斗者
 * @ClassName IndexService.java
 * @From www.javatiaozao.com
 * @Description 首页的实现接口
 */
public interface IndexService {
    DataMap getNewComment(int rows, int pageNum);

    DataMap getNewLeaveWord(int rows, int pageNum);

    DataMap getSiteInfo();

    DataMap findTagsCloud();


}
