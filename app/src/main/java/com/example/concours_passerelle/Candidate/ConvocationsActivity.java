package com.example.concours_passerelle.Candidate;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.concours_passerelle.R;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.InputStream;

public class ConvocationsActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView titleEcrit;
    private ImageView headerImage;
    private TextView nomCandidat;
    private TextView moyenne;
    private Button telechargerButton;
    private Button telechargerOralButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocations);

        // Initialisation des éléments du layout
        titleText = findViewById(R.id.title_text);
        titleEcrit = findViewById(R.id.title_ecrit);
        headerImage = findViewById(R.id.header_image);
        nomCandidat = findViewById(R.id.nom_candidat);
        moyenne = findViewById(R.id.moyenne);
        telechargerButton = findViewById(R.id.telechargerConvo_button);
        Button telechargerOralButton = findViewById(R.id.telechargerConvoOral_button);

        // Exemple de données dynamiques
        titleText.setText("Mes Convocations");
        titleEcrit.setText("Concours écrit :");
        nomCandidat.setText("John Doe");
        moyenne.setText("Moyenne: 15/20");

        // Bouton de téléchargement pour la convocation écrite
        telechargerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genererPdf();  // Générer le PDF écrit
            }
        });

        // Bouton de téléchargement pour la convocation orale
        telechargerOralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genererPdfOral();  // Générer le PDF oral
            }
        });
    }


    private void genererPdf() {
        // Chemin de sauvegarde du fichier
        String filePath = getExternalFilesDir(null)+ "/Convocation_écrit.pdf";

        try {
            // Création du fichier PDF
            PdfWriter writer = new PdfWriter(filePath);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDocument, com.itextpdf.kernel.geom.PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            // Styles améliorés
            Style titleStyle = new Style()
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);

            Style subtitleStyle = new Style()
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            Style subtitleStyle2 = new Style()
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(14);

            Style normalStyle = new Style()
                    .setFontSize(19)
                    .setMarginBottom(5);
            Style normalStyle2 = new Style()
                    .setFontSize(19)
                    .setMarginBottom(5)
                    .setBold(); // Espacement entre les paragraphes

            // Contenu du PDF
            InputStream logoStream = getResources().openRawResource(R.drawable.logo_ensa);
            byte[] logoBytes = new byte[logoStream.available()];
            logoStream.read(logoBytes);
            logoStream.close();

            ImageData imageData = ImageDataFactory.create(logoBytes);
            Image logo = new Image(imageData).setWidth(140).setHeight(140).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
            document.add(new Paragraph("Convocation\nCONCOURS COMMUN D'ACCES AUX CYCLES INGENIEUR DE L'ENSA DE KENITRA")
                    .addStyle(subtitleStyle2));
            document.add(new Paragraph("Septembre 2025").addStyle(subtitleStyle).setMarginBottom(20));

            // Table avec bordures visibles
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(20);

            table.addCell(new Cell().add(new Paragraph("Nom et Prénom :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("JARY Oumaima").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph("N° de convocation :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("1").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph("CIN :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("G759728").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph("CNE :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("E741852").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph("Filière :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Génie Industriel").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

            document.add(table);

            // Informations supplémentaires
            document.add(new Paragraph("Veuillez-vous présenter aux épreuves écrites du concours qui se déroulera")
                    .addStyle(normalStyle).setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("dimanche 14 septembre 2025 à 8h00 du matin")
                    .setBold().setTextAlignment(TextAlignment.RIGHT).addStyle(subtitleStyle).setFontColor(ColorConstants.RED));

            // Table pour le lieu d'examen avec un seul colonne
            Table lieuTable = new Table(UnitValue.createPercentArray(1))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(20);

            lieuTable.addCell(new Paragraph("Lieu d'examen :")
                    .addStyle(normalStyle)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)); // Centrer le titre

            lieuTable.addCell(new Paragraph("L'examen aura lieu à l'École Nationale des Sciences Appliquées de Kenitra, dans la salle C2 du Bâtiment C. "
                    + "Veuillez vous présenter une heure avant le début de l'examen pour l'enregistrement et la vérification des informations.")
                    .addStyle(normalStyle)
                    .setTextAlignment(TextAlignment.CENTER)); // Centrer la description

            document.add(lieuTable);

            // Consignes importantes
            document.add(new Paragraph("Consignes importantes").addStyle(titleStyle).setMarginTop(20));
            document.add(new Paragraph("1. Les candidats doivent se présenter au centre du concours à 8 heures et être munis uniquement de :")
                    .addStyle(normalStyle));
            document.add(new Paragraph("   a) la présente convocation\n   b) une carte nationale d'identité\n   c) un stylo bleu ou noir")
                    .addStyle(normalStyle) .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("2. Il est strictement interdit d'introduire dans la salle d'examen un téléphone portable, la calculatrice, "
                    + "ou tout autre objet interdit.").addStyle(normalStyle) .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("3. Aucun document en dehors du matériel précité n'est autorisé dans les centres des examens.")
                    .addStyle(normalStyle) .setTextAlignment(TextAlignment.CENTER));

            // Fermer le document
            document.close();
            Toast.makeText(this, "PDF généré avec succès à : " + filePath, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du PDF.", Toast.LENGTH_SHORT).show();
        }}

        private void genererPdfOral() {
            // Chemin de sauvegarde du fichier
            String filePath = getExternalFilesDir(null)+ "/Convocation_Oral.pdf";

            try {
                // Création du fichier PDF
                PdfWriter writer = new PdfWriter(filePath);
                com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                Document document = new Document(pdfDocument, com.itextpdf.kernel.geom.PageSize.A4);
                document.setMargins(20, 20, 20, 20);

                // Styles améliorés
                Style titleStyle = new Style()
                        .setFontSize(20)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(10);

                Style subtitleStyle = new Style()
                        .setFontSize(18)
                        .setBold()
                        .setFontColor(ColorConstants.RED)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(10);
                Style subtitleStyle2 = new Style()
                        .setFontSize(18)
                        .setBold()
                        .setFontColor(ColorConstants.BLACK)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(14);

                Style normalStyle = new Style()
                        .setFontSize(19)
                        .setMarginBottom(5);
                Style normalStyle2 = new Style()
                        .setFontSize(19)
                        .setMarginBottom(5)
                        .setBold(); // Espacement entre les paragraphes

                // Contenu du PDF
                InputStream logoStream = getResources().openRawResource(R.drawable.logo_ensa);
                byte[] logoBytes = new byte[logoStream.available()];
                logoStream.read(logoBytes);
                logoStream.close();

                ImageData imageData = ImageDataFactory.create(logoBytes);
                Image logo = new Image(imageData).setWidth(140).setHeight(140).setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
                document.add(new Paragraph("Convocation\nCONCOURS ORALE  D'ACCES AUX CYCLES INGENIEUR DE L'ENSA DE KENITRA")
                        .addStyle(subtitleStyle2));
                document.add(new Paragraph("Octobre 2025").addStyle(subtitleStyle).setMarginBottom(20));

                // Table avec bordures visibles
                Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginBottom(20);

                table.addCell(new Cell().add(new Paragraph("Nom et Prénom :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("JARY Oumaima").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph("N° de convocation :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("1").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph("CIN :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("G759728").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph("CNE :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("E741852").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph("Filière :").addStyle(normalStyle2)).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("Génie Industriel").addStyle(normalStyle)).setBorder(Border.NO_BORDER));

                document.add(table);

                // Informations supplémentaires
                document.add(new Paragraph("Veuillez-vous présenter aux entretiens orales du concours qui se déroulera")
                        .addStyle(normalStyle).setTextAlignment(TextAlignment.LEFT));
                document.add(new Paragraph("dimanche 04 Octobre 2025 à 10h00 du matin")
                        .setBold().setTextAlignment(TextAlignment.RIGHT).addStyle(subtitleStyle).setFontColor(ColorConstants.RED));

                // Table pour le lieu d'examen avec un seul colonne
                Table lieuTable = new Table(UnitValue.createPercentArray(1))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginTop(20);

                lieuTable.addCell(new Paragraph("Lieu d'examen :")
                        .addStyle(normalStyle)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)); // Centrer le titre

                lieuTable.addCell(new Paragraph("L'examen aura lieu à l'École Nationale des Sciences Appliquées de Kenitra, dans la salle B6 du Bâtiment B. "
                        + "Veuillez vous présenter une heure avant le début de l'examen pour l'enregistrement et la vérification des informations.")
                        .addStyle(normalStyle)
                        .setTextAlignment(TextAlignment.CENTER)); // Centrer la description

                document.add(lieuTable);

                // Consignes importantes
                document.add(new Paragraph("Consignes Importantes Pour l'entretien Oral")
                        .addStyle(titleStyle).setMarginTop(20));

// Consignes générales
                document.add(new Paragraph("Consignes Générales :")
                        .addStyle(subtitleStyle2)
                        .setUnderline()
                        .setMarginBottom(10)
                        .setTextAlignment(TextAlignment.LEFT));
                document.add(new Paragraph("• Les candidats doivent se présenter au centre du concours à 10 heures du matin.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• Veuillez arriver au moins 30 minutes avant l'heure de l'entretien pour les formalités d'inscription.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• L'entretien dure environ 20 à 30 minutes.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• Assurez-vous d'être à l'heure. Tout retard pourrait entraîner l'annulation de votre candidature.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• Adoptez une tenue vestimentaire adéquate, correspondant à l'importance de cet entretien.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• Préparez-vous à répondre à des questions techniques liées à votre parcours, vos projets académiques et professionnels, ainsi que votre motivation.")
                        .addStyle(normalStyle));

// Documents à apporter
                document.add(new Paragraph("Documents à apporter :").addStyle(subtitleStyle2).setMarginTop(20).setMarginBottom(10) .setUnderline().setTextAlignment(TextAlignment.LEFT));
                document.add(new Paragraph("• Une copie de votre carte nationale d'identité (CIN) ou passeport.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• Votre convocation imprimée.")
                        .addStyle(normalStyle));
                document.add(new Paragraph("• Une copie des certificats originaux des diplômes obtenus et des relevés des mentions dans votre cursus.")
                        .addStyle(normalStyle));

                // Fermer le document
                document.close();
                Toast.makeText(this, "PDF généré avec succès à : " + filePath, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de la génération du PDF.", Toast.LENGTH_SHORT).show();
            }




    }

}