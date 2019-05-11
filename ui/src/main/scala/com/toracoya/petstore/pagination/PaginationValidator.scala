package com.toracoya.petstore.pagination

import cats.data.ValidatedNec
import cats.implicits._
import com.toracoya.petstore.APIValidation
import com.toracoya.petstore.APIValidation.{InvalidPage, InvalidPageSize}

trait PaginationValidator {

  type ValidationResult[A] = ValidatedNec[APIValidation, A]

  def validate(page: Int, pageSize: Int): ValidationResult[Pagination] =
    (validatePage(page), validatePageSize(pageSize)).mapN(Pagination.apply)

  private def validatePage(page: Int): ValidationResult[Int] =
    if (page >= 0) page.validNec else InvalidPage.invalidNec

  private def validatePageSize(pageSize: Int): ValidationResult[Int] =
    if (pageSize >= 0) pageSize.validNec else InvalidPageSize.invalidNec
}

object PaginationValidator extends PaginationValidator
