package com.templete.board.account

import com.templete.board.factor.comment.Comment
import com.templete.board.factor.post.Post
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Document(collection = "user")
class User(nickname : String, email : String, m_password : String, enabled : Boolean, roles : MutableList<String>) : UserDetails {

    @Id
    var _id : String? = null

    @Field(name = "nickname")
    var nickname : String = nickname

    @Field(name = "email")
    val email : String = email

    @Field(name = "m_password")
    var m_password : String = m_password

    @Field(name = "enabled")
    var enabled : Boolean = enabled

    @Field(name = "roles")
    var roles : MutableList<String> = roles

    @DocumentReference(lazy = true)
    var refreshToken: RefreshToken? = null

    @DocumentReference(lazy = true)
    var posts : MutableList<Post> = mutableListOf()

    @DocumentReference(lazy = true)
    var comments : MutableList<Comment> = mutableListOf()


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        for(role in this.roles)
        {
            authorities.add(SimpleGrantedAuthority(role))
        }
        return authorities
    }

    override fun getUsername(): String {
        return email
    }

    override fun getPassword(): String {
        return m_password
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }
}

@Document(collection = "refreshtoken")
open class RefreshToken(
    @Id var _id : String?,
    @DocumentReference(lazy = true) var user : User?,
    var refreshToken: String
)
{

}