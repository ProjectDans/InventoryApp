package com.danscoding.inventoryapp.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danscoding.inventoryapp.Item
import com.danscoding.inventoryapp.R

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var list = mutableListOf<Item>()
    private var actionUpdate: ((Item)-> Unit)? = null
    private var actionDelete: ((Item)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_barang, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.namaitem.text = item.itemName
        holder.hargaItem.text = item.itemPrice
        holder.kategoriItem.text = item.itemCategory

        holder.actionUpdate.setOnClickListener { actionUpdate?.invoke(item) }
        holder.actionDelete.setOnClickListener { actionDelete?.invoke(item) }
    }

    override fun getItemCount() = list.size

    fun setData(data: List<Item>){
        list.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun setOnActionEditListener(callback: (Item) -> Unit){
        this.actionUpdate = callback
    }
    fun setOnActionDeleteListener(callback: (Item) -> Unit){
        this.actionDelete = callback
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val namaitem: TextView = itemView.findViewById(R.id.tvNamaBarang)
        val hargaItem: TextView = itemView.findViewById(R.id.tvHargaBarang)
        val kategoriItem: TextView = itemView.findViewById(R.id.tvKategori)
        val actionUpdate: ImageView = itemView.findViewById(R.id.action_update)
        val actionDelete: ImageView = itemView.findViewById(R.id.action_delete)
    }
}