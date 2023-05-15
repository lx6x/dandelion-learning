package org.dandelion.netty.common.bean;

import java.io.Serializable;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/16 21:05
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1254513837293006293L;

    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
