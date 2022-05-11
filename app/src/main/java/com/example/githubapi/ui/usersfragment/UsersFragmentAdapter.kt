package com.example.githubapi.ui.usersfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi.databinding.ItemUsersBinding
import com.example.githubapi.domain.models.DomainUserModel

class UsersFragmentAdapter(
    private val navigate: (DomainUserModel) -> Unit,
) : ListAdapter<DomainUserModel, UsersFragmentAdapter.UsersViewHolder>(UsersCallback) {

    inner class UsersViewHolder(private val vb: ItemUsersBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: DomainUserModel) {
            vb.root.setOnClickListener { navigate(model) }
            vb.usersItemLogin.text = model.login
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

    companion object UsersCallback : DiffUtil.ItemCallback<DomainUserModel>() {
        override fun areItemsTheSame(oldItem: DomainUserModel, newItem: DomainUserModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DomainUserModel,
            newItem: DomainUserModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}