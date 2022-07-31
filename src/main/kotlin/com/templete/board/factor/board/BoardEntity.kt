package com.templete.board.factor.board

import com.templete.board.factor.comment.Comment
import com.templete.board.factor.post.Post
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "board")
class Board(@Id var _id : String?,
            var name : String,
            @DocumentReference(lazy = true) var posts : MutableList<Post>?,
            @DocumentReference(lazy = true) var comments : MutableList<Comment>?) {
}