package com.ardnn.githubuserv2.api.repositories

import com.ardnn.githubuserv2.api.callbacks.UserCallback
import com.ardnn.githubuserv2.api.callbacks.UserListCallback
import com.ardnn.githubuserv2.api.responses.UserDetailResponse
import com.ardnn.githubuserv2.api.responses.UserResponse
import com.ardnn.githubuserv2.api.services.UserApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepository {
    private val USER_SERVICE: UserApiServices =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_USER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiServices::class.java)


    // method to get user detail
    fun getUserDetail(username: String, callback: UserCallback) {
        USER_SERVICE.getUserDetail(username).enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(call: Call<UserDetailResponse>, detailResponse: Response<UserDetailResponse>) {
                if (detailResponse.isSuccessful) {
                    if (detailResponse.body() != null) {
                        callback.onSuccess(detailResponse.body() ?: UserDetailResponse())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(detailResponse.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserDetail failure")
            }
        })
    }

    // method to get user followers
    fun getUserFollowers(username: String, callback: UserListCallback) {
        USER_SERVICE.getUserFollowers(username).enqueue(object : Callback<MutableList<UserResponse>> {
            override fun onResponse(
                call: Call<MutableList<UserResponse>>,
                response: Response<MutableList<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body() ?: mutableListOf())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MutableList<UserResponse>>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserFollowers failure")
            }
        })
    }

    fun getUserFollowings(username: String, callback: UserListCallback) {
        USER_SERVICE.getUserFollowings(username).enqueue(object : Callback<MutableList<UserResponse>> {
            override fun onResponse(
                call: Call<MutableList<UserResponse>>,
                response: Response<MutableList<UserResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body() ?: mutableListOf())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MutableList<UserResponse>>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserFollowers failure")
            }

        })
    }
}