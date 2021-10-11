package com.ardnn.githubuserv2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv2.api.callbacks.UserListCallback
import com.ardnn.githubuserv2.api.repositories.SearchedUsersRepository
import com.ardnn.githubuserv2.api.repositories.UserRepository
import com.ardnn.githubuserv2.api.responses.UserResponse

class HomeViewModel : ViewModel() {
    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    private val _followerList = MutableLiveData<MutableList<UserResponse>>()
    val followerList: LiveData<MutableList<UserResponse>> = _followerList

    private val _followingList = MutableLiveData<MutableList<UserResponse>>()
    val followingList: LiveData<MutableList<UserResponse>> = _followingList

    private val _searchedUsers = MutableLiveData<MutableList<UserResponse>>()
    val searchedUsers: LiveData<MutableList<UserResponse>> = _searchedUsers

    fun getSearchedUsers(searchedWords: String) {
        SearchedUsersRepository.getSearchedUsers(searchedWords, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                _searchedUsers.value = userList
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }

        })
    }

    fun getUserFollowers(username: String) {
        UserRepository.getUserFollowers(username, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                _followerList.value = userList
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }

        })
    }

    fun getUserFollowings(username: String) {
        UserRepository.getUserFollowings(username, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                _followingList.value = userList
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }

        })
    }
}