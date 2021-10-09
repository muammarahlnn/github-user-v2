package com.ardnn.githubuserv2.api.responses

data class UserFollowingsResponse(
    val followingList: MutableList<UserResponse>
)