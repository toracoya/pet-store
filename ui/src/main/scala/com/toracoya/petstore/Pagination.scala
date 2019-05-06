package com.toracoya.petstore

import org.http4s.QueryParamDecoder
import org.http4s.dsl.impl.OptionalQueryParamDecoderMatcher

object Pagination {

  import QueryParamDecoder._

  object PageMatcher extends OptionalQueryParamDecoderMatcher[Int]("page")
  object PageSizeMatcher extends OptionalQueryParamDecoderMatcher[Int]("pageSize")

  val DefaultPage: Int = 0
  val DefaultPageSize: Int = 20
}
