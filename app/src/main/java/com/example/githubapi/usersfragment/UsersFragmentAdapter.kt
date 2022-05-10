package com.example.githubapi.usersfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi.databinding.ItemUsersBinding
import com.example.githubapi.model.UsersDTO

class UsersFragmentAdapter
    : ListAdapter<UsersDTO, UsersFragmentAdapter.UsersViewHolder>(UsersCallback) {

    inner class UsersViewHolder(private val vb: ItemUsersBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: UsersDTO) {
            vb.userItemLogin.text = model.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    companion object UsersCallback : DiffUtil.ItemCallback<UsersDTO>() {
        override fun areItemsTheSame(oldItem: UsersDTO, newItem: UsersDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UsersDTO, newItem: UsersDTO): Boolean {
            return oldItem == newItem
        }
    }
}