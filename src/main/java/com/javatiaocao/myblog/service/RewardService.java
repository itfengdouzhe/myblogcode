package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Reward;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IT枫斗者
 * @ClassName RewardService.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface RewardService {

    DataMap getRewardInfo();

    DataMap saveReward(Reward reward, HttpServletRequest request, MultipartFile file);

    DataMap deleteReward(Reward reward);
}
