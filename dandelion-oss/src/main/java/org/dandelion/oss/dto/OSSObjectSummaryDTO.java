package org.dandelion.oss.dto;

/**
 * TODO OSSObjectSummary
 *
 * @author L
 * @version 1.0
 * @date 2021/12/8 17:52
 */
public class OSSObjectSummaryDTO {

    private String key;
    private long size;
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
