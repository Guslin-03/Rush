package com.example.rush.ui.restaurant.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.data.model.Menu
import com.example.rush.databinding.SubitemMenuBinding

class DrinksAdapter(
    private val onAddClickListener: (Menu, SubitemMenuBinding) -> Unit,
    private val onSubtractClickListener: (Menu, SubitemMenuBinding) -> Unit
) : ListAdapter<Menu, DrinksAdapter.DrinksViewHolder>(DrinksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        val binding =
            SubitemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val menu = getItem(position)
        holder.bind(menu)

    }

    inner class DrinksViewHolder(private val binding: SubitemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            binding.name.text = menu.name
            binding.price.text = menu.price.toString()
            binding.amountMenu.text = menu.amount.toString()
            binding.subtractMenu.isEnabled = false

            binding.addMenu.setOnClickListener{
                onAddClickListener(menu, binding)
            }

            binding.subtractMenu.setOnClickListener {
                onSubtractClickListener(menu, binding)
            }

        }
    }

    class DrinksDiffCallback : DiffUtil.ItemCallback<Menu>() {

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