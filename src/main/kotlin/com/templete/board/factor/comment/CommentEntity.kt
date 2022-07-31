package com.templete.board.factor.comment

import com.templete.board.account.User
import com.templete.board.factor.board.Board
import com.templete.board.factor.post.Post
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.util.Date

@Document(collection = "comment")
class Comment(@Id var _id : String?,
              @DocumentReference(lazy = true) var user : User,
              @DocumentReference(lazy = true) var board : Board,
              @DocumentReference(lazy = true) var post : Post,
              var time : Date,
              var content : String) {
}