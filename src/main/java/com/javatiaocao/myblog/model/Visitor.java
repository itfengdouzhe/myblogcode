package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author IT枫斗者
 * @ClassName visitor.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    private int id;
    private long visitorNum;
    private String pageName;
}
