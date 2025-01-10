package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.example.concours_passerelle.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.example.concours_passerelle.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

        // Initialiser DrawerLayout et NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Initialisation de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Toggle pour le DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.nav_AdminDashboard) {
                    startActivity(new Intent(AdminDashboardActivity.this, AdminDashboardActivity.class));
                } else if (item.getItemId() == R.id.nav_profil) {
                    startActivity(new Intent(AdminDashboardActivity.this, ProfilAdminActivity.class));
                } else if (item.getItemId() == R.id.nav_liste_preinscrit) {
                    startActivity(new Intent(AdminDashboardActivity.this, PreinscritListActivity.class));
                }else if (item.getItemId() == R.id.nav_Getion_notes) {
                    startActivity(new Intent(AdminDashboardActivity.this, GestionNotesAdminActivity.class));
                } else if (item.getItemId() == R.id.nav_Gestion_Annonces) {
                    startActivity(new Intent(AdminDashboardActivity.this, AnnonceListActivity.class));
                }else if (item.getItemId() == R.id.nav_déconnexion) {
                    startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }

        else {
            super.onBackPressed();
        }
    }
}
