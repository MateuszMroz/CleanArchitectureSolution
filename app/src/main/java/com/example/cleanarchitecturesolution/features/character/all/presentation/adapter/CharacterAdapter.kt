package com.example.cleanarchitecturesolution.features.character.all.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cleanarchitecturesolution.databinding.ItemCharacterBinding
import com.example.cleanarchitecturesolution.features.character.all.presentation.model.CharacterDisplayable

class CharacterAdapter(itemCallback: ItemCallback<CharacterDisplayable>) :
    ListAdapter<CharacterDisplayable, CharacterAdapter.CharacterViewHolder>(itemCallback) {

    lateinit var onClick: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val onClick: (Int) -> Unit,
    ) : ViewHolder(binding.root) {

        private var characterId: Int? = null

        init {
            binding.root.setOnClickListener {
                characterId?.let { onClick(it) }
            }
        }

        fun bind(character: CharacterDisplayable) {
            characterId = character.id
            binding.item.text = character.name
        }
    }
}
