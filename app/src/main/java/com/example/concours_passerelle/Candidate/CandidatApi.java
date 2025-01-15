package com.example.concours_passerelle.Candidate;

import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.models.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;
public interface CandidatApi
{
    @POST("/add")
    Call<Candidat> addCandidat(@Body Candidat candidat);

    @GET("{id}")
    Call<Candidat> getCandidatById(@Path("id") Long id);


    @GET("all")
    Call<List<Candidat>> getAllCandidats();

    @GET("search")
    Call<List<Candidat>> searchCandidats(@Query("name") String name);





    @PUT("update/{id}")
    Call<UserResponse> updateCandidat(@Path("id") Long id, @Body Candidat candidat);

    @DELETE("delete/{id}")
    Call<UserResponse> deleteCandidat(@Path("id") Long id);
}



