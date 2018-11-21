package services.xis.search.searcher

import java.lang.Integer

class HitsTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends TitleContentSearcher(host, port0, port1, protocol) with HitsMixin

class HitsAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsSearcher(host, port0, port1, protocol) with HitsMixin

class HitsSynonymTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymTitleContentSearcher(host, port0, port1, protocol) with HitsMixin

class HitsSynonymAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymAllFieldsSearcher(host, port0, port1, protocol) with HitsMixin

class HitsTitleContentConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends TitleContentConstSearcher(host, port0, port1, protocol) with HitsMixin

class HitsAllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsConstSearcher(host, port0, port1, protocol) with HitsMixin

class HitsSynonymTitleContentConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymTitleContentConstSearcher(host, port0, port1, protocol) with HitsMixin

class HitsSynonymAllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymAllFieldsConstSearcher(host, port0, port1, protocol) with HitsMixin
