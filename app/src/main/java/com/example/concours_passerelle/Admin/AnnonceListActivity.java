package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.concours_passerelle.R;
import com.example.concours_passerelle.Annonce;
import com.example.concours_passerelle.Admin.AnnonceAdapter;
import com.example.concours_passerelle.Admin.RetrofitInstance;
import com.example.concours_passerelle.Admin.AnnonceApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnonceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnnonceAdapter adapter;
    private Button addAnnonceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        // Initialisation du RecyclerView
        recyclerView = findViewById(R.id.recycler_view_annonces);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation du bouton pour ajouter une annonce
        addAnnonceButton = findViewById(R.id.add_annonce_button);

        // Ajout du clic sur le bouton pour ouvrir l'activité de création d'annonce
        addAnnonceButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnnonceListActivity.this, CreateAnnonceActivity.class);
            startActivity(intent);  // Lancer l'activité de création d'annonce
        });

        // Charger les annonces
        loadAnnonces();
    }

    /**
     * Charge la liste des annonces depuis l'API.
     */
    private void loadAnnonces() {
        // Création d'une instance de l'API
        AnnonceApi api = RetrofitInstance.getAnnonceApi();

        // Appel de l'API pour récupérer toutes les annonces
        Call<List<Annonce>> call = api.getAllAnnonces();
        call.enqueue(new Callback<List<Annonce>>() {
            @Override
            public void onResponse(Call<List<Annonce>> call, Response<List<Annonce>> response) {
                if (response.isSuccessful()) {
                    List<Annonce> annonces = response.body();
                    if (annonces != null && !annonces.isEmpty()) {
                        // Mise à jour de l'adaptateur avec les annonces récupérées
                        adapter = new AnnonceAdapter(annonces);
                        recyclerView.setAdapter(adapter);
                        Toast.makeText(AnnonceListActivity.this, "Annonces chargées avec succès.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Si la liste est vide
                        Toast.makeText(AnnonceListActivity.this, "Aucune annonce disponible.", Toast.LENGTH_SHORT).show();
                        if (annonces == null) {
                            Toast.makeText(AnnonceListActivity.this, "Response body est null.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AnnonceListActivity.this, "Response body est vide.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Affiche les détails de l'erreur HTTP
                    Toast.makeText(AnnonceListActivity.this, "Erreur HTTP : " + response.code() + " - " + response.message(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<List<Annonce>> call, Throwable t) {
                // Log the error stack trace for better debugging
                Log.e("AnnonceListActivity", "Erreur réseau : ", t);

                // Show the error message in the Toast for the user
                String errorMessage = "Erreur réseau : " + (t.getMessage() != null ? t.getMessage() : "Unknown error");

                // Display detailed error message in a Toast
                Toast.makeText(AnnonceListActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

        });
    }
}
