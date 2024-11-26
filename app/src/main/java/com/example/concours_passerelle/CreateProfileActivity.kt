package com.example.concours_passerelle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CreateProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        val btnContinue: Button = findViewById(R.id.btnContinue)

        btnContinue.setOnClickListener {
            startActivity(Intent(this, EmailVerificationActivity::class.java))
        }
    }
}
