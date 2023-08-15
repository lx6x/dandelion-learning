package org.dandelion.search.elastic.example.query;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 *
 * @author lx6x
 * @date 2023/8/3
 */
public class PaginationExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        int pageSize = 10; // 每页显示的文档数量
        int currentPage = 2; // 当前页码，假设要获取第2页的结果

        SearchRequest searchRequest = new SearchRequest("library_book_detail_doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("id", "183");

        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.size(pageSize); // 指定每页显示的文档数量
        searchSourceBuilder.from((currentPage - 1) * pageSize); // 计算from参数，分页从0开始计算
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();

        List<String> documentIds = new ArrayList<>();
        for (SearchHit hit : hits) {
            String documentId = hit.getId();
            documentIds.add(documentId);
            System.err.println(documentId);
        }
        // 在这里可以根据获取的documentIds做进一步处理


        client.close();
    }
}
