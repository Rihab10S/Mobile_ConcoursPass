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

public class LoginActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;  // Variable pour garder l'état de visibilité du mot de passe
    private EditText passwordField;
    private ImageView eyeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupération des champs et boutons après setContentView
        EditText useremailField = findViewById(R.id.etEmailLogin);
        passwordField = findViewById(R.id.etPasswordLogin);
        Button loginButton = findViewById(R.id.btnLoginNow);
        eyeIcon = findViewById(R.id.eyeIcon);

        // Gérer la visibilité du mot de passe avec l'icône de l'œil
        eyeIcon.setOnClickListener(v -> togglePasswordVisibility());

        // Ajoutez un écouteur pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String useremail = useremailField.getText().toString();
            String password = passwordField.getText().toString();

            // Vérification des identifiants et rôle
            if (useremail.equals("admin@gmail.com") && password.equals("admin1234")) {
                // Rediriger vers AdminDashboardActivity si l'utilisateur est un admin
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            } else if (useremail.equals("candidat@gmail.com") && password.equals("1234")) {
                // Rediriger vers CandidatDashboardActivity si l'utilisateur est un candidat
                Intent intent = new Intent(LoginActivity.this, CandidateDashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Afficher un message d'erreur si les identifiants sont incorrects
                Toast.makeText(LoginActivity.this, "Identifiants incorrects !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour alterner la visibilité du mot de passe
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Si le mot de passe est visible, on le masque
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eyeIcon.setImageResource(R.drawable.ic_eye_off);  // Changer l'icône de l'œil (icône "off")
        } else {
            // Si le mot de passe est masqué, on l'affiche
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eyeIcon.setImageResource(R.drawable.ic_eye_on);  // Changer l'icône de l'œil (icône "on")
        }
        // Pour remettre le curseur à la fin du texte, on le réinitialise
        passwordField.setSelection(passwordField.getText().length());

        // Inverser l'état de visibilité du mot de passe
        isPasswordVisible = !isPasswordVisible;
    }
}
