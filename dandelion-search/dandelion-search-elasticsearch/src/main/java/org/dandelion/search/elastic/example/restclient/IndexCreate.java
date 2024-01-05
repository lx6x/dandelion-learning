package org.dandelion.search.elastic.example.restclient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author lx6x
 * @date 2024/1/4
 */
public class IndexCreate {
    /*public static void main(String[] args) throws IOException {


        HttpHost host = new HttpHost("127.0.0.1", 9200, "http");
        RestClientBuilder builder = RestClient.builder(host);
        RestHighLevelClient client = new RestHighLevelClient(builder);
        //创建索引
        CreateIndexRequest req = new CreateIndexRequest("teacher");
        CreateIndexResponse res = client.indices().create(req, RequestOptions.DEFAULT);

        boolean acknowledged = res.isAcknowledged();
        System.out.println("索引操作: " + acknowledged);
        client.close();
    }*/

    public static void main(String[] args) throws Exception {
        set();
    }

    private static void index() throws IOException {
        // 创建低级客户端
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();
        // 使用Jackson映射器创建传输层
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );
        // 创建API客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
        // 创建索引
        CreateIndexResponse createIndexResponse = client.indices().create(c -> c.index("teacher"));
        System.out.println(createIndexResponse.acknowledged());

        // 关闭ES客户端
        transport.close();
        restClient.close();
    }

    /**
     * 初始属性
     */
    private static void set() throws IOException {
        // 创建低级客户端
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();
        // 使用Jackson映射器创建传输层
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );
        // 创建API客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
        // 创建索引
        CreateIndexResponse createIndexResponse = client.indices().create(c -> {
            c.settings(indexSetting ->
                    indexSetting.numberOfReplicas("1").numberOfShards("1")
            ).mappings(typeMapping ->
                    typeMapping
                            .properties("id", propertyBuilder -> propertyBuilder.integer(i -> i))
                            .properties("name", propertyBuilder -> propertyBuilder.text(t -> t.index(true)))
                            .properties("sex", propertyBuilder -> propertyBuilder.keyword(k -> k.index(false)))
            ).index("teacher-set");
            return c;
        });
        System.out.println(createIndexResponse.acknowledged());

        // 关闭ES客户端
        transport.close();
        restClient.close();
    }
}
