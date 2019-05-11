package com.toracoya.petstore.pets.json

import com.toracoya.petstore.pet.Pet

case class PetJson(id: Long, name: String)

object PetJson {

  def from(pet: Pet): PetJson = PetJson(pet.id.toLong, pet.name.toString)
}
