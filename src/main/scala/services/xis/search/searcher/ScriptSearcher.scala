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

class DateTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends TitleContentSearcher(host, port0, port1, protocol) with DateMixin

class DateAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsSearcher(host, port0, port1, protocol) with DateMixin

class DateSynonymTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymTitleContentSearcher(host, port0, port1, protocol) with DateMixin

class DateSynonymAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymAllFieldsSearcher(host, port0, port1, protocol) with DateMixin

class DateTitleContentConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends TitleContentConstSearcher(host, port0, port1, protocol) with DateMixin

class DateAllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsConstSearcher(host, port0, port1, protocol) with DateMixin

class DateSynonymTitleContentConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymTitleContentConstSearcher(host, port0, port1, protocol) with DateMixin

class DateSynonymAllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymAllFieldsConstSearcher(host, port0, port1, protocol) with DateMixin

class DateHitsTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends TitleContentSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsSynonymTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymTitleContentSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsSynonymAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymAllFieldsSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsTitleContentConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends TitleContentConstSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsAllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends AllFieldsConstSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsSynonymTitleContentConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymTitleContentConstSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin

class DateHitsSynonymAllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymAllFieldsConstSearcher(host, port0, port1, protocol) with HitsMixin with DateMixin
