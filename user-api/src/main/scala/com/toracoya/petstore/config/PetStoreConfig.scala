package com.toracoya.petstore.config

import com.toracoya.petstore.repository.doobie.DatabaseConfig
import io.circe.Decoder
import io.circe.generic.semiauto._

case class PetStoreConfig(db: DatabaseConfig)

object PetStoreConfig {

  implicit val petStoreDecoder: Decoder[PetStoreConfig] = deriveDecoder
}
