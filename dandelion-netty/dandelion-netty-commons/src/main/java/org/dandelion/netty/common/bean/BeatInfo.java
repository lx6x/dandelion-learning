package org.dandelion.netty.common.bean;

import io.netty.util.CharsetUtil;

import java.io.Serializable;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class BeatInfo implements Serializable {

    private String id;
    private String content;
    public BeatInfo(){

    }

    public BeatInfo(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BeatInfo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
