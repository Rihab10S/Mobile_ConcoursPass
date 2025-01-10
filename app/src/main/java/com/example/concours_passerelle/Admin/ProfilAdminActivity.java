package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.example.concours_passerelle.R;
import android.view.MenuItem;

public class ProfilAdminActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Configurer la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.nav_AdminDashboard) {
                    startActivity(new Intent(ProfilAdminActivity.this, AdminDashboardActivity.class));
                } else if (item.getItemId() == R.id.nav_profil) {
                    startActivity(new Intent(ProfilAdminActivity.this, ProfilAdminActivity.class));
                } else if (item.getItemId() == R.id.nav_Getion_notes) {
                    startActivity(new Intent(ProfilAdminActivity.this, GestionNotesAdminActivity.class));
                } else if (item.getItemId() == R.id.nav_Gestion_Annonces) {
                    // Gérer l'option "Gestion Annonces"
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Button btnEditProfil = findViewById(R.id.btnEditProfil);
        btnEditProfil.setOnClickListener(v -> {
            // Démarrer EditProfilActivity
            Intent intent = new Intent(ProfilAdminActivity.this, EditProfilAdminActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
