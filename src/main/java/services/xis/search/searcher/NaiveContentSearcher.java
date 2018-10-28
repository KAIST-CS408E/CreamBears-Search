package services.xis.search.searcher;

import java.util.Map;
import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

public class NaiveContentSearcher extends Searcher {
    private String index, typ;

    public NaiveContentSearcher(
        String host, Integer port0, Integer port1,
        String protocol, String index, String typ
    ) {
        super(host, port0, port1, protocol);
        this.index = index;
        this.typ = typ;
    }

    public void search(String key) {
        SearchRequest request = new SearchRequest(index).types(typ);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("content", key));
        request.source(builder);
        try {
            SearchResponse response =
                client().search(request, RequestOptions.DEFAULT);

            for (SearchHit h : response.getHits().getHits()) {
                Map<String, Object> s = h.getSourceAsMap();
                System.out.println(h.getId());
                System.out.println(s.get("board"));
                System.out.println(s.get("title"));
                System.out.println(s.get("content"));
                System.out.println();
            }
        } catch (IOException e) {
        }
    }
}
