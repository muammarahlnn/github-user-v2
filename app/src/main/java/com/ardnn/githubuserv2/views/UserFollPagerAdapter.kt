package com.ardnn.githubuserv2.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.githubuserv2.R

class UserFollPagerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val username: String
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return UserFollFragment.newInstance(position, username)
    }

    override fun getItemCount(): Int {
        return fragmentActivity.resources.getStringArray(R.array.user_foll_tab_text).size
    }

}