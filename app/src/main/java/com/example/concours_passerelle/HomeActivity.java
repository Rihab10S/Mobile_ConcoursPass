package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.concours_passerelle.Candidate.CreateProfileCandidateActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialiser les boutons
        Button btnCandidate = findViewById(R.id.btnCandidate);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Configurer l'action au clic sur "Candidate"
        btnCandidate.setOnClickListener(v -> {
        // Démarrer CreateProfileCandidateActivity
        startActivity(new Intent(HomeActivity.this, CreateProfileCandidateActivity.class));
    });

        // Configurer l'action au clic sur "Login"
        btnLogin.setOnClickListener(v -> {
        // Démarrer LoginActivity
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    });
    }
}
