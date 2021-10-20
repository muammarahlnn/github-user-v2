package com.ardnn.githubuserv2.api.responses

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("login")
    val username: String = "",

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("location")
    val location: String? = "",

    @field:SerializedName("company")
    val company: String? = "",

    @field:SerializedName("public_repos")
    val repositories: Int? = 0,

    @field:SerializedName("followers")
    val followers: Int? = 0,

    @field:SerializedName("following")
    val following: Int? = 0,

    @field:SerializedName("avatar_url")
    val avatarUrl: String = "",
)