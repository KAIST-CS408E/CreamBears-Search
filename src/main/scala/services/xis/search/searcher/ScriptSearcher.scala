package services.xis.search.searcher

import java.lang.Integer

class HitsAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsSearcher(host, port0, port1, protocol) with HitsMixin

class _HitsAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsSearcher(host, port0, port1, protocol) with HitsMixin
