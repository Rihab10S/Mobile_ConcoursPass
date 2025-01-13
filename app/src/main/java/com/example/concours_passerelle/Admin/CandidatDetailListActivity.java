package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.Candidate.CandidatApi;
import com.example.concours_passerelle.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidatDetailListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CandidatDetailAdapter adapter;
    private List<Candidat> candidats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtenir l'ID du candidat depuis l'Intent
        Intent intent = getIntent();
        long candidatId = intent.getLongExtra("candidat_id", -1);

        if (candidatId != -1) {
            // Initialiser Retrofit avec le client OkHttp configuré avec le token
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8082/PasserelleApi/candidate/")  // Base URL pour l'API
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(RetrofitInstance.getClient())  // Utilisation de votre client OkHttp avec le token
                    .build();
            CandidatApi apiService = retrofit.create(CandidatApi.class);

            // Initialisation de la liste de candidats
            candidats = new ArrayList<>();

            // Faire la requête à l'API pour récupérer le candidat par ID
            apiService.getCandidatById(candidatId).enqueue(new Callback<Candidat>() {
                @Override
                public void onResponse(Call<Candidat> call, Response<Candidat> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Candidat apiResponse = response.body();

                        // Créer un candidat à partir des données de l'API
                        Candidat candidat = new Candidat();
                        candidat.setIdCandidat(apiResponse.getIdCandidat());
                        candidat.setNom(apiResponse.getNom());
                        candidat.setPrenom(apiResponse.getPrenom());
                        candidat.setCin(apiResponse.getCin());
                        candidat.setTel(apiResponse.getTel());
                        candidat.setCodeEtudiant(apiResponse.getCodeEtudiant());
                        candidat.setFiliereChoisi(apiResponse.getFiliereChoisi());
                        candidat.setSerieBac(apiResponse.getSerieBac());
                        candidat.setMentionBac(apiResponse.getMentionBac());
                        candidat.setReleveBac(apiResponse.getReleveBac());
                        candidat.setTitreDiplome(apiResponse.getTitreDiplome());
                        candidat.setEtablissement(apiResponse.getEtablissement());
                        candidat.setNotePremiereAnnee(apiResponse.getNotePremiereAnnee());
                        candidat.setNoteDeuxiemeAnnee(apiResponse.getNoteDeuxiemeAnnee());
                        candidat.setEmail(apiResponse.getEmail());
                        candidat.setDateNaissance(apiResponse.getDateNaissance());

                        // Ajouter le candidat à la liste
                        candidats.add(candidat);

                        // Mettre à jour l'adaptateur
                        adapter = new CandidatDetailAdapter(candidats);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(CandidatDetailListActivity.this, "Candidat non trouvé", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Candidat> call, Throwable t) {
                    Toast.makeText(CandidatDetailListActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "ID Candidat invalide", Toast.LENGTH_SHORT).show();
        }
    }
}
