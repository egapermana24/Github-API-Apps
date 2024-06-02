package com.example.mygithubuser.appearance

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.userdata.network.response.GithubUser
import com.example.mygithubuser.databinding.ListUserBinding

class ListUsersAdapter : ListAdapter<GithubUser, ListUsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }

    class MyViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: GithubUser) {
            Glide.with(binding.imgAvatar.context)
                .load(users.avatarUrl)
                .into(binding.imgAvatar)
            binding.tvGithubUsername.text = "${users.login}"

            binding.root.setOnClickListener {
                openDetailPage(users)
            }
        }

        private fun openDetailPage(users: GithubUser) {
            val intent = Intent(binding.root.context, DetailsUserGithubActivity::class.java)
            intent.putExtra(USERNAME_GITHUB_KEY, users.login)
            intent.putExtra(AVATAR_GITHUB_KEY, users.avatarUrl)
            binding.root.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }

        const val USERNAME_GITHUB_KEY = "username_data"
        const val AVATAR_GITHUB_KEY = "avatar_data"
    }
}