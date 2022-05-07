package com.example.cleanarchitecturesolution.features.location.all.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecturesolution.databinding.ItemLocationBinding
import com.example.cleanarchitecturesolution.features.location.all.presentation.adapter.LocationAdapter.LocationViewHolder
import com.example.cleanarchitecturesolution.features.location.all.presentation.model.LocationDisplayable

class LocationAdapter(itemCallback: ItemCallback<LocationDisplayable>) :
    ListAdapter<LocationDisplayable, LocationViewHolder>(itemCallback) {

    lateinit var onClick: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }

    inner class LocationViewHolder(
        private val binding: ItemLocationBinding,
        private val onClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var locationId: Int? = null

        init {
            binding.root.setOnClickListener {
                locationId?.let { onClick(it) }
            }
        }

        fun bind(location: LocationDisplayable) {
            locationId = location.id
            binding.item.text = location.name
        }
    }
}
