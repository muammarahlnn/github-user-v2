package com.ardnn.githubuserv2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv2.api.callbacks.UserListCallback
import com.ardnn.githubuserv2.api.repositories.UserRepository
import com.ardnn.githubuserv2.api.responses.UserResponse

class UserFollViewModel(
    section: Int,
    username: String
) : ViewModel() {

    companion object {
        private val TAG = UserFollViewModel::class.java.simpleName
    }

    private val _followerList = MutableLiveData<MutableList<UserResponse>>()
    val followerList: LiveData<MutableList<UserResponse>> = _followerList

    private val _followingList = MutableLiveData<MutableList<UserResponse>>()
    val followingList: LiveData<MutableList<UserResponse>> = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isListEmpty = MutableLiveData(false)
    val isListEmpty: LiveData<Boolean> = _isListEmpty

    init {
        when (section) {
            0 -> { // followers
                setUserFollowers(username)
            }
            1 -> { // following
                setUserFollowings(username)
            }
        }
    }

    private fun setUserFollowers(username: String) {
        // show progressbar
        _isLoading.value = true

        UserRepository.getUserFollowers(username, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                // hide progressbar
                _isLoading.value = false

                _followerList.value = userList
                if (userList.isNullOrEmpty()) {
                    _isListEmpty.value = true
                }
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }

        })
    }

    private fun setUserFollowings(username: String) {
        // show progressbar
        _isLoading.value = true

        UserRepository.getUserFollowings(username, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                // hide progressbar
                _isLoading.value = false

                _followingList.value = userList
                if (userList.isNullOrEmpty()) {
                    _isListEmpty.value = true
                }
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }

        })
    }

}