package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.redis.RedisService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author IT枫斗者
 * @ClassName RedisServiceImpl.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class RedisServiceImpl  {

    @Autowired
    private HashRedisServiceImpl hashRedisServiceImpl;
    @Autowired
    private StringRedisServiceImpl stringRedisServiceImpl;
    @Autowired
    private UserMapper userMapper;

    /**
     * @return long
     * @Author IT枫斗者
     * @From www.javatiaozao.com
     * @Description 增加redis中的访问量
     * @Param [visitor, total_visiTor, i]
     **/
    public long addVisitorNumOnRedis(String visitor, Object field, long i) {
        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(visitor, field);
        if (fieldIsExist) {
            return hashRedisServiceImpl.hashIncrement(visitor, field, i);
        }
        return 0l;
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 在redis中保持访问量
     * @Param [visitor, field, i]
     * @return long
     **/
    public long putVisitorNumOnRedis(String visitor, Object field, Object value) {
       hashRedisServiceImpl.put(visitor,field,value);
       return Long.valueOf(hashRedisServiceImpl.get(visitor,field).toString());
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //从redis中查询访问量
     * @Param [visitor, totalVisitor]
     * @return long
     **/
    public long getVisitorOnRedis(String visitor, String totalVisitor) {
        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(visitor, totalVisitor);
        if(fieldIsExist){
            return Long.valueOf(hashRedisServiceImpl.get(visitor, totalVisitor).toString());
        }
        return 0l;
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //更新redis中的未读量
     * @Param [articleThumbsUp, i]
     * @return void
     **/
    public void readThumbsUpOnRedis(String articleThumbsUp, int i) {
        Boolean readThumbsUpOnRedisIsExist = stringRedisServiceImpl.hasKey(articleThumbsUp);
        if(!readThumbsUpOnRedisIsExist){
            stringRedisServiceImpl.set(articleThumbsUp,1);
        }else {
            stringRedisServiceImpl.stringIncrement(articleThumbsUp,i);
        }

    }




    public DataMap getUserNews(String username) {
         //封装数据
        HashMap<String, Object> map = new HashMap<>();
        //根据当前登陆用户查询用户id
        int userId = userMapper.getUserIdByuserName(username);
        //根据用户id查询redis中的数据
        LinkedHashMap allFieldAndValueMap = (LinkedHashMap)hashRedisServiceImpl.getAllFieldAndValue(String.valueOf(userId));
        JSONObject jsonObject = new JSONObject();
        if(allFieldAndValueMap.size() == 0){
            map.put("result",0);
        }else {
           int allNewsNum = (int)allFieldAndValueMap.get("allNewsNum");
           int commentNum = (int)allFieldAndValueMap.get("commentNum");
           int leaveMessageNum = (int)allFieldAndValueMap.get("leaveMessageNum");
           jsonObject.put("allNewsNum",allNewsNum);
           jsonObject.put("commentNum",commentNum);
           jsonObject.put("leaveMessageNum",leaveMessageNum);
           map.put("result",jsonObject);
        }
        return DataMap.success().setData(map);
    }
}
