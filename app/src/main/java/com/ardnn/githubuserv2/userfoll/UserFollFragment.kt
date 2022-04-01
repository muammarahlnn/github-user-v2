package com.ardnn.githubuserv2.userfoll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.core.data.source.remote.response.UserResponse
import com.ardnn.githubuserv2.databinding.FragmentUserFollBinding
import com.ardnn.githubuserv2.core.util.ClickListener
import com.ardnn.githubuserv2.userdetail.UserDetailFragmentDirections
import com.ardnn.githubuserv2.core.util.Helper

class UserFollFragment : Fragment(), ClickListener {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_USERNAME = "username"

        fun newInstance(index: Int, username: String) =
            UserFollFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }

    private lateinit var viewModel: UserFollViewModel
    private var _binding: FragmentUserFollBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         _binding = FragmentUserFollBinding.inflate(inflater, container, false)

        // set recyclerview
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserFoll.layoutManager = layoutManager

        // get args and set it as parameters on view model
        val section: Int = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
        val username: String = arguments?.getString(ARG_USERNAME, "") ?: ""
        viewModel = ViewModelProvider(this, UserFollViewModelFactory(section, username))
            .get(UserFollViewModel::class.java)

        // subscribe view model
        subscribe(section, username)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding  = null
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()  // to make view pager's height flexible
    }

    private fun subscribe(section: Int, username: String) {
        // fetch user foll depends on its section number
        fetchUserFoll(section)

        // show progressbar
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            Helper.showLoading(binding.progressBar, isLoading)
        })

        // show alert if list is empty
        viewModel.isListEmpty.observe(viewLifecycleOwner, { isListEmpty ->
            showAlert(isListEmpty, resources.getString(R.string.no_users_found))
        })

        // show alert if unable to retrieve data
        viewModel.isFailure.observe(viewLifecycleOwner, { isFailure ->
            showAlert(isFailure, resources.getString(R.string.unable_to_retrieve_data))
        })
    }

    private fun fetchUserFoll(section: Int) {
        when (section) {
            0 -> { // user followers
                viewModel.followerList.observe(viewLifecycleOwner, { followerList ->
                    setUserFoll(followerList)
                })
            }
            1 -> { // user following
                viewModel.followingList.observe(viewLifecycleOwner, { followingList ->
                    setUserFoll(followingList)
                })
            }
        }
    }

    private fun setUserFoll(userFollList: MutableList<UserResponse>) {
        val adapter = UserFollAdapter(userFollList, this)
        binding.rvUserFoll.adapter = adapter
    }

    private fun showAlert(isShow: Boolean, text: String) {
        with (binding) {
            if (isShow) {
                tvAlert.text = text
                tvAlert.visibility = View.VISIBLE
            } else {
                tvAlert.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClicked(user: UserResponse) {
        val toUserDetail = UserDetailFragmentDirections.actionUserDetailFragmentSelf()
        toUserDetail.username = user.username
        findNavController().navigate(toUserDetail)
    }
}