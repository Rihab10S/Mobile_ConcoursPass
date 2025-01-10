package com.example.concours_passerelle.Candidate;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.example.concours_passerelle.EmailVerificationActivity;
import com.example.concours_passerelle.R;

public class CreateProfileCandidateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
        // Démarrer l'activité EmailVerificationActivity
        startActivity(new Intent(CreateProfileCandidateActivity.this, EmailVerificationActivity.class));
    });
    }
}
