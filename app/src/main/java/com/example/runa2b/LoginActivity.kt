package com.example.runa2b

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.runa2b.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.loginButton.setOnClickListener {

            if (checkValidInput()) {
                login()
            }
        }

        binding.registerButton.setOnClickListener {

            if (checkValidInput()) {
                register()
            }
        }

    }
    fun login() {
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        authViewModel.login(email, password, onSuccess = {
//            clearFields()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, onFailure =  {
            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
        })

    }

    fun register() {
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        authViewModel.register(email, password) {

            if (it.isSuccessful) {
//                clearFields()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun checkValidInput() : Boolean {
        var check = true

        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isBlank()) {
            check = false

            Toast.makeText(this, "Username cannot be blank", Toast.LENGTH_SHORT).show()
        }
        if (password.isBlank()) {
            check = false
            Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show()

        }
        if (password.length < 6) {
            check = false
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()

        }

        return check
    }

    fun clearFields() {
        binding.etUsername.text.clear()
        binding.etPassword.text.clear()
    }

}