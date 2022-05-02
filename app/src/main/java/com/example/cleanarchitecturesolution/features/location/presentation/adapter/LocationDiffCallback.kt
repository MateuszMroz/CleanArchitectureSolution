package com.example.cleanarchitecturesolution.features.location.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationDisplayable

class LocationDiffCallback: DiffUtil.ItemCallback<LocationDisplayable>() {
    override fun areItemsTheSame(
        oldItem: LocationDisplayable,
        newItem: LocationDisplayable,
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: LocationDisplayable,
        newItem: LocationDisplayable,
    ) = oldItem == newItem
}
