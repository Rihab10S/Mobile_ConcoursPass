package com.example.concours_passerelle.Admin;

import com.example.concours_passerelle.Annonce;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface AnnonceApi {

    @GET("/api/annonces") // Assurez-vous que l'URI correspond au backend
    Call<List<Annonce>> getAllAnnonces();

    @GET("/api/annonces/{id}")
    Call<Annonce> getAnnonceById(@Path("id") Long id);

    @POST("/api/annonces")
    Call<Annonce> createAnnonce(@Body Annonce annonce);

    @PUT("/api/annonces/{id}")
    Call<Annonce> updateAnnonce(@Path("id") Long id, @Body Annonce annonce);


    @DELETE("/api/annonces/{id}")
    Call<Void> deleteAnnonce(@Path("id") Long id);
}
