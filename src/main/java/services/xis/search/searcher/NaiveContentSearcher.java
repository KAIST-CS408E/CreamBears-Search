package services.xis.search.searcher;

import java.util.Map;
import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.action.search.SearchResponse;

public class NaiveContentSearcher extends Searcher {
    public NaiveContentSearcher(
        String host, Integer port0, Integer port1, String protocol
    ) {
        super(host, port0, port1, protocol);
    }

    public SearchResponse search(String index, String typ, String key)
        throws IOException {
        SearchRequest request = new SearchRequest(index).types(typ);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("content", key));
        request.source(builder);
        return client().search(request, RequestOptions.DEFAULT);
    }
}
