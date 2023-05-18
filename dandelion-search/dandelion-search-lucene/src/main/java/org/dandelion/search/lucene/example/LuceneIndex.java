package org.dandelion.search.lucene.example;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author liujunfei
 * @date 2023/5/13
 */
public class LuceneIndex {

    public static void main(String[] args) throws IOException {
        create();
    }

    /**
     * 创建索引
     *
     * @author liujunfei
     * @date 2023/5/13
     **/
    public static void create() throws IOException {
        // 原始文档
        File path = new File("D:\\lucene\\data");
        // 生成索引文件目录
        Directory directory = FSDirectory.open(Paths.get("D:\\lucene\\index"));
        // 创建分词器 IK 分词器
        IKAnalyzer ikAnalyzer = new IKAnalyzer();
        // 创建 IndexWriterConfig
        IndexWriterConfig writerConfig = new IndexWriterConfig(ikAnalyzer);
        // 是否使用复合索引格式
        writerConfig.setUseCompoundFile(false);
        // 创建 IndexWriter
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
        File[] files = path.listFiles();
        for (File file : files) {
            // 创建 Document
            Document document = new Document();
            // 文件名称
            document.add(new TextField("fileName", file.getName(), Field.Store.YES));
            // 文件内容
            document.add(new TextField("content", FileUtils.readFileToString(file, "UTF-8"), Field.Store.YES));
            // 文件路径
            document.add(new StoredField("path", file.getPath()));
            // 文件大小
            document.add(new NumericDocValuesField("size", FileUtils.sizeOf(file)));
            // Document 写入索引
            indexWriter.addDocument(document);
        }
        if (indexWriter != null) {
            indexWriter.close();
            indexWriter = null;
        }
    }

    public static void search(){

    }
}
