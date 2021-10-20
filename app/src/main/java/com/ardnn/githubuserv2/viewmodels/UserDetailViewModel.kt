package com.ardnn.githubuserv2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.githubuserv2.api.callbacks.UserCallback
import com.ardnn.githubuserv2.api.repositories.UserRepository
import com.ardnn.githubuserv2.api.responses.UserDetailResponse

class UserDetailViewModel : ViewModel() {
    companion object {
        private val TAG = UserDetailViewModel::class.java.simpleName
    }

    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchUserDetail(username: String) {
        // show progressbar
        _isLoading.value = true
        UserRepository.getUserDetail(username, object : UserCallback {
            override fun onSuccess(userDetail: UserDetailResponse) {
                // hide progressbar
                _isLoading.value = false

                _user.value = userDetail
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }

        })
    }
}