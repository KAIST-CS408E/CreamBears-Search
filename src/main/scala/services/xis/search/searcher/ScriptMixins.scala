package services.xis.search.searcher

import java.util.Date

import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.functionscore.{
  ScoreFunctionBuilders, FunctionScoreQueryBuilder
}
import org.elasticsearch.common.lucene.search.function.{
  FunctionScoreQuery, CombineFunction
}

trait HitsMixin extends GenBuilderSearcher {
  abstract override def builder(key: String): QueryBuilder =
    new FunctionScoreQueryBuilder(super.builder(key),
      ScoreFunctionBuilders.scriptFunction(
        "Math.log(1 + doc['hits'].value)"
      ))
      .boostMode(CombineFunction.SUM)
      .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
}

trait DateMixin extends TextSearcher {
  abstract override def builder(key: String): QueryBuilder =
    new FunctionScoreQueryBuilder(super.builder(key),
      ScoreFunctionBuilders.gaussDecayFunction(
        "time", new Date, "100d",
      ))
      .boostMode(CombineFunction.MULTIPLY)
      .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
}
