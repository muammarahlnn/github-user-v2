package com.ardnn.githubuserv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("public_repos")
    val repositories: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)