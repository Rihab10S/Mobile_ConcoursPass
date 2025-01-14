package com.example.concours_passerelle.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.Candidat;
import com.example.concours_passerelle.Note;
import com.example.concours_passerelle.NoteAdapter;
import com.example.concours_passerelle.R;
import com.example.concours_passerelle.SeuilApi;
import com.example.concours_passerelle.SeuilOralEntity;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

public class GestionNotesAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private Spinner spinner;
    private EditText seuilEditText;
    private View emptyView;
    private Button telechargerBtn;
    private Button telechargerBtnAdmis ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesadmin);

        // Initialisation des vues
        initViews();

        // Configuration du RecyclerView
        setupRecyclerView();

        // Configuration du TextWatcher pour le seuil
        setupSeuilTextWatcher();

        // Configuration du Spinner pour filtrer par filière
        setupSpinnerListener();

        // Button click listener pour générer le PDF
        setupDownloadButton();

        // Button click listener pour générer le PDF
        setupDownloadButtonResultat();


        // Charger les notes sans filtre au démarrage
        fetchNotes("");
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.filter_spinner);
        emptyView = findViewById(R.id.empty_view);
        seuilEditText = findViewById(R.id.filter_edit_text);
        telechargerBtn = findViewById(R.id.btn_telecharger_liste_orale);
        telechargerBtnAdmis = findViewById(R.id.btn_telecharger_liste_finale);
        // Initialisation du bouton pour envoyer le seuil
        Button envoyerSeuilBtn = findViewById(R.id.btn_env_liste_orale);
        envoyerSeuilBtn.setOnClickListener(view -> envoyerSeuil());

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Initialisation de l'adaptateur avec une liste vide au départ
        noteAdapter = new NoteAdapter(new ArrayList<>());
        recyclerView.setAdapter(noteAdapter);
    }

    private void setupSeuilTextWatcher() {
        seuilEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().isEmpty()) {
                    double seuil = Double.parseDouble(charSequence.toString());
                    noteAdapter.setSeuil(seuil);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void setupSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String filiere = parentView.getItemAtPosition(position).toString();
                fetchNotes(filiere);  // Appel pour récupérer les notes filtrées
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void setupDownloadButton() {
        telechargerBtn.setOnClickListener(view -> {
            if (noteAdapter != null) {
                List<Note> candidatsAuDessusDuSeuil = noteAdapter.getCandidatsAuDessusDuSeuil();
                if (!candidatsAuDessusDuSeuil.isEmpty()) {
                    generatePDF(candidatsAuDessusDuSeuil);
                } else {
                    Toast.makeText(this, "Aucun candidat au-dessus du seuil.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setupDownloadButtonResultat() {
        telechargerBtnAdmis.setOnClickListener(view -> {
            if (noteAdapter != null) {
                // Vérifier si tous les statuts sont remplis
                boolean tousStatutsRemplis = true;
                for (Note note : noteAdapter.getNotes()) {
                    if (note.getStatutOral() == null || note.getStatutOral().isEmpty()) {
                        tousStatutsRemplis = false;
                        break;  // Si un statut est vide, on interrompt la boucle
                    }
                }

                if (tousStatutsRemplis) {
                    // Récupérer les candidats ayant le statut "admis"
                    List<Note> candidatsAdmis = noteAdapter.getCandidatsAdmis();
                    if (!candidatsAdmis.isEmpty()) {
                        // Générer le PDF avec les candidats "admis"
                        generatePDFResultat(candidatsAdmis);
                    } else {
                        Toast.makeText(this, "Aucun candidat admis.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Afficher un message pour informer l'utilisateur qu'il faut entrer les résultats de l'oral
                    Toast.makeText(this, "Vous devez entrer les résultats de l'oral pour tous les candidats.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Nouvelle méthode pour envoyer le seuil à l'API
    private void envoyerSeuil() {
        String seuilStr = seuilEditText.getText().toString().trim();
        if (seuilStr.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une valeur pour le seuil.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir la valeur saisie en float
        float seuil = Float.parseFloat(seuilStr);

        // Créer l'objet SeuilOralEntity avec la valeur du seuil
        SeuilOralEntity seuilEntity = new SeuilOralEntity();
        seuilEntity.setSeuil(seuil);

        // Récupérer l'API Seuil
        SeuilApi seuilApi = RetrofitInstance.getSeuilApi();

        // Appeler l'API pour mettre à jour le seuil
        Call<SeuilOralEntity> call = seuilApi.updateSeuil(seuilEntity); // Utiliser l'objet SeuilOralEntity
        call.enqueue(new Callback<SeuilOralEntity>() {
            @Override
            public void onResponse(Call<SeuilOralEntity> call, Response<SeuilOralEntity> response) {
                if (response.isSuccessful()) {
                    // Afficher un message de succès
                    Toast.makeText(GestionNotesAdminActivity.this, "Liste Orale envoyé avec succès !", Toast.LENGTH_SHORT).show();
                } else {
                    // Afficher un message d'erreur avec le code de réponse
                    showError("Erreur lors de l'envoi du seuil : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SeuilOralEntity> call, Throwable t) {
                // Afficher un message d'erreur réseau
                showError("Erreur réseau : " + t.getMessage());
            }
        });
    }



    private void fetchNotes(String filiere) {
        NoteApi api = RetrofitInstance.getApi();
        Call<List<Note>> call = filiere.isEmpty() || "Toutes les Filières".equals(filiere)
                ? api.getNotes()  // Appel pour toutes les notes
                : api.getNotesByFiliere(filiere);  // Appel pour une filière spécifique

        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful()) {
                    List<Note> notes = response.body();
                    if (notes == null || notes.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        noteAdapter.updateNotes(notes);
                    }
                } else {
                    showError("Erreur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                showError("Erreur réseau : " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Log.e("GestionNotesAdminActivity", message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void generatePDF(List<Note> candidatsAuDessusDuSeuil) {
        try {
            String filePath = getExternalFilesDir(null) + "/Oral.pdf";
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Charger et ajouter le logo
            InputStream logoStream = getResources().openRawResource(R.drawable.logo_ensa);
            byte[] logoBytes = new byte[logoStream.available()];
            logoStream.read(logoBytes);
            logoStream.close();

            ImageData imageData = ImageDataFactory.create(logoBytes);
            Image logo = new Image(imageData).setWidth(120).setHeight(120).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);

            // Ajouter un titre et un sous-titre
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.add(new Paragraph("Liste des candidats convoqués pour passer l'oral")
                    .setFont(boldFont).setFontSize(24).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE).setMarginBottom(10));

            document.add(new Paragraph("Année Universitaire 2025-2026")
                    .setFont(font).setFontSize(20).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY).setMarginBottom(20));
// Grouper les notes par filière et générer les tableaux
            Map<String, List<Note>> candidatsParFiliere = candidatsAuDessusDuSeuil.stream()
                    .collect(Collectors.groupingBy(Note::getFiliere));

            for (Map.Entry<String, List<Note>> entry : candidatsParFiliere.entrySet()) {
                // Ajouter le titre de la filière
                document.add(new Paragraph(entry.getKey())
                        .setFont(boldFont).setFontSize(18).setFontColor(com.itextpdf.kernel.colors.ColorConstants.DARK_GRAY)
                        .setMarginTop(15).setMarginBottom(5));

                // Création du tableau avec 3 colonnes : Nom, Concours, Filière (exemple avec des colonnes égales)
                Table table = new Table(new float[]{1, 1, 1})  // 1/3 de la largeur pour chaque colonne
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginTop(15)
                        .setMarginBottom(15);


                // Ajouter les en-têtes du tableau
                table.addHeaderCell(new Cell().add(new Paragraph("CIN").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER).setPadding(10));
                table.addHeaderCell(new Cell().add(new Paragraph("Nom Complet").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER).setPadding(10));
                table.addHeaderCell(new Cell().add(new Paragraph("Filière").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER).setPadding(10));

                // Ajouter les données des candidats dans le tableau
                for (Note note : entry.getValue()) {
                    // Ajouter une ligne pour chaque candidat avec son nom et sa filière
                    table.addCell(new Cell().add(new Paragraph(note.getCIN()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell().add(new Paragraph(note.getNom()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell().add(new Paragraph(note.getFiliere()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                }

                // Ajouter le tableau au document
                document.add(table);
            }




            document.close();
            Toast.makeText(this, "PDF généré : " + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public void openAddNoteActivity(View view) {
        startActivity(new Intent(this, AddNoteActivity.class));
    }
    private void generatePDFResultat(List<Note> getCandidatsAdmis) {
        try {
            String filePath = getExternalFilesDir(null) + "/admis.pdf";
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Charger et ajouter le logo
            InputStream logoStream = getResources().openRawResource(R.drawable.logo_ensa);
            byte[] logoBytes = new byte[logoStream.available()];
            logoStream.read(logoBytes);
            logoStream.close();

            ImageData imageData = ImageDataFactory.create(logoBytes);
            Image logo = new Image(imageData).setWidth(120).setHeight(120).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);

            // Ajouter un titre et un sous-titre
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            document.add(new Paragraph("Liste principale des admis au concours passerelle")
                    .setFont(boldFont).setFontSize(24).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE).setMarginBottom(10));

            document.add(new Paragraph("Année Universitaire 2025-2026")
                    .setFont(font).setFontSize(20).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.GRAY).setMarginBottom(20));
// Grouper les notes par filière et générer les tableaux
            Map<String, List<Note>> candidatsParFiliere = getCandidatsAdmis.stream()
                    .collect(Collectors.groupingBy(Note::getFiliere));

            for (Map.Entry<String, List<Note>> entry : candidatsParFiliere.entrySet()) {
                // Ajouter le titre de la filière
                document.add(new Paragraph(entry.getKey())
                        .setFont(boldFont).setFontSize(18).setFontColor(com.itextpdf.kernel.colors.ColorConstants.DARK_GRAY)
                        .setMarginTop(15).setMarginBottom(5));


                Table table = new Table(new float[]{1, 1, 1})  // 1/3 de la largeur pour chaque colonne
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginTop(15)
                        .setMarginBottom(15);


                // Ajouter les en-têtes du tableau
                table.addHeaderCell(new Cell().add(new Paragraph("CIN").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER).setPadding(10));

                table.addHeaderCell(new Cell().add(new Paragraph("Nom Complet").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER).setPadding(10));
                table.addHeaderCell(new Cell().add(new Paragraph("Filière").setFont(boldFont).setFontSize(18))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER).setPadding(10));

                // Ajouter les données des candidats dans le tableau
                for (Note note : entry.getValue()) {
                    // Ajouter une ligne pour chaque candidat avec son nom et sa filière
                    table.addCell(new Cell().add(new Paragraph(note.getCIN()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell().add(new Paragraph(note.getNom()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                    table.addCell(new Cell().add(new Paragraph(note.getFiliere()).setFont(font).setFontSize(20))
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setPadding(8));
                }

                // Ajouter le tableau au document
                document.add(table);
            }


            document.close();
            Toast.makeText(this, "PDF généré : " + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
