package com.ardnn.githubuserv2.api.responses

import com.google.gson.annotations.SerializedName

data class SearchedUserResponse(
    @field:SerializedName("items")
    val searchedUserDetail: MutableList<UserResponse>
)
