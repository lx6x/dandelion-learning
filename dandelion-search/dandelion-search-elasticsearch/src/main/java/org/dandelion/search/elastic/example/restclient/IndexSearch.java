package org.dandelion.search.elastic.example.restclient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
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
public class IndexSearch {

    public static void main(String[] args) throws IOException {

        // 创建低级客户端
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();
        // 使用Jackson映射器创建传输
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        // 创建API客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
//        GetIndexResponse createIndexResponse = client.indices().get(e -> e.index("teacher"));
        GetIndexResponse createIndexResponse = client.indices().get(e -> e.index("teacher-set"));
        System.out.println(createIndexResponse.result());
        System.out.println(createIndexResponse.result().keySet());
        transport.close();
        restClient.close();
    }
}
