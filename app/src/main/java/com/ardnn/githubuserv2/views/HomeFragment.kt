package com.ardnn.githubuserv2.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.views.SearchedUserAdapter
import com.ardnn.githubuserv2.api.responses.UserResponse
import com.ardnn.githubuserv2.databinding.FragmentHomeBinding
import com.ardnn.githubuserv2.viewmodels.HomeViewModel
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment(), SearchedUserAdapter.ClickListener {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // set recyclerview
        val layoutManager = LinearLayoutManager(activity)
        binding.rvUser.layoutManager = layoutManager

        // observe searched users
        viewModel.searchedUsers.observe(viewLifecycleOwner, { searchedUsers ->
            setSearchedUsers(searchedUsers)
        })

        // check if username field is not empty then remove the error
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                binding.inputLayoutUsername.error = null
            }
        }

        // show alert or not
        viewModel.isSearched.observe(viewLifecycleOwner, { isSearched ->
            if (isSearched) hideNotSearchedYetAlert()
        })

        // show alert if list is empty
        viewModel.isSearchedUsersEmpty.observe(viewLifecycleOwner, { isEmpty ->
            showAlertUsersNotFound(isEmpty)
        })

        // show progressbar
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })

        // if search button clicked
        binding.inputLayoutUsername.setEndIconOnClickListener { view ->
            btnSearchCLicked(view)
        }
        binding.etUsername.setOnEditorActionListener { v, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    btnSearchCLicked(v)
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun btnSearchCLicked(view: View) {
        // get query from edit text
        val searched = binding.etUsername.text.toString()
        if (searched.isEmpty()) {
            // set error to the input layout
            binding.inputLayoutUsername.error = resources.getString(R.string.empty_field_alert)
        } else {
            // set recyclerview
            viewModel.setSearchedUsers(searched)
        }
1
        // hide keyboard
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideNotSearchedYetAlert() {
        binding.tvAlertNotSearchedYet.visibility = View.INVISIBLE
    }

    private fun showAlertUsersNotFound(isEmpty: Boolean) {
        binding.tvAlertUsersNotFound.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setSearchedUsers(searchedUsers: MutableList<UserResponse>) {
        val adapter = SearchedUserAdapter(searchedUsers, this)
        binding.rvUser.adapter = adapter
    }

    override fun itemClicked(user: UserResponse) {
        val toUserDetail = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment()
        toUserDetail.username = user.username
        findNavController().navigate(toUserDetail)
    }

}