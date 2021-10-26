package com.ardnn.githubuserv2.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.api.responses.UserResponse
import com.ardnn.githubuserv2.databinding.ItemUserFollBinding
import com.ardnn.githubuserv2.listeners.ClickListener
import com.ardnn.githubuserv2.utils.Helper

class UserFollAdapter(
    private val userList: MutableList<UserResponse>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<UserFollAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_foll, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserFollBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClicked(userList[adapterPosition])
            }
        }

        internal fun onBind(user: UserResponse) {
            with (binding) {
                Helper.setImageGlide(itemView.context, user.avatarUrl, ivAva)
                tvUsername.text = user.username
            }
        }
    }

}