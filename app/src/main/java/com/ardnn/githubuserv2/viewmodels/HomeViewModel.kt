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

    private val _searchedUsers = MutableLiveData<MutableList<UserResponse>>()
    val searchedUsers: LiveData<MutableList<UserResponse>> = _searchedUsers

    private val _isSearched = MutableLiveData(false)
    val isSearched: LiveData<Boolean> = _isSearched

    private val _isSearchedUsersEmpty = MutableLiveData<Boolean>()
    val isSearchedUsersEmpty: LiveData<Boolean> = _isSearchedUsersEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchedUsers(searchedWords: String) {
        // first search
        _isSearched.value = true

        // show progressbar
        _isLoading.value = true

        // make it false so the alert text gonna be invisible everytime we searching
        _isSearchedUsersEmpty.value = false
        SearchedUsersRepository.getSearchedUsers(searchedWords, object : UserListCallback {
            override fun onSuccess(userList: MutableList<UserResponse>) {
                // hide progressbar
                _isLoading.value = false

                _isSearchedUsersEmpty.value = userList.isNullOrEmpty()
                _searchedUsers.value = userList
            }

            override fun onFailure(message: String) {
                _isLoading.value = false
                Log.d(TAG, message)
            }

        })
    }

}