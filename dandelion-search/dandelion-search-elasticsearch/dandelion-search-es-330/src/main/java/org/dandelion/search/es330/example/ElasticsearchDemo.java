package org.dandelion.search.es330.example;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 本地es版本 8.14.1
 */
public class ElasticsearchDemo {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        String serverUrl = "https://localhost:9200";
        String apiKey = "NHZUWkw1QUJZc3NuNEZnVzhCZnY6cUZzMk90UElSR1N4MDkzcFFsc01uZw==";

        // Configure the REST client
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();

        // Create the transport with a Jackson mapper
        RestClientTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        // Create a new book document
        Book book = new Book("1", "Elasticsearch Basics", "John Doe");

        // Index the document
        IndexRequest<Book> indexRequest = IndexRequest.of(i -> i
                .index("books")
                .id(book.getId())
                .document(book)
        );

        IndexResponse indexResponse = client.index(indexRequest);
        System.out.println("Indexed with version: " + indexResponse.version());

        // Retrieve the document
        GetResponse<Book> getResponse = client.get(g -> g
                .index("books")
                .id("1"), Book.class
        );

        if (getResponse.found()) {
            Book retrievedBook = getResponse.source();
            System.out.println("Retrieved book: " + retrievedBook);
        } else {
            System.out.println("Book not found");
        }

        // Search for the document
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("books")
                .query(q -> q
                        .match(t -> t
                                .field("title")
                                .query("Elasticsearch")
                        )
                )
        );

        SearchResponse<Book> searchResponse = client.search(searchRequest, Book.class);
        List<Hit<Book>> hits = searchResponse.hits().hits();

        for (Hit<Book> hit : hits) {
            System.out.println("Found book: " + hit.source());
        }

        // Close the client
        restClient.close();
    }
}

