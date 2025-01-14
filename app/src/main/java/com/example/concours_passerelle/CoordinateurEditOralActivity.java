package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.Admin.AnnonceListActivity;
import com.example.concours_passerelle.Admin.EditAnnonceActivity;
import com.example.concours_passerelle.Admin.NoteApi;
import com.example.concours_passerelle.Admin.RetrofitInstance;
import com.example.concours_passerelle.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoordinateurEditOralActivity extends AppCompatActivity {

    private TextView nomTextView;
    private TextView concoursTextView;
    private EditText statutTextView;  // EditText pour rendre le champ modifiable
    private TextView noteTextView;
    private TextView filiereTextView;
    private TextView CINTextView;
    private Button saveButton;  // Bouton Ajouter
    private Long noteId;  // ID de la note à mettre à jour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_oral);

        // Initialisation des vues pour afficher les données
        nomTextView = findViewById(R.id.edit_nom);
        concoursTextView = findViewById(R.id.edit_concours);
        statutTextView = findViewById(R.id.edit_Statut_oral);
        noteTextView = findViewById(R.id.edit_note);
        filiereTextView = findViewById(R.id.edit_filiere);
        CINTextView = findViewById(R.id.edit_CIN);
        saveButton = findViewById(R.id.save_button); // Initialisation du bouton

        // Récupérer les données passées par l'Intent
        Intent intent = getIntent();
        noteId = intent.getLongExtra("noteId", -1);  // Récupérer l'ID de la note
        String noteNom = intent.getStringExtra("noteNom");
        String noteConcours = intent.getStringExtra("noteConcours");
        String noteStatutOral = intent.getStringExtra("noteStatutOral");
        String noteFiliere = intent.getStringExtra("noteFiliere");
        String noteCIN = intent.getStringExtra("noteCIN");

        // Vérifier la valeur de "noteValue" récupérée
        double noteValue = intent.getDoubleExtra("noteValue", -1);
        Log.d("CoordinateurEditOralActivity", "noteValue récupérée: " + noteValue);

        // Affecter ces données aux TextViews et EditTexts
        nomTextView.setText(noteNom);
        concoursTextView.setText(noteConcours);
        statutTextView.setText(noteStatutOral);
        noteTextView.setText(String.valueOf(noteValue));
        filiereTextView.setText(noteFiliere);
        CINTextView.setText(noteCIN);

        // Désactiver les champs non modifiables
        nomTextView.setEnabled(false);
        concoursTextView.setEnabled(false);
        filiereTextView.setEnabled(false);
        CINTextView.setEnabled(false);
        noteTextView.setEnabled(false);

        // Gérer l'action du bouton "Ajouter"
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer la nouvelle valeur du statut oral
                String updatedStatutOral = statutTextView.getText().toString();

                if (updatedStatutOral.isEmpty()) {
                    // Si le champ est vide, afficher un message d'erreur
                    Toast.makeText(CoordinateurEditOralActivity.this, "Le statut oral ne peut pas être vide", Toast.LENGTH_SHORT).show();
                } else {
                    // Mettre à jour le TextView de statut oral avec la nouvelle valeur
                    statutTextView.setText(updatedStatutOral);

                    // Log pour vérifier que la mise à jour fonctionne
                    Log.d("CoordinateurEditOralActivity", "Statut oral mis à jour : " + updatedStatutOral);

                    // Appeler l'API pour mettre à jour la note dans la base de données
                    updateNoteInDatabase(updatedStatutOral);
                }
            }
        });
    }

    private void updateNoteInDatabase(String updatedStatutOral) {
        NoteApi api = RetrofitInstance.getApi();
        Note note = new Note(noteId,  // Utilisation de l'ID de la note existante
                nomTextView.getText().toString(),  // Récupérer le nom du TextView
                concoursTextView.getText().toString(),  // Récupérer le concours
                updatedStatutOral,  // Nouvelle valeur du statut oral
                Double.parseDouble(noteTextView.getText().toString()),  // Note
                filiereTextView.getText().toString(),  // Filière
                CINTextView.getText().toString(),  // CIN
                false,  // statuSendAdmin, par défaut
                false);  // statusPublication, par défaut

        // Appel de la méthode PUT de Retrofit pour mettre à jour la note
        Call<Void> call = api.updateItem(noteId, note);  // L'ID de la note est passé ici

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la mise à jour est réussie, on met à jour l'affichage immédiatement
                    Toast.makeText(CoordinateurEditOralActivity.this, "Statut oral mis à jour avec succès", Toast.LENGTH_SHORT).show();

                    // Créer un Intent pour envoyer les données mises à jour à l'activité précédente
                    Intent intent = new Intent(CoordinateurEditOralActivity.this, CoordinateurActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Si l'API retourne une erreur
                    Toast.makeText(CoordinateurEditOralActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si une erreur réseau se produit
                Toast.makeText(CoordinateurEditOralActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}