package com.ardnn.githubuserv2.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.api.responses.UserDetailResponse
import com.ardnn.githubuserv2.databinding.FragmentUserDetailBinding
import com.ardnn.githubuserv2.utils.Helper
import com.ardnn.githubuserv2.viewmodels.UserDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailFragment : Fragment() {

    private lateinit var viewModel: UserDetailViewModel
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]

        // get args
        val username: String = UserDetailFragmentArgs.fromBundle(arguments as Bundle).username

        // fetch user detail
        viewModel.fetchUserDetail(username)
        viewModel.user.observe(viewLifecycleOwner, { user ->
            setUserDetailToWidgets(user)
        })

        // show progressbar
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })

        // set viewpager
        val userFollPagerAdapter = UserFollPagerAdapter(requireActivity())
        binding.vp2.adapter = userFollPagerAdapter

        // set tab layout
        val countFolls = intArrayOf(21, 12)
        TabLayoutMediator(binding.tabLayout, binding.vp2) { tab, pos ->
            tab.text = String.format(resources.getStringArray(R.array.user_foll_tab_text)[pos], countFolls[pos])
        }.attach()
        Helper.equalingEachTabWidth(binding.tabLayout)

        // if button clicked
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserDetailToWidgets(user: UserDetailResponse) {
        with (binding) {
            Glide.with(requireContext()).load(user.avatarUrl)
                .into(ivAva)
            tvUsername.text = user.username
            tvName.text = user.name ?: "-"
            tvLocation.text = user.location ?: "-"
            tvCompany.text = user.company ?: "-"
            tvRepositories.text = (user.repositories ?: 0).toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with (binding) {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                ivLocation.visibility = View.INVISIBLE
                ivCompany.visibility = View.INVISIBLE
                ivRepositories.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                ivLocation.visibility = View.VISIBLE
                ivCompany.visibility = View.VISIBLE
                ivRepositories.visibility = View.VISIBLE
            }
        }
    }

}