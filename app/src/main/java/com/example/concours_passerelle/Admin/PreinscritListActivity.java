package com.example.concours_passerelle.Admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.colors.ColorConstants;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.concours_passerelle.R;
import com.example.concours_passerelle.Candidat;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import com.itextpdf.layout.properties.TextAlignment;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.properties.UnitValue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreinscritListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PreinscritAdapter adapter;
    private Spinner spinner;
    private EditText seuilEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_preinscrits);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        seuilEditText = findViewById(R.id.filter_edit_text);
        spinner = findViewById(R.id.filter_spinner);
        Button telechargerBtn = findViewById(R.id.btn_telecharger_liste_pre);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Spinner event listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filiere = parent.getItemAtPosition(position).toString();
                loadPreinscrits(filiere);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Ajout du TextWatcher pour le seuil
        seuilEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applySeuilToAdapter(); // Applique le seuil en temps réel
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // Button click listener for PDF generation
        telechargerBtn.setOnClickListener(view -> {
            if (adapter != null) {
                List<Candidat> candidatsAuDessusDuSeuil = adapter.getCandidatsAuDessusDuSeuil();
                if (!candidatsAuDessusDuSeuil.isEmpty()) {
                    generatePDF(candidatsAuDessusDuSeuil);
                } else {
                    Toast.makeText(this, "Aucun candidat au-dessus du seuil.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load initial data
        loadPreinscrits("");
    }

    private void generatePDF(List<Candidat> candidatsAuDessusDuSeuil) {
        try {
            // Chemin du fichier PDF
            String filePath = getExternalFilesDir(null) + "/CandidatsPreseles.pdf";

            // Création d'un PDF
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Charger l'image du logo
            InputStream logoStream = getResources().openRawResource(R.drawable.logo_ensa);
            byte[] logoBytes = new byte[logoStream.available()];
            logoStream.read(logoBytes);
            logoStream.close();

            ImageData imageData = ImageDataFactory.create(logoBytes);
            Image logo = new Image(imageData);

            // Ajuster la taille et l'alignement du logo
            logo.setAutoScale(false); // Désactiver l'auto-dimensionnement
            logo.setWidth(120); // Largeur de 45
            logo.setHeight(120); // Hauteur de 45 (ou ajustez selon vos besoins)
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER); // Centrer le logo


            // Ajouter le logo au document
            document.add(logo);


            // Utiliser une police standard pour la clarté
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Ajouter un titre principal avec style
            Paragraph title = new Paragraph("Liste de Pré-sélectionnés pour les Concours Passerelles")
                    .setFont(boldFont)
                    .setFontSize(24)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
                    .setMarginBottom(10);
            document.add(title);

            // Ajouter une sous-titre
            Paragraph subtitle = new Paragraph("Année Universitaire 2025-2026")
                    .setFont(font)
                    .setFontSize(20)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY)
                    .setMarginBottom(20);
            document.add(subtitle);

            // Grouper les candidats par filière
            Map<String, List<Candidat>> candidatsParFiliere = candidatsAuDessusDuSeuil.stream()
                    .collect(Collectors.groupingBy(Candidat::getFiliereChoisi));

            for (Map.Entry<String, List<Candidat>> entry : candidatsParFiliere.entrySet()) {
                // Ajouter un sous-titre pour chaque filière
                Paragraph filiereTitle = new Paragraph(entry.getKey())
                        .setFont(boldFont)
                        .setFontSize(18)
                        .setFontColor(com.itextpdf.kernel.colors.ColorConstants.DARK_GRAY)
                        .setMarginTop(15)
                        .setMarginBottom(5);
                document.add(filiereTitle);

                // Ajouter un tableau pour les candidats de la filière
                float[] columnWidths = {4, 4, 4, 4}; // Largeurs relatives des colonnes
                Table table = new Table(columnWidths);
                table.setWidth(UnitValue.createPercentValue(95));

                // Ajouter des marges et des espacements autour du tableau
                table.setMarginTop(15); // Espace avant le tableau
                table.setMarginBottom(15); // Espace après le tableau

                // Ajouter des en-têtes au tableau avec style
                table.addHeaderCell(new Cell()
                        .add(new Paragraph("Nom Complet").setFont(boldFont).setFontSize(18)) // Agrandir la taille de la police des en-têtes
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                        .setPadding(10));
                table.addHeaderCell(new Cell()
                        .add(new Paragraph("CIN").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                        .setPadding(10));
                table.addHeaderCell(new Cell()
                        .add(new Paragraph("Code Étudiant").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                        .setPadding(10));
                table.addHeaderCell(new Cell()
                        .add(new Paragraph("Filière").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                        .setPadding(10));

                // Ajouter les données des candidats avec des cellules plus grandes et une police agrandie
                for (Candidat candidat : entry.getValue()) {
                    table.addCell(new Cell()
                            .add(new Paragraph(candidat.getNom()).setFont(font).setFontSize(20)) // Police plus grande pour les valeurs
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell()
                            .add(new Paragraph(candidat.getCin()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell()
                            .add(new Paragraph(candidat.getCodeEtudiant()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell()
                            .add(new Paragraph(candidat.getFiliereChoisi()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                }

                // Ajouter le tableau au document
                document.add(table);
            }

            // Fermer le document
            document.close();

            // Message de succès
            Toast.makeText(this, "PDF généré : " + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPreinscrits(String filiere) {
        PreinscritApi api = RetrofitInstance.getPreinscritApi();
        Call<List<Candidat>> call = filiere.isEmpty() || "Toutes les Filières".equalsIgnoreCase(filiere)
                ? api.getCandidats()
                : api.getInscritByFiliere(filiere);

        call.enqueue(new Callback<List<Candidat>>() {
            @Override
            public void onResponse(Call<List<Candidat>> call, Response<List<Candidat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Candidat> candidats = response.body();
                    adapter = new PreinscritAdapter(candidats);
                    recyclerView.setAdapter(adapter);

                    // Appliquer le seuil après la création de l'adaptateur
                    applySeuilToAdapter();
                } else {
                    Toast.makeText(PreinscritListActivity.this, "Erreur: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Candidat>> call, Throwable t) {
                Log.e("PreinscritListActivity", "Erreur: ", t);
                Toast.makeText(PreinscritListActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applySeuilToAdapter() {
        // Récupérer la valeur du seuil
        String seuilString = seuilEditText.getText().toString();

        if (!seuilString.isEmpty()) {
            try {
                double seuil = Double.parseDouble(seuilString); // Convertir la valeur en double
                adapter.setSeuil(seuil); // Passer la valeur au PreinscritAdapter
            } catch (NumberFormatException e) {
                // Gérer les erreurs si la conversion échoue
                Toast.makeText(this, "Veuillez entrer un seuil valide", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
