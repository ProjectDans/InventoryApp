package com.danscoding.inventoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.danscoding.inventoryapp.databinding.ActivityAddItemBinding
import kotlinx.coroutines.launch

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Tambah Barang")

        item = intent.getSerializableExtra("Data") as? Item

        if (item == null) binding.btnAddOrUpdateItem.text = "Tambah Item"
        else {
            binding.btnAddOrUpdateItem.text = "Update"
            binding.nameEditText.setText(item?.itemName.toString())
            binding.hargaEditText.setText(item?.itemPrice.toString())
            binding.categoryEditText.setText(item?.itemCategory.toString())
        }

        binding.btnAddOrUpdateItem.setOnClickListener { addItem() }
    }

    private fun addItem() {
        val namaItem = binding.nameEditText.text.toString()
        val kategoriItem = binding.categoryEditText.text.toString()
        val hargaItem = binding.hargaEditText.text.toString()


        //VALIDASI
        if (namaItem.isEmpty()){
            binding.nameEditText.error = "Harus Diisi!"
            binding.nameEditText.requestFocus()
            return
        }

        lifecycleScope.launch {
            if (item == null){
                val item = Item(itemName = namaItem, itemPrice = hargaItem, itemCategory = kategoriItem)
                AppDatabase(this@AddItemActivity).getItemDao().addItem(item)
                finish()
            }else{
                val i = Item(itemName = namaItem, itemPrice = hargaItem, itemCategory = kategoriItem)
                i.id = item?.id ?: 0
                AppDatabase(this@AddItemActivity).getItemDao().updateItem(i)
                finish()
            }
        }
    }
}