package com.example.concours_passerelle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EmailVerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)

        val btnVerify: Button = findViewById(R.id.btnVerify)

        btnVerify.setOnClickListener {
            startActivity(Intent(this, CongratulationsActivity::class.java))
        }
    }
}
