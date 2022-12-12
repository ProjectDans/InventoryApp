package com.danscoding.inventoryapp

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.ActivityId
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.nio.file.attribute.AclEntry.Builder

class DaftarPelangganActivity : AppCompatActivity(), PelangganAdapter.FirebaseDataListener {

    private var mFloatingActionButton: ExtendedFloatingActionButton? = null
    private var mEditNama: EditText? = null
    private var mEditDomisili: EditText? = null
    private var mEditGender: EditText? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: PelangganAdapter? = null
    private var daftarPelanggan: ArrayList<ModelPelanggan>? = null
    private var databaseReference: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_pelanggan)
        if (Build.VERSION.SDK_INT>=19 && Build.VERSION.SDK_INT < 21){
            DaftarPelangganActivity.Companion.setWindowFlag(
                this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                true
            )
        }
        if (Build.VERSION.SDK_INT >=19){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }
        if (Build.VERSION.SDK_INT >= 21){
            DaftarPelangganActivity.Companion.setWindowFlag(
                this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
        FirebaseApp.initializeApp(this)
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseInstance!!.getReference("pelanggan")
        mDatabaseReference!!.child("data_pelanggan")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    daftarPelanggan = ArrayList<ModelPelanggan>()
                    for (mDataSnapshot in dataSnapshot.children) {
                        val pelanggan: ModelPelanggan = mDataSnapshot.getValue(ModelPelanggan::class.java)
                        pelanggan.setKey(mDataSnapshot.key)
                        daftarPelanggan!!.add(pelanggan)
                    }
                    mAdapter = PelangganAdapter(this@DaftarPelangganActivity, daftarPelanggan)
                    mRecyclerView.setAdapter(mAdapter)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@DaftarPelangganActivity,
                        databaseError.details + " " + databaseError.message, Toast.LENGTH_LONG
                    ).show()
                }
            })
        mFloatingActionButton = findViewById(R.id.tambah_pelanggan)
        mFloatingActionButton.setOnClickListener(View.OnClickListener { dialogTambahPelanggan() })
    }

    fun onDataClick(barang: ModelPelanggan, position: Int) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Pilih Aksi")
        builder.setPositiveButton(
            "UPDATE"
        ) { dialog, id -> dialogUpdatePelanggan(barang) }
        builder.setNegativeButton(
            "HAPUS"
        ) { dialog, id -> hapusDataPelanggan(barang) }
        builder.setNeutralButton(
            "BATAL"
        ) { dialog, id -> dialog.dismiss() }
        val dialog: Dialog = builder.create()
        dialog.show()
    }

    private fun dialogTambahPelanggan() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tambah Data Pelanggan")
        val view: View = layoutInflater.inflate(R.layout.layout_edit_pelanggan, null)
        mEditNama = view.findViewById(R.id.nama_lengkap)
        mEditDomisili = view.findViewById(R.id.domisili)
        mEditGender = view.findViewById(R.id.gender)
        builder.setView(view)
        builder.setPositiveButton(
            "SIMPAN"
        ) { dialog, id ->
            val namaPelanggan = mEditNama.getText().toString()
            val domisiliPelanggan = mEditDomisili.getText().toString()
            val genderPelanggan = mEditGender.getText().toString()
            if (!namaPelanggan.isEmpty() && !domisiliPelanggan.isEmpty() && !genderPelanggan.isEmpty()) {
                submitDataPelanggan(ModelPelanggan(namaPelanggan, domisiliPelanggan, genderPelanggan))
            } else {
                Toast.makeText(this@DaftarPelangganActivity, "Data Harus Diisi!", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton(
            "BATAL"
        ) { dialog, id -> dialog.dismiss() }
        val dialog: Dialog = builder.create()
        dialog.show()
    }

    private fun dialogUpdatePelanggan(pelanggan: ModelPelanggan?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Data Model Pelanggan")
        val view: View = layoutInflater.inflate(R.layout.layout_edit_pelanggan, null)
        mEditNama = view.findViewById(R.id.nama_lengkap)
        mEditDomisili = view.findViewById(R.id.domisili)
        mEditGender = view.findViewById(R.id.gender)
        mEditNama.setText(pelanggan.getNama())
        mEditDomisili.setText(pelanggan.getDomisili())
        mEditGender.setText(pelanggan.getGender())
        builder.setView(view)
        if (pelanggan != null){
            builder.setPositiveButton(
                "SIMPAN"
            ){ dialog, id ->
                pelanggan.setNama(mEditNama.getText().toString())
                pelanggan.setDomisili(mEditDomisili.getText().toString())
                pelanggan.setGender(mEditGender.getText().toString())
                updateDataPelanggan(pelanggan)
            }
        }
        builder.setNegativeButton(
            "BATAL"
        ){ dialog, id -> dialog.dismiss() }
        val dialog: Dialog = builder.create()
        dialog.show()
    }

    private fun submitDataPelanggan(pelanggan: ModelPelanggan){
        mDatabaseReference!!.child("data_pelanggan").push()
            .setValue(pelanggan).addOnSuccessListener(
                this
            ) {
                Toast.makeText(
                    this@DaftarPelangganActivity,
                    "Data pelanggan berhasil disimpan",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun updateDataPelanggan(pelanggan: ModelPelanggan) {
        mDatabaseReference!!.child("data_pelanggan").child(pelanggan.getKey())
            .setValue(pelanggan).addOnSuccessListener {
                Toast.makeText(
                    this@DaftarPelangganActivity,
                    "Data berhasil di update !",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun hapusDataPelanggan(pelanggan: ModelPelanggan){
        if (mDatabaseReference != null) {
            mDatabaseReference!!.child("data_pelanggan").child(pelanggan.getKey())
                .removeValue().addOnSuccessListener {
                    Toast.makeText(
                        this@DaftarPelangganActivity,
                        "Data berhasil dihapus!",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    companion object{
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }
}