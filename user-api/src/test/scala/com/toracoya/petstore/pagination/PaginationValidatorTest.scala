package com.toracoya.petstore.pagination

import cats.data.{NonEmptyChain, NonEmptyList}
import cats.implicits._
import com.toracoya.petstore.APIValidation.{InvalidPage, InvalidPageSize}
import org.scalatest.WordSpec

class PaginationValidatorTest extends WordSpec {

  "#validate" when {
    "page is negative" should {
      "return invalid" in {
        val validated = PaginationValidator.validate(-1, 20)
        assert(validated == InvalidPage.invalidNec)
      }
    }

    "pageSize is negative" should {
      "return invalid" in {
        val validated = PaginationValidator.validate(0, -1)
        assert(validated == InvalidPageSize.invalidNec)
      }
    }

    "both page and pageSize are negative" should {
      "return invalid" in {
        val validated = PaginationValidator.validate(-1, -1)
        assert(validated == NonEmptyChain.fromNonEmptyList(NonEmptyList.of(InvalidPage, InvalidPageSize)).invalid)
      }
    }

  }
}
