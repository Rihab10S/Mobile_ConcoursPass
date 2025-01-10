package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class LaunchingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);

        // Délai de 2 secondes avant de passer à l'écran d'accueil
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Lancer HomeActivity
                startActivity(new Intent(LaunchingActivity.this, HomeActivity.class));
                // Terminer LaunchingActivity
                finish();
            }
        }, 2000); // Délai de 2 secondes
    }
}
