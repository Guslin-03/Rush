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
) : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)

        holder.itemView.setOnClickListener {
            onClickListener(restaurant)
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

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.name.text = restaurant.name
            binding.rating.text = restaurant.rating.toString()
            binding.numberReviews.text = "(${restaurant.numberReviews})"
            binding.culinaryStyle.text = restaurant.originType
            binding.specialty.text = restaurant.specialty
            binding.location.text = restaurant.location
        }
    }

    class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {

        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return (oldItem.id == newItem.id
                    && oldItem.name == newItem.name
                    && oldItem.originType == newItem.originType
                    && oldItem.specialty == newItem.specialty
                    && oldItem.rating == newItem.rating
                    && oldItem.numberReviews == newItem.numberReviews
                    && oldItem.priceRange == newItem.priceRange
                    && oldItem.location == newItem.location)
        }

    }

}