package com.example.rush.ui.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.data.model.Restaurant
import com.example.rush.databinding.ItemRestaurantBinding

class RestaurantAdapter(
    private val onClickListener: (Restaurant) -> Unit
) : ListAdapter<Restaurant, RestaurantAdapter.GroupViewHolder>(GroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.GroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)

        holder.itemView.setOnClickListener {
            onClickListener(group)
        }

    }

//    fun filtrateTypeGroup(listGroups: List<Restaurant>, typeGroup: ChatEnumType): List<Restaurant> {
//
//
//        val filteredGroups: List<Restaurant>?
//
//        filteredGroups = listGroups.filter { it.type == typeGroup.toString() }
//        submitList(filteredGroups.toList())
//        return filteredGroups
//    }

    inner class GroupViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: Restaurant) {
//            binding.groupName.text = group.name

        }
    }

    class GroupDiffCallback : DiffUtil.ItemCallback<Restaurant>() {

        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return (oldItem.id == newItem.id
                    && oldItem.name == newItem.name
                    && oldItem.location == newItem.location)
        }

    }

}