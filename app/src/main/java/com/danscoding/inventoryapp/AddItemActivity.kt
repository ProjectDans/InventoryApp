package com.danscoding.inventoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.danscoding.inventoryapp.databinding.ActivityAddItemBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import kotlinx.coroutines.launch
import org.json.JSONException
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class AddItemActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private var cardView1: CardView? = null
    private var cardView2: CardView? = null
    private var btnScan: Button? = null
    private var btnEnterCode: Button? = null
    private var btnEnter: Button? = null
    private var edtCode: EditText? = null
    private var hide: Animation? = null
    private var reveal: Animation? = null

    private lateinit var binding: ActivityAddItemBinding
    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Tambah Barang")

        //FITUR QR CODE
        cardView1 = findViewById(R.id.cardView1)
        cardView2 = findViewById(R.id.cardView2)
        btnScan = findViewById(R.id.btnScan)
        btnEnterCode = findViewById(R.id.btnEnterCode)
        btnEnter = findViewById(R.id.btnEnter)
        edtCode = findViewById(R.id.categoryEditText)

        hide = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        reveal = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        cardView2!!.startAnimation(reveal)
        cardView2!!.visibility = View.VISIBLE

        btnScan!!.setOnClickListener {
            cardView1!!.startAnimation(hide)
            cardView2!!.startAnimation(reveal)

            cardView2!!.visibility = View.VISIBLE
            cardView1!!.visibility = View.GONE
        }

        cardView2!!.setOnClickListener {
            cameraTask()
        }

        btnEnter!!.setOnClickListener {

            if (edtCode!!.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Please enter code", Toast.LENGTH_SHORT).show()
            } else {
                var value = edtCode!!.text.toString()

                Toast.makeText(this, value, Toast.LENGTH_SHORT).show()

            }
        }
        btnEnterCode!!.setOnClickListener {
            cardView1!!.startAnimation(reveal)
            cardView2!!.startAnimation(hide)

            cardView2!!.visibility = View.GONE
            cardView1!!.visibility = View.VISIBLE
        }

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

    //FITUR QR CODE
    private fun hasCameraAccess(): Boolean {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)
    }

    private fun cameraTask() {

        if (hasCameraAccess()) {

            var qrScanner = IntentIntegrator(this)
            qrScanner.setPrompt("scan a QR code")
            qrScanner.setCameraId(0)
            qrScanner.setOrientationLocked(true)
            qrScanner.setBeepEnabled(true)
            qrScanner.captureActivity = CaptureActivity::class.java
            qrScanner.initiateScan()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your camera so you can take pictures.",
                123,
                android.Manifest.permission.CAMERA
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        var result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_SHORT).show()
                edtCode!!.setText("")
            } else {
                try {

                    cardView1!!.startAnimation(reveal)
                    cardView2!!.startAnimation(hide)
                    cardView1!!.visibility = View.VISIBLE
                    cardView2!!.visibility = View.GONE
                    edtCode!!.setText(result.contents.toString())
                } catch (exception: JSONException) {
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    edtCode!!.setText("")
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }
}