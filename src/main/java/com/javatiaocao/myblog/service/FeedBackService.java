package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

/**
 * @author IT枫斗者
 * @ClassName FeedBackService.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface FeedBackService {
    DataMap getAllFeedback(int rows, int pageNum);

    DataMap getAllPrivateWord();
}
