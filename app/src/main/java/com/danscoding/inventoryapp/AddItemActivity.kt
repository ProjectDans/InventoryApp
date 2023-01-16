package com.danscoding.inventoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.danscoding.inventoryapp.databinding.ActivityAddItemBinding
import kotlinx.coroutines.launch

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Tambah Barang")

        binding.btnTambahItem.setOnClickListener { addItem() }
    }

    private fun addItem() {
        val namaItem = binding.nameEditText.text.toString()
        val kategoriItem = binding.categoryEditText.text.toString()
        val hargaItem = binding.hargaEditText.text.toString()

        lifecycleScope.launch {
            val item = Item(namaItem, kategoriItem, hargaItem)
            AppDatabase(this@AddItemActivity).getItemDao().addItem(item)
            finish()
        }
    }
}