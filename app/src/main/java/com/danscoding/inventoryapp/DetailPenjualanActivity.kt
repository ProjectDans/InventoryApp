package com.danscoding.inventoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class DetailPenjualanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_penjualan)
        supportActionBar?.title = "Detail Penjualan"

        val penjualan = intent.getParcelableExtra<Penjualan>("penjualan")
        if (penjualan != null){
            val namaEditText : TextView = findViewById(R.id.namaPelangganEditText)
            val tanggalEditText: TextView = findViewById(R.id.tanggalEditText)

            namaEditText.text = penjualan.namaPelanggan
            tanggalEditText.text = penjualan.tanggal

        }
    }
}