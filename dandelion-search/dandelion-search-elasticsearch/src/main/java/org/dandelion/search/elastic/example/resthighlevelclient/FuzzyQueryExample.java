package org.dandelion.search.elastic.example.resthighlevelclient;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQueryParser;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 模糊查询
 *
 * @author lx6x
 * @date 2023/8/3
 */
public class FuzzyQueryExample {

    public static void main(String[] args) throws Exception {

        multiMatch();

    }

    /**
     * 用于构建全文搜索（Full Text Search）的查询条件。它可以对指定字段进行全文搜索，
     * 并将查询的关键词分词后进行匹配。MatchQueryBuilder会根据查询字符串的分词结果去匹配文档，
     * 返回与查询字符串匹配的文档。
     *
     * @throws Exception
     */
    private static void match() throws Exception {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest searchRequest = new SearchRequest("library_book_doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("bookName", "务")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(2); // 设置模糊搜索的前缀长度

        boolQuery.must(matchQuery);
        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();

        List<Map<String, Object>> books = new LinkedList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", hit.getId());
            map.put("author", hit.getSourceAsMap().get("author"));
            map.put("bookDesc", hit.getSourceAsMap().get("bookDesc"));
            map.put("bookName", hit.getSourceAsMap().get("bookName"));
            books.add(map);
        }

        System.out.println(books);

        restHighLevelClient.close();
    }


    /**
     * 用于在多个字段上执行全文搜索（Full Text Search）的查询。它将查询的关键词分词后应用到指定的多个字段上，并返回包含关键词匹配的文档。
     *
     * @throws Exception
     */
    private static void multiMatch() throws Exception {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest searchRequest = new SearchRequest("library_book_doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        MultiMatchQueryBuilder matchQuery = QueryBuilders.multiMatchQuery("9", "bookName", "author","isbn").type(MatchQueryParser.Type.PHRASE);
        matchQuery.boost(0);

        boolQuery.must(matchQuery);
        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();

        List<Map<String, Object>> books = new LinkedList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", hit.getId());
            map.put("author", hit.getSourceAsMap().get("author"));
            map.put("bookDesc", hit.getSourceAsMap().get("bookDesc"));
            map.put("bookName", hit.getSourceAsMap().get("bookName"));
            books.add(map);
        }

        System.out.println(books);

        restHighLevelClient.close();
    }


}
