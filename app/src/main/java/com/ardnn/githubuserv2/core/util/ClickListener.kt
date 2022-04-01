package com.ardnn.githubuserv2.core.util

import com.ardnn.githubuserv2.core.data.source.remote.response.UserResponse

interface ClickListener {
    fun onItemClicked(user: UserResponse)
}