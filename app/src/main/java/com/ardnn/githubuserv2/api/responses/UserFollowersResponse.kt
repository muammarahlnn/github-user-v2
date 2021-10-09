package com.ardnn.githubuserv2.api.responses

data class UserFollowersResponse(
    val followerList: MutableList<UserResponse>
)