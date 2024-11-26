package com.example.concours_passerelle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Récupération des champs et boutons après setContentView
        val useremailField = findViewById<EditText>(R.id.etEmailLogin)
        val passwordField = findViewById<EditText>(R.id.etPasswordLogin)
        val loginButton = findViewById<Button>(R.id.btnLoginNow)

        // Ajoutez un écouteur pour le bouton de connexion
        loginButton.setOnClickListener {
            val useremail = useremailField.text.toString()
            val password = passwordField.text.toString()

            // Vérification des identifiants
            if (useremail == "candidat@gmail.com" && password == "1234") {
                // Rediriger vers DashboardActivity
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Affiche un message d’erreur
                Toast.makeText(this, "Identifiants incorrects !", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
