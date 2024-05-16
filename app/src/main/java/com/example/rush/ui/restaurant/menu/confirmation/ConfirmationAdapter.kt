package com.example.rush.ui.restaurant.menu.confirmation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.data.model.Menu
import com.example.rush.databinding.ItemConfirmationBinding

class ConfirmationAdapter : ListAdapter<Menu, ConfirmationAdapter.ConfirmationViewHolder>(ConfirmationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmationViewHolder {
        val binding =
            ItemConfirmationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConfirmationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConfirmationAdapter.ConfirmationViewHolder, position: Int) {
        val menu = getItem(position)
        holder.bind(menu)
    }

    inner class ConfirmationViewHolder(private val binding: ItemConfirmationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            binding.name.text = menu.name
            binding.price.text = menu.price.toString()
            binding.amount.text = menu.amount.toString()
        }
    }

    class ConfirmationDiffCallback : DiffUtil.ItemCallback<Menu>() {

        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return (oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.price == newItem.price &&
                    oldItem.amount == newItem.amount)
        }

    }

}