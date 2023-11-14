package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Reward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName RewardMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
public interface RewardMapper {

    List<Reward> getRewardInfo();

    void saveReward(Reward reward);

    int deleteReward(int id);
}
