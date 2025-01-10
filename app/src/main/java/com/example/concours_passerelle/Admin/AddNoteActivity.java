package com.example.concours_passerelle.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.concours_passerelle.Note;
import com.example.concours_passerelle.R;
import retrofit2.Response;
import okhttp3.Interceptor;

import okhttp3.OkHttpClient;

import java.io.IOException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editConcours;
    private EditText editStatut;
    private EditText editNote;
    private EditText editFiliere;
    private EditText editEmail;
    private Button btnSubmit;

    // Token d'authentification
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZTFAZ21haWwuY29tIiwiZXhwIjoxNzM2Njk2NTg0fQ.e_CqkGZslZcC_dxmRefTzAxn30LilimTsE-gOgIfBnt7HyXk5N_coBXlO4yszUrwzKiI05CG_SIQm1Mh_kv2Bw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Initialisation des vues
        editName = findViewById(R.id.edit_name);
        editConcours = findViewById(R.id.edit_concours);
        editStatut = findViewById(R.id.edit_statut);
        editNote = findViewById(R.id.edit_note);
        editFiliere = findViewById(R.id.edit_filiere);
        editEmail = findViewById(R.id.edit_email);
        btnSubmit = findViewById(R.id.btn_submit);

        // Listener pour le bouton "Ajouter"
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNote();
            }
        });}

    // Fonction pour soumettre une note
    private void submitNote() {
        String nom = editName.getText().toString();
        String concours = editConcours.getText().toString();
        String statut = editStatut.getText().toString();
        String noteString = editNote.getText().toString();
        String filiere = editFiliere.getText().toString();
        String email = editEmail.getText().toString();

        // Vérification des champs
        if (nom.isEmpty() || concours.isEmpty() || statut.isEmpty() || noteString.isEmpty() || filiere.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Conversion de la note en Double
        Double note = null;
        try {
            note = Double.parseDouble(noteString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer une note valide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création de l'objet Note
        Note newNote = new Note(nom, concours, statut, note, filiere, email);

        // Appel à l'API pour enregistrer la note
        saveNoteToApi(newNote);
    }

    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    // Construction de la requête avec le header Authorization
                    okhttp3.Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    // Exécution de la requête avec l'ajout du header
                    return chain.proceed(request);
                }
            })
            .build();

    // Fonction pour sauvegarder la note via l'API
    private void saveNoteToApi(Note note) {
        // Configuration de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/api/notes/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NoteApi apiService = retrofit.create(NoteApi.class);
        Call<Void> call = apiService.addNote(note);

        // Exécution de la requête API
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Note ajoutée avec succès
                    Toast.makeText(AddNoteActivity.this, "Note ajoutée avec succès", Toast.LENGTH_SHORT).show();
                    finish(); // Ferme l'activité et revient à la précédente
                } else {
                    // Erreur lors de l'ajout de la note
                    Toast.makeText(AddNoteActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Échec de la requête
                Toast.makeText(AddNoteActivity.this, "Échec de la requête", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
