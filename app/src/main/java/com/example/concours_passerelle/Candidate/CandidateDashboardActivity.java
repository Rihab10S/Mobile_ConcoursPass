package com.example.concours_passerelle.Candidate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.example.concours_passerelle.api.UserApi;
import com.example.concours_passerelle.LoginActivity;
import com.example.concours_passerelle.R;
import com.example.concours_passerelle.models.UserResponse;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateDashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private UserApi userApi; // Votre interface d'API pour l'utilisateur

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatedashboard);

        // Initialisation de DrawerLayout et NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Récupérer l'ID utilisateur passé dans l'Intent
        String userId = getIntent().getStringExtra("user_id");
        if (userId != null) {
            getUserDetails(userId); // Charger les informations de l'utilisateur si l'ID est disponible
        }

        // Configurer la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configuration du toggle pour DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurer le Listener pour les éléments du menu de navigation
        navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_convocation) {
                startActivity(new Intent(CandidateDashboardActivity.this, ConvocationsActivity.class));
            } else if (itemId == R.id.nav_inscription) {
                startActivity(new Intent(CandidateDashboardActivity.this, Form_Candidat.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // Méthode pour récupérer les détails de l'utilisateur
    private void getUserDetails(String userId) {
        // Appel API pour obtenir les informations de l'utilisateur avec l'ID
        Call<UserResponse> call = userApi.getUser(userId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse user = response.body();
                    // Vous pouvez utiliser ici les informations utilisateur, par exemple, les afficher dans l'interface utilisateur
                    Toast.makeText(CandidateDashboardActivity.this, "Bienvenue " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                } else {
                    // En cas d'erreur dans la réponse API
                    Toast.makeText(CandidateDashboardActivity.this, "Erreur lors de la récupération des informations de l'utilisateur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // En cas d'échec de la requête API
                Toast.makeText(CandidateDashboardActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Gestion du retour arrière
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START); // Fermer le tiroir
        } else {
            super.onBackPressed();
        }
    }
}
