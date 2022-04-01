package com.ardnn.githubuserv2.core.data.source.remote.network

import com.ardnn.githubuserv2.core.data.source.remote.response.SearchedUserResponse
import com.ardnn.githubuserv2.core.data.source.remote.response.UserDetailResponse
import com.ardnn.githubuserv2.core.data.source.remote.response.UserResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getSearchedUsers(
        @Query("q")
        username: String
    ): Flowable<SearchedUserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username")
        username: String
    ): Flowable<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String
    ): Flowable<List<UserResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username")
        username: String
    ): Flowable<List<UserResponse>>
}