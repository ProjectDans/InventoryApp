package com.danscoding.inventoryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danscoding.inventoryapp.Penjualan
import com.danscoding.inventoryapp.R

class PenjualanAdapter(private val penjualanList: List<Penjualan>)
    : RecyclerView.Adapter<PenjualanAdapter.PenjualanViewHolder>() {

    var onItemClick : ((Penjualan) -> Unit)? = null

    class PenjualanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val namaTextView : TextView = itemView.findViewById(R.id.tvNamaPelangganPenjualan)
        val tanggalTextView : TextView = itemView.findViewById(R.id.tvIsiTanggalPenjualan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenjualanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_penjualan, parent, false)
        return PenjualanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenjualanViewHolder, position: Int) {
        val penjualan = penjualanList[position]
        holder.namaTextView.text = penjualan.namaPelanggan
        holder.tanggalTextView.text = penjualan.tanggal

        holder.itemView.setOnClickListener { 
            onItemClick?.invoke(penjualan)
        }
    }

    override fun getItemCount(): Int {
        return penjualanList.size
    }
}