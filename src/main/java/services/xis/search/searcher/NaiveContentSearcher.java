package services.xis.search.searcher;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class NaiveContentSearcher extends GenBuilderSearcher {
    public NaiveContentSearcher(
        String host, Integer port0, Integer port1, String protocol
    ) {
        super(host, port0, port1, protocol);
    }

    public SearchSourceBuilder builder(String key) {
        return (new SearchSourceBuilder())
            .query(QueryBuilders.matchQuery("content", key));
    }
}
