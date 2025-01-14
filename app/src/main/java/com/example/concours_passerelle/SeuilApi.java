package com.example.concours_passerelle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

// Interface API pour gérer les seuils
public interface SeuilApi {

    // Récupérer le seuil actuel
    @GET("/Passerelle/seuil_orale")
    Call<SeuilOralEntity> getSeuil();

    // Mettre à jour le seuil
    @PUT("/Passerelle/seuil_orale")
    Call<SeuilOralEntity> updateSeuil(@Body SeuilOralEntity seuil);
}
