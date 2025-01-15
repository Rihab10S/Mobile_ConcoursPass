package com.example.concours_passerelle.Candidate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.R;
import com.example.concours_passerelle.Admin.RetrofitInstance;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Form_Candidat extends AppCompatActivity {

    // Déclaration des champs de saisie
    private EditText firstName, lastName, cin, email, phone, birthDate;
    private EditText serieBac, mentionBac;
    private EditText diplomeTitle, annee1Note, annee2Note, etablissementDiplome;
    private EditText country, city, studentCode, filiere;
    private EditText genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_form);

        // Initialisation des champs
        initViews();

        // Définir l'action du bouton
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérification des champs obligatoires
                if (!validateFields()) {
                    return;
                }

                // Création de l'objet Candidat
                Candidat candidate = createCandidate();


                CandidatApi api = RetrofitInstance.getDetailApi();
                Call<Candidat> call = api.addCandidat(candidate);

                call.enqueue(new Callback<Candidat>() {
                    @Override
                    public void onResponse(Call<Candidat> call, Response<Candidat> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Form_Candidat.this, "Candidat créé avec succès", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Form_Candidat.this, "Erreur lors de la création du candidat", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Candidat> call, Throwable t) {
                        Toast.makeText(Form_Candidat.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initViews() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        cin = findViewById(R.id.cin);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        birthDate = findViewById(R.id.birthDate);
        serieBac = findViewById(R.id.serieBac);
        mentionBac = findViewById(R.id.mentionBac);
        diplomeTitle = findViewById(R.id.diplomeTitle);
        annee1Note = findViewById(R.id.annee1Note);
        annee2Note = findViewById(R.id.annee2Note);
        etablissementDiplome = findViewById(R.id.etablissementDiplome);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        studentCode = findViewById(R.id.studentCode);
        filiere = findViewById(R.id.filiere);
        genre = findViewById(R.id.genre);
    }

    private boolean validateFields() {
        if (firstName.getText().toString().trim().isEmpty() || lastName.getText().toString().trim().isEmpty()) {
            Toast.makeText(Form_Candidat.this, "Nom et prénom sont obligatoires", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Candidat createCandidate() {
        Candidat candidate = new Candidat();
        candidate.setNom(lastName.getText().toString().trim());
        candidate.setPrenom(firstName.getText().toString().trim());
        candidate.setCin(cin.getText().toString().trim());
        candidate.setEmail(email.getText().toString().trim());
        candidate.setTel(phone.getText().toString().trim());
        candidate.setDateNaissance(birthDate.getText().toString().trim());

        // Autres champs
        candidate.setSerieBac(serieBac.getText().toString().trim());
        candidate.setMentionBac(mentionBac.getText().toString().trim());
        candidate.setTitreDiplome(diplomeTitle.getText().toString().trim());
        candidate.setNotePremiereAnnee(parseFloat(annee1Note));
        candidate.setNoteDeuxiemeAnnee(parseFloat(annee2Note));
        candidate.setEtablissement(etablissementDiplome.getText().toString().trim());
        candidate.setPays(country.getText().toString().trim());
        candidate.setVille(city.getText().toString().trim());
        candidate.setCodeEtudiant(studentCode.getText().toString().trim());
        candidate.setFiliereChoisi(filiere.getText().toString().trim());
        candidate.setGenre(genre.getText().toString().trim());

        return candidate;
    }

    private float parseFloat(EditText editText) {
        try {
            String text = editText.getText().toString().trim();
            return text.isEmpty() ? 0 : Float.parseFloat(text);
        } catch (NumberFormatException e) {
            return 0;  // Valeur par défaut en cas d'erreur de format
        }
    }
}
