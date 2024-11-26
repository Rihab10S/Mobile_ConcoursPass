package com.example.concours_passerelle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CongratulationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congratulations)

        val btnFinish: Button = findViewById(R.id.btnFinish)

        btnFinish.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
