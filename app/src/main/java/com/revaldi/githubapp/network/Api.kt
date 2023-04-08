package com.revaldi.githubapp.network

import com.revaldi.githubapp.model.DetailUserResponse
import com.revaldi.githubapp.model.User
import com.revaldi.githubapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") query: String
        ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail (
        @Path("username") username: String
        ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers (
        @Path("username") username: String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing (
        @Path("username") username: String
    ):Call<ArrayList<User>>
}