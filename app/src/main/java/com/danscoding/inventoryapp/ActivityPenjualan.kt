package com.danscoding.inventoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danscoding.inventoryapp.adapter.PenjualanAdapter
import com.danscoding.inventoryapp.databinding.ActivityPenjualanBinding

class ActivityPenjualan : AppCompatActivity() {

    private lateinit var binding: ActivityPenjualanBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var penjualanList: ArrayList<Penjualan>
    private lateinit var penjualanAdapter: PenjualanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Daftar Penjualan"

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, ActivityAddPenjualan::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = binding.rvPenjualan
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        penjualanList = ArrayList()

        penjualanList.add(Penjualan("Warsito Doyok", "18-01-2023"))
        penjualanList.add(Penjualan("Anto Rosidi", "18-01-2023"))
        penjualanList.add(Penjualan("Bayu Eka", "18-01-2023"))
        penjualanList.add(Penjualan("Wijayanto", "18-01-2023"))
        penjualanList.add(Penjualan("Mang Ucup", "18-01-2023"))
        penjualanList.add(Penjualan("Kang Maman", "18-01-2023"))
        penjualanList.add(Penjualan("Siti", "18-01-2023"))
        penjualanList.add(Penjualan("Teh Neneng", "18-01-2023"))
        penjualanList.add(Penjualan("Yadi Pengkolan", "18-01-2023"))
        penjualanList.add(Penjualan("Haryanto", "12-04-2023"))
        penjualanList.add(Penjualan("Budi Hajati", "15-04-2023"))
        penjualanList.add(Penjualan("Rohman", "19-05-2023"))

        penjualanAdapter = PenjualanAdapter(penjualanList)
        recyclerView.adapter = penjualanAdapter

        penjualanAdapter.onItemClick = {
            val intent = Intent(this, DetailPenjualanActivity::class.java)
            intent.putExtra("penjualan", it)
            startActivity(intent)
        }
    }
}