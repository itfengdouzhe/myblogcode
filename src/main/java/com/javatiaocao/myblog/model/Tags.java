package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author IT枫斗者
 * @ClassName Tags.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tags {
    private int id;
    private String tagName;
    private int tagSize;

    public Tags(String tagName, int tagSize) {
        this.tagName = tagName;
        this.tagSize = tagSize;
    }
}
