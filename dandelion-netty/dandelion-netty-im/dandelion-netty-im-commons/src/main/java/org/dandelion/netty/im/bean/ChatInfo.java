package org.dandelion.netty.im.bean;

import java.io.Serializable;

/**
 * TODO chat
 *
 * @author L
 * @version 1.0
 * @date 2022/5/18 14:49
 */
public class ChatInfo implements Serializable {

    private static final long serialVersionUID = 3828175090814735538L;

    /**
     * message type
     */
    private String command;

    /**
     * send time
     */
    private Long time;

    /**
     * send id
     */
    private String userId;

    /**
     * content
     */
    private String content;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "command='" + command + '\'' +
                ", time=" + time +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}
