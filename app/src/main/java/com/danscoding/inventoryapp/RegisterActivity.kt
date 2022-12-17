package com.danscoding.inventoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.danscoding.inventoryapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnKembali.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            //VALIDASI
            if (email.isEmpty()){
                binding.emailEditText.error = "Email Harus Diisi!"
                binding.emailEditText.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailEditText.error = "Email Tidak Sesuai!"
                binding.emailEditText.requestFocus()
                return@setOnClickListener
            }
            //VALIDASI PASSWORD
            if (password.isEmpty()){
                binding.passwordEditText.error = "Password Harus Diisi!"
                binding.passwordEditText.requestFocus()
                return@setOnClickListener
            }
            //VALIDASI PANJANG PASSWORD
            if (password.length < 6){
                binding.passwordEditText.error = "Minimal Password 6 Karakter!"
                binding.passwordEditText.requestFocus()
                return@setOnClickListener
            }
            RegisterFirebase(email, password)
        }
    }

    private fun RegisterFirebase(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Sudah Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }
}