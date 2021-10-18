package com.ardnn.githubuserv2.viewmodels

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

    private val _isSearched = MutableLiveData<Boolean>(false)
    val isSearched: LiveData<Boolean> = _isSearched

    private val _isSearchedUsersEmpty = MutableLiveData<Boolean>(false)
    val isSearchedUsersEmpty: LiveData<Boolean> = _isSearchedUsersEmpty

    fun setSearchedUsers(searchedWords: String) {
        SearchedUsersRepository.getSearchedUsers(searchedWords, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                if (_isSearched.value == false) {
                    _isSearched.value = true
                }
                _isSearchedUsersEmpty.value = userList.isNullOrEmpty()
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