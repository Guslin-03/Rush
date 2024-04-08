package com.example.rush.ui.restaurant.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.data.model.Menu
import com.example.rush.databinding.SubitemMenuBinding

class DessertAdapter : ListAdapter<Menu, DessertAdapter.DessertViewHolder>(DessertDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertViewHolder {
        val binding =
            SubitemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DessertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DessertAdapter.DessertViewHolder, position: Int) {
        val menu = getItem(position)
        holder.bind(menu)

    }

    inner class DessertViewHolder(private val binding: SubitemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            binding.name.text = menu.name
            binding.price.text = menu.price.toString()
        }
    }

    class DessertDiffCallback : DiffUtil.ItemCallback<Menu>() {

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