package com.danscoding.inventoryapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danscoding.inventoryapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardPelanggan.setOnClickListener {
            val intent = Intent(this@HomeActivity, PelangganActivity::class.java)
            startActivity(intent)
        }

    }
}