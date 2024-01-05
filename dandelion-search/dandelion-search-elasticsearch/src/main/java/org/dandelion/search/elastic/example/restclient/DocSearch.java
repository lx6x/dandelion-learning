package org.dandelion.search.elastic.example.restclient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author lx6x
 * @date 2024/1/5
 */
public class DocSearch {

    public static void main(String[] args) throws IOException {
//        id();
        confine();
    }

    public static void id() throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        GetResponse<Teacher> response = client.get(r -> r.index("teacher").id("001"), Teacher.class);
        System.out.println(response.source());
        transport.close();
        restClient.close();
    }


    public static void confine() throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        SearchResponse<Teacher> teacher = client.search(s -> s.index("teacher").query(q -> q.match(m -> m.field("name").query("张三"))), Teacher.class);
//        SearchResponse<Teacher> teacher = client.search(s -> s.index("teacher").query(q -> q.matchAll(m -> m)), Teacher.class);
        System.out.println(teacher.took());
        System.out.println(teacher.hits());

        transport.close();
        restClient.close();
    }
}
