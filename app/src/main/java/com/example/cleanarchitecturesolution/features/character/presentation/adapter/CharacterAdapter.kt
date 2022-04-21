package com.example.cleanarchitecturesolution.features.character.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cleanarchitecturesolution.databinding.ItemCharacterBinding
import com.example.cleanarchitecturesolution.features.character.presentation.model.CharacterDisplayable

class CharacterAdapter(itemCallback: ItemCallback<CharacterDisplayable>) :
    ListAdapter<CharacterDisplayable, CharacterAdapter.CharacterViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
    ) : ViewHolder(binding.root) {

        fun bind(character: CharacterDisplayable) {
            binding.item.text = character.name
        }
    }
}
