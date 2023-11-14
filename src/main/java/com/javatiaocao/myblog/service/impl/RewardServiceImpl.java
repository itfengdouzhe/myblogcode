package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.RewardMapper;
import com.javatiaocao.myblog.model.Reward;
import com.javatiaocao.myblog.service.RewardService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.FileUtil;
import com.javatiaocao.myblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName RewardServiceImpl.java
 * @From www.javatiaozao.com
 * @Description 募捐管理
 */
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardMapper rewardMapper;

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description //列表接口
     * @Param []
     * @return com.javatiaocao.myblog.utils.DataMap
     **/
    @Override
    public DataMap getRewardInfo() {
        List<Reward>  rewardList = rewardMapper.getRewardInfo();
        return DataMap.success().setData(rewardList);
    }

    @Override
    public DataMap saveReward(Reward reward, HttpServletRequest request, MultipartFile file) {
        //獲取募捐时间
        String rewardDate = request.getParameter("reward-date");
        //上传附件
        FileUtil fileUtil = new FileUtil();
        String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
        String contentType = file.getContentType();
        String fileEx = contentType.substring(contentType.indexOf("/") + 1);
        TimeUtil timeUtil = new TimeUtil();
        String fileNmae =  timeUtil.getLongTime() + "." + fileEx;
        String fileCatalog = "rewardRecord" + timeUtil.getFormatDateForThree();
        String fileUrl = fileUtil.uploadFile(fileUtil.multipartFileToFile(file, filePath, fileNmae), fileCatalog);
        //处理募捐记录
        reward.setRewardDate(timeUtil.stringToDateThree(rewardDate));
        reward.setRewardUrl(fileUrl);
        reward.setFundRaiser("IT枫斗者");
        reward.setFundraisingPlace("1");
        reward.setFundRaisingSources("2");
        rewardMapper.saveReward(reward);
        return DataMap.success(CodeType.ADD_REWARD_SUCCESS).setData(reward.getId());
    }

    @Override
    public DataMap deleteReward(Reward reward) {
        int result = rewardMapper.deleteReward(reward.getId());
        return DataMap.success(CodeType.DELETE_REWARD_SUCCESS).setData(result);
    }


}
