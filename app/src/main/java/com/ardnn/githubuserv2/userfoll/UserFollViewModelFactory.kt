package com.ardnn.githubuserv2.userfoll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserFollViewModelFactory(
    private val section: Int,
    private val username: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserFollViewModel(section, username) as T
    }
}