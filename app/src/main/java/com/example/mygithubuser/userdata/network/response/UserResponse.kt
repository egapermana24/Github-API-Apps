package com.example.mygithubuser.userdata.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<GithubUser>? = null
)

data class GithubUser(

    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

)
