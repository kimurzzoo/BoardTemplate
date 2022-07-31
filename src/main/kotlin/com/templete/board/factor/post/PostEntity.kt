package com.templete.board.factor.post

import com.templete.board.account.User
import com.templete.board.factor.board.Board
import com.templete.board.factor.comment.Comment
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.util.Date

@Document(collection = "post")
class Post(@Id var _id : String?,
           @DocumentReference(lazy = true) var board : Board,
           @DocumentReference(lazy = true) var user : User,
           var time : Date,
           var title : String,
           var content : MutableList<String>,
           @DocumentReference(lazy = true) var recommendations : MutableList<Recommendation>?,
           @DocumentReference(lazy = true) var comments : MutableList<Comment>?) {

    fun numOfRecommendations() : UInt
    {
        if(recommendations == null)
        {
            return 0.toUInt()
        }
        else
        {
            return recommendations!!.size.toUInt()
        }
    }

    fun numOfComments() : UInt
    {
        if(comments == null)
        {
            return 0.toUInt()
        }
        else
        {
            return comments!!.size.toUInt()
        }
    }
}