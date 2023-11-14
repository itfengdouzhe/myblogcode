package com.javatiaocao.myblog.model;

import lombok.Data;

/**
 * @author: IT枫斗者
 * @Date: 2022/7/23 17:17
 * Describe: 反馈
 */
@Data
public class FeedBack {

    private int id;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 反馈人
     */
    private int personId;

    /**
     * 反馈时间
     */
    private String feedbackDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedBack feedBack = (FeedBack) o;

        if (id != feedBack.id) return false;
        if (personId != feedBack.personId) return false;
        if (feedbackContent != null ? !feedbackContent.equals(feedBack.feedbackContent) : feedBack.feedbackContent != null)
            return false;
        if (contactInfo != null ? !contactInfo.equals(feedBack.contactInfo) : feedBack.contactInfo != null)
            return false;
        return feedbackDate != null ? feedbackDate.equals(feedBack.feedbackDate) : feedBack.feedbackDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (feedbackContent != null ? feedbackContent.hashCode() : 0);
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        result = 31 * result + personId;
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        return result;
    }
}
