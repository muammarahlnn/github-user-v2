package com.ardnn.githubuserv2.api.callbacks

import com.ardnn.githubuserv2.api.responses.UserResponse

interface UserCallback {
    fun onSuccess(user: UserResponse)
    fun onFailure(message: String)
}