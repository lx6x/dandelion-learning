package org.dandelion.netty.common.bean;

import io.netty.util.CharsetUtil;

import java.io.Serializable;

/**
 * @author liujunfei
 * @date 2023/5/15
 */
public class BeatInfo implements Serializable {

    private Long id;
    private String content;
    public BeatInfo(){

    }

    public BeatInfo(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
