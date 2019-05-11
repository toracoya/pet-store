package com.toracoya.petstore.pets.json

import com.toracoya.petstore.pet.Pets

case class PetsJson(pets: List[PetJson], hasNext: Boolean)

object PetsJson {

  def from(pets: Pets, hasNext: Boolean): PetsJson =
    PetsJson(pets.toList.map(PetJson.from), hasNext)
}
