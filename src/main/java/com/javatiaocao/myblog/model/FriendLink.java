package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author IT枫斗者
 * @ClassName FriendLink.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendLink {
    private int id;
    private String blogger;
    private String url;

    public FriendLink(String blogger, String url) {
        this.blogger = blogger;
        this.url = url;
    }
}
