package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.Note;
import com.example.concours_passerelle.NoteAdapter;
import com.example.concours_passerelle.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class GestionNotesAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private Spinner spinner;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesadmin);

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.filter_spinner);
        emptyView = findViewById(R.id.empty_view);

        // Configuration du RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Ensures vertical scrolling
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Initialisation de l'adaptateur
        noteAdapter = new NoteAdapter(new ArrayList<>());
        recyclerView.setAdapter(noteAdapter);

        // Configuration du Spinner pour filtrer par filière
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String filiere = parentView.getItemAtPosition(position).toString();
                // Appeler la fonction pour récupérer les notes selon la filière choisie
                fetchNotes(filiere);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nothing to do here
            }
        });

        // Charger les notes sans filtre au départ
        fetchNotes("");
    }

    private void fetchNotes(String filiere) {
        NoteApi api = RetrofitInstance.getApi();

        // Appeler l'API en fonction du filtre (filière vide pour toutes les notes)
        Call<List<Note>> call = filiere.equals("Toutes les Filières") || filiere.isEmpty()
                ? api.getNotes()  // Appel pour toutes les notes
                : api.getNotesByFiliere(filiere);  // Appel avec filtre de la filière

        // Effectuer la requête API
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
                        noteAdapter.updateNotes(notes);
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

    public void openAddNoteActivity(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void showError(String message) {
        Log.e("GestionNotesAdminActivity", message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
