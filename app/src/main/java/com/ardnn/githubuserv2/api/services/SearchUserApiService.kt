package com.ardnn.githubuserv2.api.services

import com.ardnn.githubuserv2.api.responses.SearchedUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUserApiService {
    @GET("users")
    fun getSearchedUsers(
        @Query("q")
        username: String
    ): Call<SearchedUserResponse>
}