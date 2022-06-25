package com.example.cleanarchitecturesolution.features.episode.all.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.databinding.ItemEpisodeBinding
import com.example.cleanarchitecturesolution.features.episode.all.presentation.adapter.EpisodeAdapter.EpisodeViewHolder
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeDisplayable

class EpisodeAdapter(
    itemCallback: ItemCallback<EpisodeDisplayable>,
) : ListAdapter<EpisodeDisplayable, EpisodeViewHolder>(itemCallback) {

    lateinit var onClick: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)
    }

    inner class EpisodeViewHolder(
        view: View,
        private val onClick: (Int) -> Unit,
    ) : ViewHolder(view) {
        private val binding = ItemEpisodeBinding.bind(view)
        private var episodeId: Int? = null

        init {
            binding.root.setOnClickListener {
                episodeId?.let { onClick(it) }
            }
        }

        fun bind(episode: EpisodeDisplayable) {
            episodeId = episode.id

            "${episode.code} - ${episode.name}".also { binding.item.text = it }
        }
    }
}


