package com.ardnn.githubuserv2.listeners

import com.ardnn.githubuserv2.api.responses.UserResponse

interface ClickListener {
    fun onItemClicked(user: UserResponse)
}