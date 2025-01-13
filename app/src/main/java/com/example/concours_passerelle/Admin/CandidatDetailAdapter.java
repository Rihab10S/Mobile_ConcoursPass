package com.example.concours_passerelle.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.R;
import com.example.concours_passerelle.Candidat;

import java.util.List;

public class CandidatDetailAdapter extends RecyclerView.Adapter<CandidatDetailAdapter.CandidatDetailViewHolder> {

    private List<Candidat> candidats;

    // Constructor
    public CandidatDetailAdapter(List<Candidat> candidats) {
        this.candidats = candidats;
    }

    @NonNull
    @Override
    public CandidatDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item view (item_candidat_detail.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidat_detail, parent, false);
        return new CandidatDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatDetailViewHolder holder, int position) {
        Candidat candidat = candidats.get(position);

        // Lier les données aux vues avec des vérifications null pour éviter les erreurs
        holder.tvNom.setText("Nom : " + (candidat.getNom() != null ? candidat.getNom() : "Non spécifié"));
        holder.tvPrenom.setText("Prénom : " + (candidat.getPrenom() != null ? candidat.getPrenom() : "Non spécifié"));
        holder.tvCin.setText("CIN: " + (candidat.getCin() != null ? candidat.getCin() : "Non spécifié"));
        holder.tvDateNaissance.setText("Date de Naissance: " + (candidat.getDateNaissance() != null ? candidat.getDateNaissance() : "Non spécifiée"));
        holder.tvtelephone.setText("Téléphone: " + (candidat.getTel() != null ? candidat.getTel() : "Non spécifiée"));

        holder.tvEmail.setText("Email: " + (candidat.getEmail() != null ? candidat.getEmail() : "Non spécifié"));
        holder.tvFiliereChoisie.setText("Filière Choisie: " + (candidat.getFiliereChoisi() != null ? candidat.getFiliereChoisi() : "Non spécifiée"));
        holder.tvCodeEtudiant.setText("Code étudiant: " + (candidat.getCodeEtudiant() != null ? candidat.getCodeEtudiant() : "Non spécifié"));

        // Données Bac
        holder.tvSerieBac.setText("Série Bac: " + (candidat.getSerieBac() != null ? candidat.getSerieBac() : "Non spécifiée"));
        holder.tvMentionBac.setText("Mention Bac: " + (candidat.getMentionBac() != null ? candidat.getMentionBac() : "Non spécifiée"));
        holder.tvReleveBac.setText("Relevé Bac: " + (candidat.getReleveBac() != null ? candidat.getReleveBac() : "Non spécifié"));

        // Données Diplôme
        holder.tvTitreDiplome.setText("Titre du Diplôme: " + (candidat.getTitreDiplome() != null ? candidat.getTitreDiplome() : "Non spécifié"));
        holder.tvNotePremiereAnnee.setText("Note Première Année: " + formatFloat(candidat.getNotePremiereAnnee()));
        holder.tvNoteDeuxiemeAnnee.setText("Note Deuxième Année: " + formatFloat(candidat.getNoteDeuxiemeAnnee()));
        holder.tvEtablissement.setText("Etablissement: " + (candidat.getEtablissement() != null ? candidat.getEtablissement() : "Non spécifié"));
    }
    private String formatFloat(float value) {
        return value != 0.0f ? String.valueOf(value) : "Non spécifiée";
    }
    @Override
    public int getItemCount() {
        return candidats.size();
    }

    // ViewHolder pour l'élément du RecyclerView
    public static class CandidatDetailViewHolder extends RecyclerView.ViewHolder {

        // Déclaration des TextViews pour afficher les données
        TextView tvNom, tvCin, tvDateNaissance, tvEmail, tvFiliereChoisie, tvCodeEtudiant,tvtelephone;
        TextView tvSerieBac,tvPrenom, tvMentionBac, tvReleveBac, tvTitreDiplome, tvNotePremiereAnnee, tvNoteDeuxiemeAnnee, tvEtablissement;

        public CandidatDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialiser les TextViews avec leurs IDs respectifs
            tvNom = itemView.findViewById(R.id.tv_nom);
            tvPrenom = itemView.findViewById(R.id.tv_prenom);
            tvCin = itemView.findViewById(R.id.tv_cin);
            tvtelephone = itemView.findViewById(R.id.tv_telephone);
            tvDateNaissance = itemView.findViewById(R.id.tv_date_naissance);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvFiliereChoisie = itemView.findViewById(R.id.tv_filiere);
            tvCodeEtudiant = itemView.findViewById(R.id.tv_cne);

            // Données Bac
            tvSerieBac = itemView.findViewById(R.id.tv_serie_bac);
            tvMentionBac = itemView.findViewById(R.id.tv_mention_bac);
            tvReleveBac = itemView.findViewById(R.id.tv_relevé_bac);

            // Données Diplôme
            tvTitreDiplome = itemView.findViewById(R.id.tv_titre_diplome);
            tvNotePremiereAnnee = itemView.findViewById(R.id.tv_note_premiere_annee);
            tvNoteDeuxiemeAnnee = itemView.findViewById(R.id.tv_note_deuxieme_annee);
            tvEtablissement = itemView.findViewById(R.id.tv_etablissement);
        }
    }
}
