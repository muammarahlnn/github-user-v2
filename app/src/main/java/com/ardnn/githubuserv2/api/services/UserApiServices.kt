package com.ardnn.githubuserv2.api.services

import com.ardnn.githubuserv2.api.responses.UserFollowersResponse
import com.ardnn.githubuserv2.api.responses.UserFollowingsResponse
import com.ardnn.githubuserv2.api.responses.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiServices {
    @GET("{username}")
    fun getUserDetail(
        @Path("username")
        username: String
    ): Call<UserResponse>

    @GET("{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String
    ): Call<UserFollowersResponse>

    @GET("{username}/followings")
    fun getUserFollowings(
        @Path("username")
        username: String
    ): Call<UserFollowingsResponse>
}