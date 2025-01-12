package com.example.concours_passerelle.Candidate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.concours_passerelle.api.ApiClient;
import com.example.concours_passerelle.api.UserApi;
import com.example.concours_passerelle.models.UserRequest;
import com.example.concours_passerelle.models.UserResponse;
import com.example.concours_passerelle.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfileCandidateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile); // Ensure this matches your XML file name

        // Initialize UI components
        EditText etLastName = findViewById(R.id.etLastName);
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnContinue = findViewById(R.id.btnContinue);

        // Retrofit API client instance
        UserApi userApi = ApiClient.getInstance().create(UserApi.class);

        btnContinue.setOnClickListener(v -> {
            // Retrieve user input
            String lastName = etLastName.getText().toString().trim();
            String firstName = etFirstName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate inputs
            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(CreateProfileCandidateActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare request object
            UserRequest userRequest = new UserRequest(firstName, lastName, email, password);

            // Make API call
            userApi.createUser(userRequest).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreateProfileCandidateActivity.this, "Profil créé avec succès!", Toast.LENGTH_SHORT).show();

                        // Navigate to email verification screen with necessary details
                        Intent intent = new Intent(CreateProfileCandidateActivity.this, EmailVerificationActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Toast.makeText(CreateProfileCandidateActivity.this, "Échec de la création du profil: " + errorBody, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(CreateProfileCandidateActivity.this, "Échec de la création du profil", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(CreateProfileCandidateActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
