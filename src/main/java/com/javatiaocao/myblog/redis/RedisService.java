package com.javatiaocao.myblog.redis;

import com.javatiaocao.myblog.utils.DataMap;

/**
 * @author IT枫斗者
 * @ClassName RedisService.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface RedisService {
    /**
     * 判断key是否存在
     */
    Boolean hasKey(String key);



}
