package com.ardnn.githubuserv2.api.repositories

import com.ardnn.githubuserv2.api.callbacks.UserCallback
import com.ardnn.githubuserv2.api.callbacks.UserFollowersCallback
import com.ardnn.githubuserv2.api.callbacks.UserFollowingsCallback
import com.ardnn.githubuserv2.api.responses.UserFollowersResponse
import com.ardnn.githubuserv2.api.responses.UserFollowingsResponse
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
        USER_SERVICE.getUserDetail(username).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body() ?: UserResponse())
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserDetail failure")
            }
        })
    }

    // method to get user followers
    fun getUserFollowers(username: String, callback: UserFollowersCallback) {
        USER_SERVICE.getUserFollowers(username).enqueue(object : Callback<UserFollowersResponse> {
            override fun onResponse(
                call: Call<UserFollowersResponse>,
                response: Response<UserFollowersResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.followerList != null) {
                            callback.onSuccess(response.body()?.followerList ?: mutableListOf())
                        } else {
                            callback.onFailure("response.body().followerList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserFollowersResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserFollowers failure")
            }
        })
    }

    fun getUserFollowings(username: String, callback: UserFollowingsCallback) {
        USER_SERVICE.getUserFollowings(username).enqueue(object : Callback<UserFollowingsResponse> {
            override fun onResponse(
                call: Call<UserFollowingsResponse>,
                response: Response<UserFollowingsResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.followingList != null) {
                            callback.onSuccess(response.body()?.followingList ?: mutableListOf())
                        } else {
                            callback.onFailure("response.body().followingList is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<UserFollowingsResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage ?: "getUserFollowings failure")
            }

        })
    }
}