package com.example.cleanarchitecturesolution.features.episode.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cleanarchitecturesolution.databinding.ItemEpisodeBinding
import com.example.cleanarchitecturesolution.features.episode.presentation.adapter.EpisodeAdapter.EpisodeViewHolder
import com.example.cleanarchitecturesolution.features.episode.presentation.model.EpisodeDisplayable

class EpisodeAdapter(itemCallback: ItemCallback<EpisodeDisplayable>) :
    ListAdapter<EpisodeDisplayable, EpisodeViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)
    }

    inner class EpisodeViewHolder(private val binding: ItemEpisodeBinding) :
        ViewHolder(binding.root) {

        fun bind(episode: EpisodeDisplayable) {
            binding.item.text = episode.name
        }
    }
}


