package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.concours_passerelle.Admin.AdminDashboardActivity;
import com.example.concours_passerelle.Candidate.CandidateDashboardActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupération des champs et boutons après setContentView
        EditText useremailField = findViewById(R.id.etEmailLogin);
        EditText passwordField = findViewById(R.id.etPasswordLogin);
        Button loginButton = findViewById(R.id.btnLoginNow);

        // Ajoutez un écouteur pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
        String useremail = useremailField.getText().toString();
        String password = passwordField.getText().toString();

        // Vérification des identifiants et rôle
        if (useremail.equals("admin@gmail.com") && password.equals("admin1234")) {
            // Rediriger vers AdminDashboardActivity si l'utilisateur est un admin
            Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        } else if (useremail.equals("candidat@gmail.com") && password.equals("1234")) {
            // Rediriger vers CandidatDashboardActivity si l'utilisateur est un candidat
            Intent intent = new Intent(LoginActivity.this, CandidateDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Afficher un message d'erreur si les identifiants sont incorrects
            Toast.makeText(LoginActivity.this, "Identifiants incorrects !", Toast.LENGTH_SHORT).show();
        }
    });
    }
}
