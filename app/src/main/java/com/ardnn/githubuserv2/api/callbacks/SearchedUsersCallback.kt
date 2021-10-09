package com.ardnn.githubuserv2.api.callbacks

import com.ardnn.githubuserv2.api.responses.UserResponse

interface SearchedUsersCallback {
    fun onSuccess(searchedUsers: MutableList<UserResponse>)
    fun onFailure(message: String)
}