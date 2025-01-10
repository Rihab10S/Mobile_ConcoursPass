package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.Admin.NoteApi;
import com.example.concours_passerelle.Admin.RetrofitInstance;
import com.example.concours_passerelle.Note;
import com.example.concours_passerelle.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNoteActivity extends AppCompatActivity {

    private EditText nomEditText, concoursEditText, statutEditText, filiereEditText, emailEditText, noteEditText;
    private Button saveButton;
    private Long noteId;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        nomEditText = findViewById(R.id.edit_nom);
        concoursEditText = findViewById(R.id.edit_concours);
        statutEditText = findViewById(R.id.edit_statut);
        filiereEditText = findViewById(R.id.edit_filiere);
        emailEditText = findViewById(R.id.edit_email);
        noteEditText = findViewById(R.id.edit_note);
        saveButton = findViewById(R.id.save_button);

        // Récupérer les données passées par l'intent
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1L);
        nomEditText.setText(intent.getStringExtra("noteNom"));
        concoursEditText.setText(intent.getStringExtra("noteConcours"));
        statutEditText.setText(intent.getStringExtra("noteStatut"));
        filiereEditText.setText(intent.getStringExtra("noteFiliere"));
        emailEditText.setText(intent.getStringExtra("noteEmail"));
        noteEditText.setText(String.valueOf(intent.getDoubleExtra("noteValue", 0)));

        saveButton.setOnClickListener(v -> {
            // Récupérer les nouvelles données
            String newNom = nomEditText.getText().toString();
            String newConcours = concoursEditText.getText().toString();
            String newStatut = statutEditText.getText().toString();
            String newFiliere = filiereEditText.getText().toString();
            String newEmail = emailEditText.getText().toString();
            double newNote = Double.parseDouble(noteEditText.getText().toString());

            // Créer un objet Note mis à jour
            Note updatedNote = new Note(
                    newNom,
                    newConcours,
                    newStatut,
                    newNote,
                    newFiliere,
                    newEmail
            );

            // Appeler l'API pour mettre à jour la note
            NoteApi api = RetrofitInstance.getApi();
            Call<Void> call = api.updateItem(noteId, updatedNote); // Le noteId est passé dans l'URL, pas dans l'objet

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditNoteActivity.this, "Note mise à jour avec succès", Toast.LENGTH_SHORT).show();
                        finish();  // Fermer l'activité après la mise à jour
                    } else {
                        Toast.makeText(EditNoteActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(EditNoteActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
