package org.dandelion.search.lucene.service;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.dandelion.search.lucene.model.SearchEntity;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2023/5/22
 */
@Service
public class LuceneService {

    public List<SearchEntity> search(String key ,int num) {
        List<SearchEntity> list = new ArrayList<>();

        try {
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
            Query query = parser.parse(key);

            QueryScorer scorer = new QueryScorer(query,"content");
            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color=\"red\">",
                    "</font>");
            Highlighter highlighter = new Highlighter(formatter, scorer);

            // 获取搜索结果
            ScoreDoc[] scoreDoc = indexSearcher.search(query, num).scoreDocs;
            for (ScoreDoc doc : scoreDoc) {
                Document document = indexSearcher.doc(doc.doc);

                TokenStream anyTokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(), doc.doc, "content", ikAnalyzer);
                SimpleSpanFragmenter simpleSpanFragmenter = new SimpleSpanFragmenter(scorer);
                highlighter.setTextFragmenter(simpleSpanFragmenter);
                // 返回高亮 截取部分字符
                String content = highlighter.getBestFragment(anyTokenStream, document.get("content"));

                SearchEntity search = new SearchEntity();
                String fileName = document.get("fileName");
                search.setFileName(fileName);
                search.setContent(content);
                list.add(search);
            }

            indexReader.close();
            directory.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}
