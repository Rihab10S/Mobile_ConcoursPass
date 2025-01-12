package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.Note;
import com.example.concours_passerelle.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNoteActivity extends AppCompatActivity {

    private EditText nomEditText, concoursEditText, filiereEditText, CINEditText, noteEditText, StatutOralEditText;
    private Button saveButton;
    private Long noteId;
    private String oldNom, oldConcours, oldFiliere, oldCin,oldResultatOral;
    private double oldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Initialisation des vues
        nomEditText = findViewById(R.id.edit_nom);
        concoursEditText = findViewById(R.id.edit_concours);
        filiereEditText = findViewById(R.id.edit_filiere);
        CINEditText = findViewById(R.id.edit_CIN);
        noteEditText = findViewById(R.id.edit_note);
        StatutOralEditText = findViewById(R.id.edit_Statut_oral);

        saveButton = findViewById(R.id.save_button);

        // Récupérer les données passées par l'intent
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1L);
        oldNom = intent.getStringExtra("noteNom");
        oldConcours = intent.getStringExtra("noteConcours");
        oldFiliere = intent.getStringExtra("noteFiliere");
        oldCin = intent.getStringExtra("noteCIN");
        oldResultatOral = getIntent().getStringExtra("noteStatutOral");
        oldNote = intent.getDoubleExtra("noteValue", 0);

        // Afficher les anciennes données dans les champs
        nomEditText.setText(oldNom);
        concoursEditText.setText(oldConcours);
        filiereEditText.setText(oldFiliere);
        CINEditText.setText(oldCin);
        noteEditText.setText(String.valueOf(oldNote));
        StatutOralEditText.setText(String.valueOf(oldResultatOral));

        // Bouton de sauvegarde
        saveButton.setOnClickListener(v -> {
            // Récupérer les nouvelles données
            String newNom = nomEditText.getText().toString().trim();
            String newConcours = concoursEditText.getText().toString().trim();
            String newFiliere = filiereEditText.getText().toString().trim();
            String newCin = CINEditText.getText().toString().trim();
            String noteString = noteEditText.getText().toString().trim();
            String ResOralString =   StatutOralEditText.getText().toString().trim();

            // Validation des champs
            if (newNom.isEmpty() || newConcours.isEmpty() || newFiliere.isEmpty() || newCin.isEmpty() || noteString.isEmpty()) {
                Toast.makeText(EditNoteActivity.this, "Tous les champs doivent être remplis.", Toast.LENGTH_SHORT).show();
                return;
            }

            double newNote;
            try {
                newNote = Double.parseDouble(noteString);
            } catch (NumberFormatException e) {
                Toast.makeText(EditNoteActivity.this, "Veuillez entrer une valeur numérique pour la note.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Si les nouvelles données sont identiques aux anciennes, on ne fait pas la mise à jour
            if (newNom.equals(oldNom) && newConcours.equals(oldConcours) && newFiliere.equals(oldFiliere) && newCin.equals(oldCin) && newNote == oldNote) {
                Toast.makeText(EditNoteActivity.this, "Aucune modification détectée.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer un objet Note mis à jour
            Note updatedNote = new Note(newNom, newConcours, newNote, newFiliere, newCin);

            // Appeler l'API pour mettre à jour la note
            NoteApi api = RetrofitInstance.getApi();
            Call<Void> call = api.updateItem(noteId, updatedNote);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditNoteActivity.this, "Note mise à jour avec succès", Toast.LENGTH_SHORT).show();

                        // Rediriger vers la page des notes après la mise à jour
                        Intent intent = new Intent(EditNoteActivity.this, GestionNotesAdminActivity.class); // Remplacez 'NotesActivity' par l'activité qui affiche les notes
                        startActivity(intent);
                        finish();  // Fermer l'activité actuelle (d'édition)
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
