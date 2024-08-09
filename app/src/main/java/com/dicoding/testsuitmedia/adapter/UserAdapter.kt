package com.dicoding.testsuitmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.testsuitmedia.R
import com.dicoding.testsuitmedia.remote.response.DataItem

class UserAdapter(
    private val users: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName = itemView.findViewById<TextView>(R.id.userNameTextView)
        private val userEmail = itemView.findViewById<TextView>(R.id.userEmailTextView)
        private val userAvatar = itemView.findViewById<ImageView>(R.id.avatarImageView)

        fun bind(user: DataItem) {
            userName.text = "${user.firstName} ${user.lastName}"
            userEmail.text = user.email
            Glide.with(itemView.context).load(user.avatar).into(userAvatar)

            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}
