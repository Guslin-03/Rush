package com.example.rush.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.R

class ProfileAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ProfileAdapter.OptionViewHolder>() {

    private val options = listOf("Mis datos", "Cambiar contraseña", "Pedidos anteriores", "Métodos de pago", "Idioma", "Ayuda")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_option, parent, false)
        return OptionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = options[position]
        holder.bind(option)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val optionTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(option: String) {
            optionTextView.text = option
            itemView.setOnClickListener { onItemClick(option) }
        }
    }
}