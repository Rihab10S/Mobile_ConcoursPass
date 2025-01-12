package com.example.concours_passerelle.Admin;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.R;
import com.example.concours_passerelle.Candidat;

import java.util.ArrayList;
import java.util.List;

public class PreinscritAdapter extends RecyclerView.Adapter<PreinscritAdapter.CandidatViewHolder> {

    private List<Candidat> candidats;
    private double seuil = -1; // Valeur par défaut (aucun seuil appliqué)

    public PreinscritAdapter(List<Candidat> candidats) {
        this.candidats = candidats;
    }

    public void setSeuil(double seuil) {
        this.seuil = seuil;

        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public CandidatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liste_preinscrit, parent, false);
        return new CandidatViewHolder(view);
    }
    public List<Candidat> getCandidatsAuDessusDuSeuil() {
        List<Candidat> result = new ArrayList<>();
        for (Candidat candidat : candidats) {
            if (candidat.getNoteMoyenne() >= seuil) {
                result.add(candidat);
            }
        }
        return result;
    }


    @Override
    public void onBindViewHolder(@NonNull CandidatViewHolder holder, int position) {
        Candidat candidat = candidats.get(position);

        // Remplir les champs de texte avec les données du candidat
        holder.tvNom.setText(candidat.getNomComplet());
        holder.tvCin.setText(candidat.getCin());
        holder.tvCodeEtudiant.setText(candidat.getCodeEtudiant());
        holder.tvFiliere.setText(candidat.getFiliereChoisi());
        holder.tvNoteMoyenne.setText(String.valueOf(candidat.getNoteMoyenne()));

        // Appliquer la couleur et le style en fonction du seuil
        if (seuil >= 0) {
            if (candidat.getNoteMoyenne() < seuil) {
                // Notes sous le seuil : design rouge professionnel
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFEAEA")); // Fond rouge clair
                holder.tvNoteMoyenne.setTextColor(Color.parseColor("#D72638")); // Texte rouge foncé
                holder.tvNoteMoyenne.setTypeface(Typeface.DEFAULT_BOLD); // Gras
                holder.tvNoteMoyenne.setBackground(createBorderDrawable("#FF5C5C")); // Bordure rouge
            } else {
                // Notes au-dessus du seuil : design normal
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF")); // Fond blanc
                holder.tvNoteMoyenne.setTextColor(Color.parseColor("#2D3436")); // Texte noir
                holder.tvNoteMoyenne.setTypeface(Typeface.DEFAULT); // Normal
                holder.tvNoteMoyenne.setBackground(null); // Pas de bordure
            }
        }
    }

    @Override
    public int getItemCount() {
        return candidats.size();
    }

    public static class CandidatViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom, tvCin, tvCodeEtudiant, tvFiliere, tvNoteMoyenne;
        CardView cardView;

        public CandidatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvNom = itemView.findViewById(R.id.tv_name);
            tvCin = itemView.findViewById(R.id.tv_cin);
            tvCodeEtudiant = itemView.findViewById(R.id.tv_code);
            tvFiliere = itemView.findViewById(R.id.tv_filiere);
            tvNoteMoyenne = itemView.findViewById(R.id.tv_note);
        }
    }

    private GradientDrawable createBorderDrawable(String colorHex) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#FFEAEA")); // Fond rouge clair
        drawable.setCornerRadius(12); // Coins arrondis
        drawable.setStroke(2, Color.parseColor(colorHex)); // Bordure rouge foncé
        return drawable;
    }
}
