package com.ardnn.githubuserv2.api.callbacks

import com.ardnn.githubuserv2.api.responses.UserResponse

interface UserFollowersCallback {
    fun onSuccess(followerList: MutableList<UserResponse>)
    fun onFailure(message: String)
}