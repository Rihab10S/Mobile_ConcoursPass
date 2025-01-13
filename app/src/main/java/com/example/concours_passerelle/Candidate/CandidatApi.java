package com.example.concours_passerelle.Candidate;

import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CandidatApi
{
    @GET("{id}")
    Call<Candidat> getCandidatById(@Path("id") Long id);
}
