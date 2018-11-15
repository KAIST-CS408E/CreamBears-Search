package services.xis.search.searcher

import java.util.Date

import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.functionscore.{
  ScoreFunctionBuilders, FunctionScoreQueryBuilder
}
import org.elasticsearch.common.lucene.search.function.{
  FunctionScoreQuery, CombineFunction
}

trait DateMixin extends TextSearcher {
  abstract override def builder(key: String): QueryBuilder = {
    new FunctionScoreQueryBuilder(
      super.builder(key),
      ScoreFunctionBuilders.gaussDecayFunction(
        "time", new Date, "365d",
      )
    )
    .boostMode(CombineFunction.MULTIPLY)
    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
  }
}
