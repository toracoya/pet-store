package com.toracoya.petstore.repository.doobie

import doobie._
import doobie.implicits._

trait SQLPagination {

  def paginate[A: Read](limit: Int, offset: Int)(query: Query0[A]): Query0[A] =
    (query.toFragment ++ fr"LIMIT $limit OFFSET $offset").query
}

object SQLPagination extends SQLPagination
