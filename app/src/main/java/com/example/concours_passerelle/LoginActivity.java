package com.example.concours_passerelle;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.Admin.AdminDashboardActivity;
import com.example.concours_passerelle.Candidate.CandidateDashboardActivity;
import com.example.concours_passerelle.api.ApiClient;
import com.example.concours_passerelle.api.UserApi;
import com.example.concours_passerelle.models.UserLoginRequest;
import com.example.concours_passerelle.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private EditText passwordField;
    private ImageView eyeIcon;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation de Retrofit
        userApi = ApiClient.getInstance().create(UserApi.class);

        // Récupération des champs et boutons après setContentView
        EditText useremailField = findViewById(R.id.etEmailLogin);
        passwordField = findViewById(R.id.etPasswordLogin);
        Button loginButton = findViewById(R.id.btnLoginNow);
        eyeIcon = findViewById(R.id.eyeIcon);

        // Gérer la visibilité du mot de passe avec l'icône de l'œil
        eyeIcon.setOnClickListener(v -> togglePasswordVisibility());

        // Ajoutez un écouteur pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String useremail = useremailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (useremail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs !", Toast.LENGTH_SHORT).show();
                return;
            }

            // Appeler le back-end pour authentifier l'utilisateur
            authenticateUser(useremail, password);
        });
    }

    // Méthode pour alterner la visibilité du mot de passe
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eyeIcon.setImageResource(R.drawable.ic_eye_off); // Changer l'icône de l'œil (icône "off")
        } else {
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eyeIcon.setImageResource(R.drawable.ic_eye_on); // Changer l'icône de l'œil (icône "on")
        }
        passwordField.setSelection(passwordField.getText().length()); // Repositionner le curseur
        isPasswordVisible = !isPasswordVisible;
    }

    // Méthode pour authentifier l'utilisateur via l'API
    private void authenticateUser(String email, String password) {
        UserLoginRequest loginRequest = new UserLoginRequest(email, password);

        Call<UserResponse> call = userApi.login(loginRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse user = response.body();
                    String userId = user.getUserId();
                    Toast.makeText(LoginActivity.this, "Bienvenue, " , Toast.LENGTH_SHORT).show();

                    // Redirection vers le dashboard basé sur le rôle de l'utilisateur
                    navigateToDashboard(email, userId);
                } else {
                    Toast.makeText(LoginActivity.this, "Identifiants incorrects!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Méthode pour naviguer vers le bon dashboard basé sur le rôle de l'utilisateur
    private void navigateToDashboard(String email, String userId) {
        Intent intent;
        if (email.equals("Admin2025@gmail.com")) {
            intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
        } else if (email.equals("coordinateur@gmail.com")) {
            intent = new Intent(LoginActivity.this, CoordinateurActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, CandidateDashboardActivity.class);
        }

        // Passer l'ID utilisateur à l'Intent
        intent.putExtra("user_id", userId);
        startActivity(intent);
        finish();
    }
}
