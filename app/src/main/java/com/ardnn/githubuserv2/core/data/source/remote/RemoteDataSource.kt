package com.ardnn.githubuserv2.core.data.source.remote

import android.util.Log
import com.ardnn.githubuserv2.core.data.source.remote.network.ApiResponse
import com.ardnn.githubuserv2.core.data.source.remote.network.ApiService
import com.ardnn.githubuserv2.core.data.source.remote.response.UserDetailResponse
import com.ardnn.githubuserv2.core.data.source.remote.response.UserResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource private constructor(private val apiService: ApiService) {

    fun getSearchedUsers(query: String): Flowable<ApiResponse<List<UserResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<UserResponse>>>()

        val client = apiService.getSearchedUsers(query)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.searchedUserDetail
                dataArray?.let {
                    resultData.onNext(
                        if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                        else ApiResponse.Empty
                    )
                }
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getUserDetail(username: String): Flowable<ApiResponse<UserDetailResponse>> {
        val resultData = PublishSubject.create<ApiResponse<UserDetailResponse>>()

        val client = apiService.getUserDetail(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(ApiResponse.Success(response))
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getUserFollowers(username: String): Flowable<ApiResponse<List<UserResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<UserResponse>>>()

        val client = apiService.getUserFollowers(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(
                    if (response.isNotEmpty()) ApiResponse.Success(response)
                    else ApiResponse.Empty
                )
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getUserFollowing(username: String): Flowable<ApiResponse<List<UserResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<UserResponse>>>()

        val client = apiService.getUserFollowing(username)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(
                    if (response.isNotEmpty()) ApiResponse.Success(response)
                    else ApiResponse.Empty
                )
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }
}