package com.toracoya.petstore

sealed trait APIValidation extends Product with Serializable {

  val message: String
}

object APIValidation {

  case object InvalidPage extends APIValidation {
    override val message: String = "Page must be greater than or equal to 0"
  }

  case object InvalidPageSize extends APIValidation {
    override val message: String = "Page size must be greater than or equal to 0"
  }
}
