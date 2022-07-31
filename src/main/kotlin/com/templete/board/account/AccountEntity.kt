package com.templete.board.account

import com.templete.board.factor.comment.Comment
import com.templete.board.factor.post.Post
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Document(collection = "user")
class User(@Id var _id : String?,
           var nickname : String,
           val email : String,
           var m_password : String,
           var enabled : Boolean,
           var roles : MutableList<String>,
           ) : UserDetails {

    @DocumentReference(lazy = true)
    private var refreshToken: String? = null
        get() = field
        set(value) {field = value}

    @DocumentReference(lazy = true)
    private var posts : MutableList<Post>? = null
        get() = field
        set(value) {field = value}

    @DocumentReference(lazy = true)
    private var comments : MutableList<Comment>? = null
        get() = field
        set(value) {field = value}


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
class RefreshToken(
    @Id var _id : String?,
    var userid : String,
    var refreshToken: String
)
{

}