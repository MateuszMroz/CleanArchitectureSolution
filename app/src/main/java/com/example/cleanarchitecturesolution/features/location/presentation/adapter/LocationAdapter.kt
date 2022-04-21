package com.example.cleanarchitecturesolution.features.location.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecturesolution.databinding.ItemLocationBinding
import com.example.cleanarchitecturesolution.features.location.presentation.adapter.LocationAdapter.LocationViewHolder
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationDisplayable

class LocationAdapter(itemCallback: ItemCallback<LocationDisplayable>) :
    ListAdapter<LocationDisplayable, LocationViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: LocationDisplayable) {
            binding.item.text = location.name
        }
    }
}
