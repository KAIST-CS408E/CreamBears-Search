package services.xis.search.searcher

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, BoolQueryBuilder
}
import org.elasticsearch.index.query.functionscore.{
  ScoreFunctionBuilders, FunctionScoreQueryBuilder
}
import org.elasticsearch.common.lucene.search.function.{
  FunctionScoreQuery, CombineFunction
}

class HitsTextSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends TextSearcher(
  host, port0, port1, protocol
) {
  override def builder(key: String): QueryBuilder =
    new FunctionScoreQueryBuilder(
      super.builder(key),
      ScoreFunctionBuilders.scriptFunction(
        "Math.log(1 + doc['hits'].value)"
      )
    )
    .boostMode(CombineFunction.SUM)
    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
}
