package com.example.githubapi.ui.reposfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.DomainRepoModel
import com.example.githubapi.databinding.ItemReposBinding

class ReposFragmentAdapter(
    private val isFavourite:(String) -> Boolean,
    private val favouriteClickHandler: (DomainRepoModel) -> Boolean,
) : ListAdapter<DomainRepoModel, ReposFragmentAdapter.ReposViewHolder>(ReposCallback) {

    inner class ReposViewHolder(private val vb: ItemReposBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: DomainRepoModel) {
            vb.reposItemName.text = model.name
            vb.reposItemFavourite.isChecked = isFavourite(model.id)
            vb.reposItemFavourite.setOnClickListener {
                vb.reposItemFavourite.isChecked = favouriteClickHandler(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ItemReposBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    companion object ReposCallback : DiffUtil.ItemCallback<DomainRepoModel>() {
        override fun areItemsTheSame(
            oldItem: DomainRepoModel,
            newItem: DomainRepoModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DomainRepoModel,
            newItem: DomainRepoModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}