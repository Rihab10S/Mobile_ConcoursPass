package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CongratulationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        // Initialiser le bouton "Finish"
        Button btnFinish = findViewById(R.id.btnFinish);

        // Configurer l'action au clic du bouton
        btnFinish.setOnClickListener(v -> {
        // Rediriger l'utilisateur vers l'écran de connexion
        startActivity(new Intent(CongratulationsActivity.this, LoginActivity.class));
    });
    }
}
