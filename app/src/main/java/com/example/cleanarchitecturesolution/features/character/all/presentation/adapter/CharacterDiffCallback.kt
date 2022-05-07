package com.example.cleanarchitecturesolution.features.character.all.presentation.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.features.character.all.presentation.model.CharacterDisplayable

class CharacterDiffCallback : ItemCallback<CharacterDisplayable>() {
    override fun areItemsTheSame(
        oldItem: CharacterDisplayable,
        newItem: CharacterDisplayable,
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CharacterDisplayable,
        newItem: CharacterDisplayable,
    ) = oldItem == newItem
}
