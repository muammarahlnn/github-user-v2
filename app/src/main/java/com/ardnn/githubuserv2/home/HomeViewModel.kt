package com.ardnn.githubuserv2.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv2.core.api.callbacks.UserListCallback
import com.ardnn.githubuserv2.core.api.repositories.SearchedUsersRepository
import com.ardnn.githubuserv2.core.data.source.remote.response.UserResponse

class HomeViewModel : ViewModel() {
    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    private val _searchedUsers = MutableLiveData<MutableList<UserResponse>>()
    val searchedUsers: LiveData<MutableList<UserResponse>> = _searchedUsers

    private val _isSearched = MutableLiveData(false)
    val isSearched: LiveData<Boolean> = _isSearched

    private val _isSearchedUsersEmpty = MutableLiveData<Boolean>()
    val isSearchedUsersEmpty: LiveData<Boolean> = _isSearchedUsersEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    fun setSearchedUsers(searchedWords: String) {
        _isSearched.value = true // first searching
        _isLoading.value = true // show loading
        _isSearchedUsersEmpty.value = false // make it false so the alert text gonna be invisible everytime we searching
        SearchedUsersRepository.getSearchedUsers(searchedWords, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                _isLoading.value = false // hide loading
                _isFailure.value = false // success fetch data
                _isSearchedUsersEmpty.value = userList.isNullOrEmpty() // check is list empty or not

                _searchedUsers.value = userList
            }

            override fun onFailure(message: String) {
                _isLoading.value = false // hide loading
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }

        })
    }

}