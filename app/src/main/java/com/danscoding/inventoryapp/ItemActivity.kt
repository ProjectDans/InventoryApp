package com.danscoding.inventoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.danscoding.inventoryapp.adapter.ItemAdapter
import com.danscoding.inventoryapp.databinding.ActivityItemBinding
import kotlinx.coroutines.launch

class ItemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityItemBinding
    private var mAdapter: ItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Daftar Item")

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setAdapter(list: List<Item>) {
        mAdapter?.setData(list)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val itemList = AppDatabase(this@ItemActivity).getItemDao().getAllItem()

            mAdapter = ItemAdapter()
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this@ItemActivity)
                adapter = mAdapter
                    setAdapter(itemList)

                    mAdapter?.setOnActionEditListener {
                        val intent = Intent(this@ItemActivity, AddItemActivity::class.java)
                        intent.putExtra("Data", it)
                        startActivity(intent)
                    }

                    mAdapter?.setOnActionDeleteListener {
                        val builder = AlertDialog.Builder(this@ItemActivity)
                        builder.setMessage("Apakah Ingin Dihapus?")
                        builder.setPositiveButton("Ya") { p0, p1 ->
                            lifecycleScope.launch {
                                AppDatabase(this@ItemActivity).getItemDao().deleteItem(it)
                                val list = AppDatabase(this@ItemActivity).getItemDao().getAllItem()
                                setAdapter(list)
                            }
                            p0.dismiss()
                        }

                        builder.setNegativeButton("NO") { p0, p1 ->
                            p0.dismiss()
                        }

                        val dialog = builder.create()
                        dialog.show()
                    }
                }

            }
        }
    }