package com.ardnn.githubuserv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.api.responses.UserResponse
import com.ardnn.githubuserv2.databinding.ItemUserBinding
import com.bumptech.glide.Glide

class SearchedUserAdapter(
    private val userList: MutableList<UserResponse>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<SearchedUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View, clickListener: ClickListener)
        : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickListener.itemClicked(userList[adapterPosition])
            }
        }

        internal fun onBind(user: UserResponse) {
            with (binding) {
                tvUsername.text = user.username
                Glide.with(itemView.context).load(user.avatarUrl)
                    .into(ivAva)
            }
        }
    }

    interface ClickListener {
        fun itemClicked(user: UserResponse)
    }
}