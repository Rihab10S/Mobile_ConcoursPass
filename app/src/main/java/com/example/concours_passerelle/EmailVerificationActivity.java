package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EmailVerificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        // Initialiser le bouton "Verify"
        Button btnVerify = findViewById(R.id.btnVerify);

        // Configurer l'action au clic du bouton
        btnVerify.setOnClickListener(v -> {
        // Démarrer l'activité de félicitations
        startActivity(new Intent(EmailVerificationActivity.this, CongratulationsActivity.class));
    });
    }
}
