package com.example.rush.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rush.R

class ProfileAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ProfileAdapter.OptionViewHolder>() {

    private val options = listOf(
        R.string.mis_datos,
        R.string.cambiar_contraseña,
        R.string.pedidos_anteriores,
        R.string.metodos_de_pago,
        R.string.idioma,
        R.string.ayuda
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_option, parent, false)
        return OptionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val optionResId = options[position]
        holder.bind(holder.itemView.context.getString(optionResId))
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
