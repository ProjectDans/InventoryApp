package com.danscoding.inventoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danscoding.inventoryapp.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Tambah Barang")
    }
}