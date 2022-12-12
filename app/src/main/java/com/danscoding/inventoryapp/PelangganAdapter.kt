package com.danscoding.inventoryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class PelangganAdapter(private val context: android.content.Context, private val daftarPelanggan: ArrayList<ModelPelanggan?>?) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_nama_pelanggan, parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val generator : ColorGenerator = ColorGenerator.MATERIAL
        val color : Int = generator.randomColor
        holder.view.setCardBackgroundColor(color)
        holder.namaPelanggan.text = "Nama     : " + (daftarPelanggan?.get(position)?.namaPelanggan)
        holder.domisiliPelanggan.text = "Domisili"
    }
}