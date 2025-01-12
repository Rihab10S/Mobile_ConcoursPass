package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.concours_passerelle.Annonce;
import com.example.concours_passerelle.R;

public class EditAnnonceActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText visibilityEditText;
    private EditText statusEditText;
    private EditText typeEditText;
    private EditText contentEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_annonce);

        titleEditText = findViewById(R.id.title_edit_text);
        visibilityEditText = findViewById(R.id.visibility_edit_text);
        statusEditText = findViewById(R.id.status_edit_text);
        typeEditText = findViewById(R.id.type_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);
        saveButton = findViewById(R.id.save_button);

        // Get the data from Intent
        Long annonceId = getIntent().getLongExtra("annonceId", -1);
        String title = getIntent().getStringExtra("annonceTitle");
        String visibility = getIntent().getStringExtra("annonceVisibility");
        String status = getIntent().getStringExtra("annonceStatus");
        String type = getIntent().getStringExtra("annonceType");
        String content = getIntent().getStringExtra("annonceContent");

        // Set the data into EditTexts
        titleEditText.setText(title);
        visibilityEditText.setText(visibility);
        statusEditText.setText(status);
        typeEditText.setText(type);
        contentEditText.setText(content);

        // Handle Save Button
        saveButton.setOnClickListener(v -> {
            // Retrieve updated data
            String updatedTitle = titleEditText.getText().toString();
            String updatedVisibility = visibilityEditText.getText().toString();
            String updatedStatus = statusEditText.getText().toString();
            String updatedType = typeEditText.getText().toString();
            String updatedContent = contentEditText.getText().toString();

            // Create an Annonce object with the updated data
            Annonce updatedAnnonce = new Annonce();
            updatedAnnonce.setId(annonceId); // Set the ID
            updatedAnnonce.setTitle(updatedTitle);
            updatedAnnonce.setVisibility(updatedVisibility);
            updatedAnnonce.setStatus(updatedStatus);
            updatedAnnonce.setType(updatedType);
            updatedAnnonce.setContent(updatedContent);

            // API request to update the annonce
            AnnonceApi api = RetrofitInstance.getAnnonceApi();
            api.updateAnnonce(annonceId, updatedAnnonce).enqueue(new Callback<Annonce>() {
                @Override
                public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditAnnonceActivity.this, "Annonce modifiée avec succès", Toast.LENGTH_SHORT).show();
                        // Rediriger vers la liste des annonces
                        Intent intent = new Intent(EditAnnonceActivity.this, AnnonceListActivity.class);
                        startActivity(intent);
                        finish(); // Fermer l'activité actuelle
                    } else {
                        Toast.makeText(EditAnnonceActivity.this, "Échec de la modification de l'annonce", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Annonce> call, Throwable t) {
                    Toast.makeText(EditAnnonceActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
