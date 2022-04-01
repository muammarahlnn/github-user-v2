package com.ardnn.githubuserv2.userfoll

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv2.core.api.callbacks.UserListCallback
import com.ardnn.githubuserv2.core.api.repositories.UserRepository
import com.ardnn.githubuserv2.core.data.source.remote.response.UserResponse

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

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

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
                _isLoading.value = false // hide progressbar
                _isFailure.value = false // success fetch data

                _followerList.value = userList
                if (userList.isNullOrEmpty()) {
                    _isListEmpty.value = true
                }
            }

            override fun onFailure(message: String) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }

        })
    }

    private fun setUserFollowings(username: String) {
        _isLoading.value = true // show progressbar
        UserRepository.getUserFollowings(username, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = false // success fetch data

                _followingList.value = userList
                if (userList.isNullOrEmpty()) {
                    _isListEmpty.value = true
                }
            }

            override fun onFailure(message: String) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }

        })
    }

}