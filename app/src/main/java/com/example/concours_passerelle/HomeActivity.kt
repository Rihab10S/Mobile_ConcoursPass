package com.example.concours_passerelle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnCandidate: Button = findViewById(R.id.btnCandidate)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnCandidate.setOnClickListener {
            startActivity(Intent(this, CreateProfileActivity::class.java))
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
