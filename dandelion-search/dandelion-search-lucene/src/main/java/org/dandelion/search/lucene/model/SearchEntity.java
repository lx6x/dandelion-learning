package org.dandelion.search.lucene.model;

/**
 * @date 2023/5/22
 */
public class SearchEntity {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件内容
     */
    private String content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
