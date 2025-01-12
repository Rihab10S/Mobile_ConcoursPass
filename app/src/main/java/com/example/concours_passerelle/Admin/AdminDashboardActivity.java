package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.concours_passerelle.Annonce;
import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.LoginActivity;
import com.example.concours_passerelle.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private TextView tvTotalPreinscrits;
    private TextView tvNombreTotal;
    private RecyclerView recyclerViewAnnonces;
    private TextView noAnnoncesMessage;
    private ProgressBar progressBar;
    private TextView tvProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

        // Initialiser DrawerLayout et NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        tvTotalPreinscrits = findViewById(R.id.tv_total_preinscrits);
        tvNombreTotal = findViewById(R.id.tv_nombre_total);


        loadTotalPreinscrits();

        // Initialisation de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Toggle pour le DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurer les éléments de navigation
        navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_AdminDashboard) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminDashboardActivity.class));
            } else if (itemId == R.id.nav_profil) {
                startActivity(new Intent(AdminDashboardActivity.this, ProfilAdminActivity.class));
            } else if (itemId == R.id.nav_liste_preinscrit) {
                startActivity(new Intent(AdminDashboardActivity.this, PreinscritListActivity.class));
            } else if (itemId == R.id.nav_Getion_notes) {
                startActivity(new Intent(AdminDashboardActivity.this, GestionNotesAdminActivity.class));
            } else if (itemId == R.id.nav_Gestion_Annonces) {
                startActivity(new Intent(AdminDashboardActivity.this, AnnonceListActivity.class));
            } else if (itemId == R.id.nav_déconnexion) {
                startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Initialiser les éléments pour les annonces
        recyclerViewAnnonces = findViewById(R.id.recycler_view_dashboard_annonces);
        recyclerViewAnnonces.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        noAnnoncesMessage = findViewById(R.id.no_annonces_message);

        // Charger les annonces
        loadAnnonces();
    }
    private void loadTotalPreinscrits() {
        PreinscritApi api = RetrofitInstance.getPreinscritApi();
        Call<List<Candidat>> call = api.getCandidats();

        call.enqueue(new Callback<List<Candidat>>() {
            @Override
            public void onResponse(Call<List<Candidat>> call, Response<List<Candidat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Candidat> candidats = response.body();

                    // Calcul du total des préinscrits
                    int totalPreinscrits = candidats.size();

                    // Mise à jour de la TextView pour le total des préinscrits
                    TextView tvNombreTotal = findViewById(R.id.tv_nombre_total);
                    tvNombreTotal.setText(String.valueOf(totalPreinscrits));
                    tvNombreTotal.setTextColor(Color.parseColor("#D72638"));  // Rouge
                    tvNombreTotal.setTextSize(32);  // Taille plus grande

                    // Mise à jour des autres TextViews avec les statistiques générales
                    tvTotalPreinscrits.setText("Candidats préinscrits :");

                    // Calcul des préinscrits par filière
                    Map<String, Integer> preinscritsParFiliere = new HashMap<>();
                    for (Candidat candidat : candidats) {
                        String filiere = candidat.getFiliereChoisi();
                        preinscritsParFiliere.put(filiere, preinscritsParFiliere.getOrDefault(filiere, 0) + 1);
                    }

                    // Mise à jour des informations sur les deux premières filières les plus demandées
                    updateFiliereStats(preinscritsParFiliere, totalPreinscrits);
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Erreur de chargement des candidats.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Candidat>> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Méthode pour mettre à jour les TextViews des filières
    private void updateFiliereStats(Map<String, Integer> preinscritsParFiliere, int totalPreinscrits) {
        if (!preinscritsParFiliere.isEmpty()) {
            // Trier les filières par nombre de préinscrits (du plus grand au plus petit)
            List<Map.Entry<String, Integer>> sortedFiliereList = new ArrayList<>(preinscritsParFiliere.entrySet());
            sortedFiliereList.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

            // Première filière
            Map.Entry<String, Integer> firstEntry = sortedFiliereList.get(0);
            updateFiliereView(R.id.tv_filiere_1, R.id.tv_filiere_1_percent, firstEntry, totalPreinscrits);

            // Deuxième filière (si elle existe)
            if (sortedFiliereList.size() > 1) {
                Map.Entry<String, Integer> secondEntry = sortedFiliereList.get(1);
                updateFiliereView(R.id.tv_filiere_2, R.id.tv_filiere_2_percent, secondEntry, totalPreinscrits);
            }
        }
    }

    // Méthode pour mettre à jour les TextViews d'une filière
    private void updateFiliereView(int filiereTextViewId, int filierePercentTextViewId, Map.Entry<String, Integer> entry, int totalPreinscrits) {
        String filiere = entry.getKey();
        int preinscritsFiliere = entry.getValue();
        int percentFiliere = (preinscritsFiliere * 100) / totalPreinscrits;

        TextView tvFiliere = findViewById(filiereTextViewId);
        tvFiliere.setText(filiere);

        TextView tvFilierePercent = findViewById(filierePercentTextViewId);
        tvFilierePercent.setText(percentFiliere + "% des pré-inscrits");
    }

    /**
     * Charge les annonces à afficher dans le tableau de bord.
     */
    private void loadAnnonces() {
        AnnonceApi api = RetrofitInstance.getAnnonceApi();
        Call<List<Annonce>> call = api.getAllAnnonces();

        call.enqueue(new Callback<List<Annonce>>() {
            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Annonce> annonces = response.body();
                    if (annonces.isEmpty()) {
                        noAnnoncesMessage.setVisibility(TextView.VISIBLE);
                        recyclerViewAnnonces.setVisibility(RecyclerView.GONE);
                    } else {
                        noAnnoncesMessage.setVisibility(TextView.GONE);
                        recyclerViewAnnonces.setVisibility(RecyclerView.VISIBLE);
                        // Passer true pour indiquer que nous sommes dans le dashboard
                        recyclerViewAnnonces.setAdapter(new AnnonceAdapter(annonces, true));
                    }
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Erreur de chargement des annonces.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}
