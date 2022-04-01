package com.ardnn.githubuserv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchedUserResponse(
    @field:SerializedName("items")
    val searchedUserDetail: List<UserResponse>? = null
)

data class UserResponse(
    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)
