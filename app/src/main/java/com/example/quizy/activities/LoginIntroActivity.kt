package com.example.quizy.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizy.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_intro.*

class LoginIntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

        val firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser != null){
            Toast.makeText(this, "User is already logedin", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        btnStart.setOnClickListener{
            redirect("LOGIN")
        }

    }

    private fun redirect(name: String) {
        val intent = when (name) {
            "MAIN" -> Intent(this, MainActivity::class.java)
            "LOGIN" -> Intent(this, LoginActivity::class.java)

            else -> throw Exception("Path not found")
        }
        startActivity(intent)
        finish()
    }


}