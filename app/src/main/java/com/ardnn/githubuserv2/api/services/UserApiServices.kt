package com.ardnn.githubuserv2.api.services

import com.ardnn.githubuserv2.api.responses.UserDetailResponse
import com.ardnn.githubuserv2.api.responses.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiServices {
    @GET("{username}")
    fun getUserDetail(
        @Path("username")
        username: String
    ): Call<UserDetailResponse>

    @GET("{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String
    ): Call<MutableList<UserResponse>>

    @GET("{username}/following")
    fun getUserFollowings(
        @Path("username")
        username: String
    ): Call<MutableList<UserResponse>>
}