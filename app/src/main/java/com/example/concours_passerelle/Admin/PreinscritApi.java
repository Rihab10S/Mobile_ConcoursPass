package com.example.concours_passerelle.Admin;

import com.example.concours_passerelle.Candidat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PreinscritApi {
    @GET("/inscrits")
    Call<List<Candidat>> getCandidats();

    @GET("/inscrits/filiere/{filiere}")
    Call<List<Candidat>> getInscritByFiliere(@Path("filiere") String filiere);

}



