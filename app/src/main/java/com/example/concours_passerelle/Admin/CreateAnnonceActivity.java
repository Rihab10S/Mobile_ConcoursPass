package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.Annonce;
import com.example.concours_passerelle.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAnnonceActivity extends AppCompatActivity {

    private EditText titleEditText, visibilityEditText, statusEditText, typeEditText, contentEditText;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_annonce);

        // Liaison des vues
        titleEditText = findViewById(R.id.annonce_title);
        visibilityEditText = findViewById(R.id.annonce_visibility);
        statusEditText = findViewById(R.id.annonce_status);
        typeEditText = findViewById(R.id.annonce_type);
        contentEditText = findViewById(R.id.annonce_content);
        createButton = findViewById(R.id.create_annonce_button);

        createButton.setOnClickListener(v -> createAnnonce());
    }

    private void createAnnonce() {
        // Récupération des données saisies
        String title = titleEditText.getText().toString().trim();
        String visibility = visibilityEditText.getText().toString().trim();
        String status = statusEditText.getText().toString().trim();
        String type = typeEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        // Validation des champs
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(visibility) || TextUtils.isEmpty(status) || TextUtils.isEmpty(type) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création de l'objet Annonce
        Annonce annonce = new Annonce();
        annonce.setTitle(title);
        annonce.setVisibility(visibility);
        annonce.setStatus(status);
        annonce.setType(type);
        annonce.setContent(content);

        // Envoi de l'annonce au serveur
        AnnonceApi api = RetrofitInstance.getAnnonceApi();
        Call<Annonce> call = api.createAnnonce(annonce);
        call.enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateAnnonceActivity.this, "Annonce créée avec succès", Toast.LENGTH_SHORT).show();
                    // Rediriger vers la liste des annonces
                    Intent intent = new Intent(CreateAnnonceActivity.this, AnnonceListActivity.class);
                    startActivity(intent);
                    finish(); // Fermer l'activité actuelle
                } else {
                    Toast.makeText(CreateAnnonceActivity.this, "Erreur lors de la création de l'annonce", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Annonce> call, Throwable t) {
                Toast.makeText(CreateAnnonceActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
