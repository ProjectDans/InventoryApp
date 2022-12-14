package com.danscoding.inventoryapp

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    var namaPelanggan : TextView

    @JvmField
    var domisiliPelanggan : TextView

    @JvmField
    var genderPelanggan : TextView

    @JvmField
    var view: CardView

    init {
        namaPelanggan = itemView.findViewById(R.id.tvNamaLengkap)
        domisiliPelanggan = itemView.findViewById(R.id.tvDomisili)
        genderPelanggan = itemView.findViewById(R.id.tvHasilGender)
        view = itemView.findViewById(R.id.cvMain)
    }
}