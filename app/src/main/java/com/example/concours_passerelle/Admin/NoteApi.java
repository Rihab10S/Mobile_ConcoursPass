package com.example.concours_passerelle.Admin;

import com.example.concours_passerelle.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NoteApi {

    @GET("/api/notes")
    Call<List<Note>> getNotes();

    @GET("/api/notes/filiere/{filiere}")
    Call<List<Note>> getNotesByFiliere(@Path("filiere") String filiere);

    @POST("/api/notes")
    Call<Void> addNote(@Body Note note);

    @DELETE("/api/notes/{id}")
    Call<Void> deleteNote(@Path("id") int noteId);

    @PUT("/api/notes/{id}")
    Call<Void> updateItem(@Path("id") long id, @Body Note note);




}
