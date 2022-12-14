package com.danscoding.inventoryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class PelangganAdapter(private val context: Context,
                       private val daftarPelanggan: ArrayList<ModelPelanggan?>?) : RecyclerView.Adapter<MainViewHolder>() {
    private val listener: FirebaseDataListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nama_pelanggan, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.namaPelanggan.text = "Nama   : " + (daftarPelanggan?.get(position)?.nama)
        holder.domisiliPelanggan.text = "Domisili     : " + daftarPelanggan?.get(position)?.domisili
        holder.genderPelanggan.text = "Gender   : " + daftarPelanggan?.get(position)?.gender
        holder.view.setOnClickListener { listener.onDataClick(daftarPelanggan?.get(position), position) }
    }

    override fun getItemCount(): Int {

        return daftarPelanggan?.size!!
    }

    //interface data listener
    interface FirebaseDataListener {
        fun onDataClick(pelanggan: ModelPelanggan?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }
}