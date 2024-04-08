package com.example.rush.ui.restaurant.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.data.model.Menu
import com.example.rush.databinding.SubitemMenuBinding

class FirstsAdapter : ListAdapter<Menu, FirstsAdapter.FirstsViewHolder>(FirstsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstsViewHolder {
        val binding =
            SubitemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FirstsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FirstsAdapter.FirstsViewHolder, position: Int) {
        val menu = getItem(position)
        holder.bind(menu)

    }

    inner class FirstsViewHolder(private val binding: SubitemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            binding.name.text = menu.name
            binding.price.text = menu.price.toString()
        }
    }

    class FirstsDiffCallback : DiffUtil.ItemCallback<Menu>() {

        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return (oldItem.id == newItem.id
                    && oldItem.name == newItem.name
                    && oldItem.description == newItem.description
                    && oldItem.price == newItem.price
                    && oldItem.type == newItem.type
                    && oldItem.restaurantId == newItem.restaurantId
                    && oldItem.amount == newItem.amount)
        }

    }

}