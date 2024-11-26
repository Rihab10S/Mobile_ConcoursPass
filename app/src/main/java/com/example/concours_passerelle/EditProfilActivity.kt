package com.example.concours_passerelle

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EditProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize the input fields and button
        val etFirstName: EditText = findViewById(R.id.etFirstName)
        val etLastName: EditText = findViewById(R.id.etLastName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val btnSaveProfile: Button = findViewById(R.id.btnSaveProfile)

        // Handle the save button click event
        btnSaveProfile.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            // Validate the form
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                 Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show()


                finish()
            }
        }
    }
}
