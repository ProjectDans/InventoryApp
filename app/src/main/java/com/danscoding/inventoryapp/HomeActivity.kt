package com.danscoding.inventoryapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danscoding.inventoryapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.cardPelanggan.setOnClickListener {
            val intent = Intent(this@HomeActivity, PelangganActivity::class.java)
            startActivity(intent)
        }
        binding.cardItemBarang.setOnClickListener{
            val intent = Intent(this, ItemActivity::class.java)
            startActivity(intent)
        }
        binding.cardPenjualan.setOnClickListener {
            val intent = Intent(this, ActivityAddPenjualan::class.java)
            startActivity(intent)
        }
        binding.cardLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}