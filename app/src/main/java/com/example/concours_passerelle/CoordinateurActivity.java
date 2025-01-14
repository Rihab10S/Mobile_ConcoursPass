package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.Admin.NoteApi;
import com.example.concours_passerelle.Admin.RetrofitInstance;
import com.example.concours_passerelle.SeuilApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoordinateurActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CoordinateurAdapter coordinateurAdapter;
    private Spinner spinner;
    private View emptyView;
    private double seuil = -1;  // Variable pour stocker le seuil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinateur);

        // Initialisation des vues
        initViews();

        // Configuration du RecyclerView
        setupRecyclerView();

        // Charger le seuil depuis la base de données
        fetchSeuil();

        // Initialiser le listener du Spinner
        setupSpinnerListener();
    }
    public void onLogoutClick(View view) {
        // Crée un Intent pour naviguer vers LoginActivity
        Intent intent = new Intent(CoordinateurActivity.this, LoginActivity.class);

        // Lance l'activité de connexion
        startActivity(intent);

        // Termine l'activité actuelle pour empêcher l'utilisateur de revenir à la page précédente
        finish();
    }



    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewOral);  // Correction du nom de la RecyclerView
        spinner = findViewById(R.id.filter_spinner);
        emptyView = findViewById(R.id.empty_view);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Initialisation de l'adaptateur avec une liste vide au départ
        coordinateurAdapter = new CoordinateurAdapter(new ArrayList<>());
        recyclerView.setAdapter(coordinateurAdapter);
    }

    private void setupSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String filiere = parentView.getItemAtPosition(position).toString();
                fetchNotes(filiere);  // Appel pour récupérer les notes filtrées
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    // Méthode pour récupérer le seuil depuis la base de données
    private void fetchSeuil() {
        SeuilApi api = RetrofitInstance.getSeuilApi();
        Call<SeuilOralEntity> call = api.getSeuil();

        call.enqueue(new Callback<SeuilOralEntity>() {
            @Override
            public void onResponse(Call<SeuilOralEntity> call, Response<SeuilOralEntity> response) {
                if (response.isSuccessful()) {
                    SeuilOralEntity seuilEntity = response.body();
                    if (seuilEntity != null) {
                        seuil = seuilEntity.getSeuil();
                        fetchNotes("");
                    } else {
                        showError("Seuil non trouvé.");
                    }
                } else {
                    showError("Erreur de récupération du seuil.");
                }
            }

            @Override
            public void onFailure(Call<SeuilOralEntity> call, Throwable t) {
                showError("Erreur réseau : " + t.getMessage());
            }
        });
    }


    // Méthode pour récupérer les notes depuis l'API et les afficher
    private void fetchNotes(String filiere) {
        if (seuil == -1) {
            return;  // Si le seuil n'est pas encore récupéré, on ne charge pas les notes
        }

        NoteApi api = RetrofitInstance.getApi();
        Call<List<Note>> call = filiere.isEmpty() || "Toutes les Filières".equals(filiere)
                ? api.getNotes()  // Appel pour toutes les notes
                : api.getNotesByFiliere(filiere);  // Appel pour une filière spécifique

        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful()) {
                    List<Note> notes = response.body();
                    if (notes == null || notes.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        // Filtrer les notes selon le seuil
                        List<Note> filteredNotes = filterNotesBySeuil(notes);
                        coordinateurAdapter.updateNotes(filteredNotes);
                    }
                } else {
                    showError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                showError("Erreur réseau : " + t.getMessage());
            }
        });
    }

    // Méthode pour filtrer les notes au-dessus du seuil
    private List<Note> filterNotesBySeuil(List<Note> notes) {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : notes) {
            if (note.getNote() >= seuil) {
                filteredNotes.add(note);
            }
        }
        return filteredNotes;
    }


    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
