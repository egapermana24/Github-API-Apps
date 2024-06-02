package com.example.mygithubuser.userdata.network.retrofit

import com.example.mygithubuser.userdata.network.response.DetailsUserGithubResponse
import com.example.mygithubuser.userdata.network.response.UserResponse
import com.example.mygithubuser.userdata.network.response.GithubUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getUsers(
        @Header("Authorization") token: String
    ): Call<GithubUser>

    @GET("search/users")
    fun getGithubUser(
        @Query("q") id: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailsUserGithubResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<GithubUser>>
}