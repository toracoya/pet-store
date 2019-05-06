package com.toracoya.petstore.pets.json

import com.toracoya.petstore.pet.Pets

// TODO: Support `hasNext`
case class PetsJson(pets: List[PetJson])

object PetsJson {

  def from(pets: Pets): PetsJson = PetsJson(pets.toList.map(PetJson.from))
}
