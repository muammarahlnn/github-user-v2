package com.ardnn.githubuserv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ardnn.githubuserv2.api.responses.UserDetailResponse
import com.ardnn.githubuserv2.api.responses.UserResponse
import com.ardnn.githubuserv2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.simpleName

    // view binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // view model
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val username = "alba054"
        // debug searched users
        viewModel.getSearchedUsers(username)
        viewModel.searchedUsers.observe(viewLifecycleOwner, { searchedUsers ->
            Log.d(TAG, "Searched users done")
            debugUsers(searchedUsers)
        })

        // debug followings
        viewModel.getUserFollowings(username)
        viewModel.followingList.observe(viewLifecycleOwner, { followingList ->
            Log.d(TAG, "User followings done")
            debugUsers(followingList)
        })

        // debug followers
        viewModel.getUserFollowers(username)
        viewModel.followerList.observe(viewLifecycleOwner, { followerList ->
            Log.d(TAG, "User followers done")
            debugUsers(followerList)
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun debugUsers(list: MutableList<UserResponse>) {
        for (user in list) {
            Log.d(TAG, user.username)
        }
    }

}