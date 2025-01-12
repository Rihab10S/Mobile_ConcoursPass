package com.example.concours_passerelle.api;

import com.example.concours_passerelle.models.UserLoginRequest;
import com.example.concours_passerelle.models.UserRequest;
import com.example.concours_passerelle.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;

public interface UserApi {

    @POST("users/login")
    Call<UserResponse> loginUser(@Body UserLoginRequest userLoginRequest);

    @POST("users")
    Call<UserResponse> createUser(@Body UserRequest userRequest);

    @PUT("users/{id}")
    Call<UserResponse> updateUser(@Path("id") String id, @Body UserRequest userRequest);

    @GET("users/{id}")
    Call<UserResponse> getUser(@Path("id") String id);

    @GET("users")
    Call<List<UserResponse>> getAllUsers(@Query("page") int page, @Query("limit") int limit);
}
