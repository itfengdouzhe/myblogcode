package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.FeedBackMapper;
import com.javatiaocao.myblog.model.FeedBack;
import com.javatiaocao.myblog.model.PrivateWord;
import com.javatiaocao.myblog.service.FeedBackService;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName FeedBackServiceImpl.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    FeedBackMapper feedBackMapper;
    @Autowired
    Userservice userservice;

    @Override
    public DataMap getAllFeedback(int rows, int pageNum) {
        //开启分页插件功能
        PageHelper.startPage(pageNum,rows);
        //查询反馈信息并且存入集合中
        List<FeedBack> feedbackList = feedBackMapper.getAllFeedback();
        PageInfo<FeedBack> pageInfo = new PageInfo<>(feedbackList);
        //返回数据处理
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();
        //创建一个feedbackJson
        JSONObject feedbackJson;
        for (FeedBack feedBack : feedbackList) {
            feedbackJson = new JSONObject();
            feedbackJson.put("person",userservice.findUsernameByid(feedBack.getPersonId()));
            feedbackJson.put("feedbackDate",feedBack.getFeedbackDate());
            feedbackJson.put("feedbackContent",feedBack.getFeedbackContent());
            if(feedBack.getContactInfo() == null){
                feedbackJson.put("contactInfo", StringUtil.BLANK);
            }else {
                feedbackJson.put("contactInfo",feedBack.getContactInfo());
            }
            returnJsonArray.add(feedbackJson);
        }
        //处理外层封装
        returnJsonObject.put("result",returnJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJsonObject.put("pageInfo",pageJson);
        return DataMap.success().setData(returnJsonObject);
    }

//    @Override
//    public DataMap getAllPrivateWord() {
//        List<PrivateWord> privateWordList = feedBackMapper.getAllPrivateWord();
//        //封装数据
//        JSONObject returnJson = new JSONObject();
//        JSONArray returnjsonArray = new JSONArray();
//        JSONObject userJson;
//        for (PrivateWord privateWord : privateWordList) {
//            userJson = new JSONObject();
//            userJson.put("privateWord",privateWord.getPrivateWord());
//            userJson.put("publisher",userservice.findUsernameByid(privateWord.getPublisherId()));
//            userJson.put("publisherDate",privateWord.getPublisherDate());
//            userJson.put("id",privateWord.getId());
//            if(privateWord.getReplyContent() == null){
//               userJson.put("replyContent",StringUtil.BLANK);
//               //回复的人，待定
//            }else {
//                userJson.put("replyContent",privateWord.getReplyContent());
//            }
//            returnjsonArray.add(userJson);
//        }
//        //外层封装
//        returnJson.put("result",returnjsonArray);
//        return DataMap.success().setData(returnJson);
//    }
    @Override
    public DataMap getAllPrivateWord() {

        List<PrivateWord> privateWords = feedBackMapper.getAllPrivateWord();

        JSONObject returnJson = new JSONObject();
        JSONObject userJson;
        JSONArray allJsonArray = new JSONArray();
        JSONObject newUserJson;

        List<String> publishers = new ArrayList<>();
        String publisher;
        for(PrivateWord privateWord : privateWords){
            userJson = new JSONObject();
            userJson.put("privateWord", privateWord.getPrivateWord());
            publisher = userservice.findUsernameByid(privateWord.getPublisherId());
            userJson.put("publisher", publisher);
            userJson.put("publisherDate", privateWord.getPublisherDate());
            userJson.put("id", privateWord.getId());
            if(privateWord.getReplyContent() == null){
                userJson.put("replier", StringUtil.BLANK);
                userJson.put("replyContent", StringUtil.BLANK);
            } else {
                userJson.put("replyContent",privateWord.getReplyContent());
                userJson.put("replier",userservice.findUsernameByid(privateWord.getReplierId()));
            }
            if(!publishers.contains(publisher)){
                publishers.add(publisher);
                newUserJson = new JSONObject();
                newUserJson.put("publisher",publisher);
                newUserJson.put("content",new JSONArray());
                allJsonArray.add(newUserJson);
            }
            for(int i=0;i<allJsonArray.size();i++){
                JSONObject arrayUser = (JSONObject) allJsonArray.get(i);
                if(arrayUser.get("publisher").equals(publisher)){
                    JSONArray jsonArray = (JSONArray) arrayUser.get("content");
                    jsonArray.add(userJson);
                    arrayUser.put("publisher", publisher);
                    arrayUser.put("content", jsonArray);
                    allJsonArray.remove(i);

                    allJsonArray.add(arrayUser);
                    break;
                }
            }
        }
        returnJson.put("result",allJsonArray);
        return DataMap.success().setData(returnJson);
    }
}
