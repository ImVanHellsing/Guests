package com.example.convidados.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.service.model.GuestModel
import com.example.convidados.view.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(guest: GuestModel) {
        val guestName = itemView.findViewById<TextView>(R.id.row_guest_item)
        guestName.text = guest.name

        guestName.setOnClickListener {
            listener.onClick(guest.id)
        }
    }
}