package com.ardnn.githubuserv2.api.callbacks

import com.ardnn.githubuserv2.api.responses.UserResponse

interface UserListCallback {
    fun onSuccess(userList: MutableList<UserResponse>)
    fun onFailure(message: String)
}