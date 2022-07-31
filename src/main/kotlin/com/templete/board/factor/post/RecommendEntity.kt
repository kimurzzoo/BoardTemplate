package com.templete.board.factor.post

import com.templete.board.account.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "recommendation")
class Recommendation(@Id var _id : String?,
                     @DocumentReference(lazy = true) var post : Post,
                     @DocumentReference(lazy = true) var user : User
) {
}