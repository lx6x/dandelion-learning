package org.dandelion.search.elastic.example.restclient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
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
public class DocCreate {

    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        Teacher teacher = Teacher.builder().id("001").name("张三").age(10).build();

        CreateResponse createResponse = client.create(c -> c.index("teacher").id("001").document(teacher));
        System.out.println(createResponse.result());
        transport.close();
        restClient.close();
    }
}
