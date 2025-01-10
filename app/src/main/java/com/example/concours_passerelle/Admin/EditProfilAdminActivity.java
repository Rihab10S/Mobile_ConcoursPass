package com.example.concours_passerelle.Admin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.concours_passerelle.R;

public class EditProfilAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialiser les champs de saisie et le bouton
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etLastName = findViewById(R.id.etLastName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPhone = findViewById(R.id.etPhone);
        Button btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // Gérer l'événement de clic du bouton "Sauvegarder"
        btnSaveProfile.setOnClickListener(v -> {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // Valider le formulaire
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(EditProfilAdminActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditProfilAdminActivity.this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité et revenir à la précédente
        }
    });
    }
}
