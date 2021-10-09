package com.ardnn.githubuserv2.api.callbacks

import com.ardnn.githubuserv2.api.responses.UserResponse

interface UserFollowingsCallback {
    fun onSuccess(followingList: MutableList<UserResponse>)
    fun onFailure(message: String)
}