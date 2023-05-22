package org.dandelion.search.lucene.example;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @date 2023/5/13
 */
public class LuceneIndex {

    public static void main(String[] args) throws Exception {
//        create();
        search();
    }

    /**
     * 创建索引
     *
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
        if (files != null) {
            for (File file : files) {
                // 创建 Document
                Document document = new Document();
                // 文件名称 设置文档格式
                document.add(new TextField("fileName", file.getName(), Field.Store.YES));
                // 文件内容
                document.add(new TextField("content", FileUtils.readFileToString(file, "GBK"), Field.Store.YES));
                // 文件路径
                document.add(new StoredField("path", file.getPath()));
                // 文件大小
                document.add(new NumericDocValuesField("size", FileUtils.sizeOf(file)));
                // Document 写入索引
                indexWriter.addDocument(document);
            }
        }
        indexWriter.close();
    }

    /**
     * 检索查询
     *
     * @date 2023/5/18
     **/
    public static void search() throws IOException, ParseException {
        // 索引文件存放目录
        Directory directory = FSDirectory.open(Paths.get("D:\\lucene\\index"));
        // 创建 IndexReader
        IndexReader indexReader = DirectoryReader.open(directory);
        // 创建 IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 创建 ik 分词器
        IKAnalyzer ikAnalyzer = new IKAnalyzer();
        QueryParser parser = new QueryParser("content", ikAnalyzer);
        // 创建查询
        Query query = parser.parse("大师");
        // 获取搜索结果
        ScoreDoc[] scoreDoc = indexSearcher.search(query, 10).scoreDocs;
        for (ScoreDoc doc : scoreDoc) {
            Document document = indexSearcher.doc(doc.doc);
            String fileName = document.get("fileName");
            String content = document.get("content");
            System.out.println(fileName);
            System.out.println(content);
        }

        indexReader.close();
        directory.close();

    }
}
