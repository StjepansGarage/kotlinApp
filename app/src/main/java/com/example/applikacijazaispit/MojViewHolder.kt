package com.example.applikacijazaispit

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MojViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val nazivNamirniceText :TextView = itemView.findViewById(R.id.tvNamirnica)
    val kalorijeText: TextView= itemView.findViewById(R.id.tvCal)

}