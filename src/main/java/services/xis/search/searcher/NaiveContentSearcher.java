package services.xis.search.searcher;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class NaiveContentSearcher extends GenBuilderSearcher {
    public NaiveContentSearcher(
        String host, Integer port0, Integer port1, String protocol
    ) {
        super(host, port0, port1, protocol);
    }

    public QueryBuilder builder(String key) {
        return QueryBuilders.matchQuery("content", key);
    }
}
