package com.ardnn.githubuserv2.api.callbacks

import com.ardnn.githubuserv2.api.responses.UserDetailResponse

interface UserCallback {
    fun onSuccess(userDetail: UserDetailResponse)
    fun onFailure(message: String)
}