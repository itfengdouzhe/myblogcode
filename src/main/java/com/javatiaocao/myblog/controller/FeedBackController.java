package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.FeedBackService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author IT枫斗者
 * @ClassName FeedBackController.java
 * @From www.javatiaozao.com
 * @Description 反馈模块
 */
@RestController
@Slf4j
public class FeedBackController {

    @Autowired
    FeedBackService feedBackService;

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 分页获取反馈列表
     * @Param [rows, pageNum]
     * @return java.lang.String
     **/
    @GetMapping(value = "/getAllFeedback")
    public String getAllFeedback(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {
        try {
            DataMap data = feedBackService.getAllFeedback(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("FeedBackController getAllFeedback Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 查询所有的悄悄话
     * @Param []
     * @return java.lang.String
     **/
    @PostMapping(value = "/getAllPrivateWord")
    public String getAllPrivateWord() {
        try {
            DataMap data = feedBackService.getAllPrivateWord();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("FeedBackController getAllPrivateWord Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }



}
