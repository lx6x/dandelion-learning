package org.dandelion.search.es2712.example.restclient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * @author lx6x
 * @date 2024/1/5
 */
public class IndexDelete {

    public static void main(String[] args) throws Exception{
        // 创建低级客户端
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();
        // 使用Jackson映射器创建传输
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        // 创建API客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
        DeleteIndexResponse delete = client.indices().delete(e -> e.index("teacher"));
        System.out.println(delete.acknowledged());
        transport.close();
        restClient.close();
    }
}
