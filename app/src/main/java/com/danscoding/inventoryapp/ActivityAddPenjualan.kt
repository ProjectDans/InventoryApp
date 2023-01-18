package com.danscoding.inventoryapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.danscoding.inventoryapp.databinding.ActivityAddPenjualanBinding
import java.util.Calendar

class ActivityAddPenjualan : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    private lateinit var binding: ActivityAddPenjualanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Tambah Data Penjualan"

        pickDate()

        binding.btnTambahItem.setOnClickListener {
            Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ActivityPenjualan::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        findViewById<ImageView>(R.id.btnDate).setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        findViewById<TextView>(R.id.tvIsiTanggal).text = "$savedDay-$savedMonth-$savedYear"
    }
}