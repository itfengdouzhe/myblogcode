package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.Reward;
import com.javatiaocao.myblog.service.RewardService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.FileUtil;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IT枫斗者
 * @ClassName RewardController.java
 * @From www.javatiaozao.com
 * @Description 募捐管理控制层
 */
@RestController
@Slf4j
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping(value = "/getRewardInfo")
    public String getRewardInfo(){
        try {
            DataMap data = rewardService.getRewardInfo();
            return JsonResult.build(data).toJSON();
        }catch (Exception e){
           log.error("getRewardInfo exception",e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //添加募捐記錄
     * @Param [file, request, reward]
     * @return java.lang.String
     **/
    @PostMapping(value = "/addReward")
    public String addReward(@RequestParam("file") MultipartFile file, HttpServletRequest request, Reward reward){
        try {
           DataMap dataMap =  rewardService.saveReward(reward,request,file);
           return JsonResult.build(dataMap).toJSON();
        }catch (Exception e){
            log.error("addReward Exception",e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //募捐记得删除功能
     * @Param [reward]
     * @return java.lang.String
     **/
    @GetMapping(value = "/deleteReward")
    public String deleteReward(Reward reward){
        try {
            DataMap dataMap =  rewardService.deleteReward(reward);
            return JsonResult.build(dataMap).toJSON();
        }catch (Exception e){
            log.error("deleteReward Exception",e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
