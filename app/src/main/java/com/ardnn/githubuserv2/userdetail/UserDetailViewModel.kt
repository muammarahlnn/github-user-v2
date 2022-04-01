package com.ardnn.githubuserv2.userdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv2.core.api.callbacks.UserCallback
import com.ardnn.githubuserv2.core.api.repositories.UserRepository
import com.ardnn.githubuserv2.core.data.source.remote.response.UserDetailResponse

class UserDetailViewModel : ViewModel() {
    companion object {
        private val TAG = UserDetailViewModel::class.java.simpleName
    }

    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    fun fetchUserDetail(username: String) {
        // show progressbar
        _isLoading.value = true
        UserRepository.getUserDetail(username, object : UserCallback {
            override fun onSuccess(userDetail: UserDetailResponse) {
                _isLoading.value = false // hide progressbar
                _isFailure.value = false // success fetch data
                _user.value = userDetail
            }

            override fun onFailure(message: String) {
                _isFailure.value = true // unable to fetch data
                Log.d(TAG, message)
            }

        })
    }
}