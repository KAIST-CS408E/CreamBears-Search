package services.xis.search.searcher

class HitsTextSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends TextSearcher(
  host, port0, port1, protocol
) with HitsMixin
