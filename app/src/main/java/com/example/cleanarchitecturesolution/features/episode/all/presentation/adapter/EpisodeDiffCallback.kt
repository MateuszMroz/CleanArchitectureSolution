package com.example.cleanarchitecturesolution.features.episode.all.presentation.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeDisplayable

class EpisodeDiffCallback : ItemCallback<EpisodeDisplayable>() {

    override fun areItemsTheSame(
        oldItem: EpisodeDisplayable,
        newItem: EpisodeDisplayable,
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: EpisodeDisplayable,
        newItem: EpisodeDisplayable,
    ) = oldItem == newItem

}
