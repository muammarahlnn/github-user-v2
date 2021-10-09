package com.ardnn.githubuserv2.api.services

import com.ardnn.githubuserv2.api.responses.SearchedUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchUserApiService {
    @GET("{username}")
    fun getSearchedUsers(
        @Path("username")
        username: String
    ): Call<SearchedUserResponse>
}