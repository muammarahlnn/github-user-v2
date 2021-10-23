package com.ardnn.githubuserv2.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.api.responses.UserResponse
import com.ardnn.githubuserv2.databinding.FragmentUserFollBinding
import com.ardnn.githubuserv2.listeners.ClickListener
import com.ardnn.githubuserv2.utils.Helper
import com.ardnn.githubuserv2.viewmodels.UserFollViewModel
import com.ardnn.githubuserv2.viewmodels.UserFollViewModelFactory

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

        // get args
        val section: Int = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
        val username: String = arguments?.getString(ARG_USERNAME, "") ?: ""

        // fetch user foll depends on its section number
        viewModel = ViewModelProvider(this, UserFollViewModelFactory(section, username))
            .get(UserFollViewModel::class.java)
        fetchUserFoll(section)

        // show progressbar
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            Helper.showLoading(binding.progressBar, isLoading)
        })

        // show alert if list is empty
        viewModel.isListEmpty.observe(viewLifecycleOwner, { isListEmpty ->
            showAlert(isListEmpty)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding  = null
    }

    /*
        another way to make view pager's height flexible, but it's not smooth enough
        so i keep it here in case i need it in the future
        override fun onResume() {
            super.onResume()
            binding.root.requestLayout()
        }
     */

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

    private fun showAlert(isListEmpty: Boolean) {
        binding.tvAlert.visibility = if (isListEmpty) View.VISIBLE else View.INVISIBLE
    }

    override fun onItemClicked(user: UserResponse) {
        Toast.makeText(requireContext(), user.username, Toast.LENGTH_SHORT).show()
    }
}