package com.example.concours_passerelle.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.Admin.EditAnnonceActivity;
import com.example.concours_passerelle.Admin.AnnonceApi;
import com.example.concours_passerelle.Admin.RetrofitInstance;
import com.example.concours_passerelle.Annonce;
import com.example.concours_passerelle.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {

    private List<Annonce> annonces;
    private boolean isDashboard;

    public AnnonceAdapter(List<Annonce> annonces, boolean isDashboard) {
        this.annonces = annonces;
        this.isDashboard = isDashboard;
    }

    public static class AnnonceViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView visibilityTextView;
        public TextView statusTextView;
        public TextView typeTextView;
        public TextView contentTextView;
        public TextView createdAtTextView;
        public TextView annonceIdTextView;
        public Button deleteButton;
        public Button editButton;

        public AnnonceViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.annonce_title);
            visibilityTextView = itemView.findViewById(R.id.visibility_text);
            statusTextView = itemView.findViewById(R.id.status_text);
            typeTextView = itemView.findViewById(R.id.type_text);
            contentTextView = itemView.findViewById(R.id.content_text);
            createdAtTextView = itemView.findViewById(R.id.created_at_text);
            annonceIdTextView = itemView.findViewById(R.id.annonce_id);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }

    @Override
    public AnnonceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annonce, parent, false);
        return new AnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnonceViewHolder holder, int position) {
        Annonce annonce = annonces.get(position);

        // Configurer les données de l'annonce
        holder.annonceIdTextView.setText(getTextOrDefault(annonce.getId(), "ID inconnu"));
        holder.titleTextView.setText(getTextOrDefault(annonce.getTitle(), "Titre inconnu"));
        holder.visibilityTextView.setText(getTextOrDefault(annonce.getVisibility(), "Inconnu"));
        holder.statusTextView.setText(getTextOrDefault(annonce.getStatus(), "Inconnu"));
        holder.typeTextView.setText(getTextOrDefault(annonce.getType(), "Inconnu"));
        holder.contentTextView.setText(getTextOrDefault(annonce.getContent(), "Pas de contenu"));
        holder.createdAtTextView.setText(getTextOrDefault(annonce.getCreatedAt(), "Date inconnue"));

        // Configurer la visibilité des boutons
        configureButtonsVisibility(holder, isDashboard);

        // Définir les actions des boutons
        holder.deleteButton.setOnClickListener(v -> deleteAnnonce(annonce.getId(), position, holder.itemView.getContext()));
        holder.editButton.setOnClickListener(v -> openEditActivity(holder.itemView.getContext(), annonce));
    }

    private void configureButtonsVisibility(AnnonceViewHolder holder, boolean isDashboard) {
        int visibility = isDashboard ? View.GONE : View.VISIBLE;
        holder.deleteButton.setVisibility(visibility);
        holder.editButton.setVisibility(visibility);
    }

    private String getTextOrDefault(Object value, String defaultValue) {
        return value != null ? value.toString() : defaultValue;
    }

    private void openEditActivity(Context context, Annonce annonce) {
        Intent intent = new Intent(context, EditAnnonceActivity.class);
        intent.putExtra("annonceId", annonce.getId());
        intent.putExtra("annonceTitle", annonce.getTitle());
        intent.putExtra("annonceVisibility", annonce.getVisibility());
        intent.putExtra("annonceStatus", annonce.getStatus());
        intent.putExtra("annonceType", annonce.getType());
        intent.putExtra("annonceContent", annonce.getContent());
        intent.putExtra("annonceCreatedAt", getTextOrDefault(annonce.getCreatedAt(), "Date inconnue"));
        context.startActivity(intent);
    }

    private void deleteAnnonce(Long annonceId, int position, Context context) {
        AnnonceApi api = RetrofitInstance.getAnnonceApi();
        Call<Void> call = api.deleteAnnonce(annonceId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    annonces.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Annonce supprimée avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return annonces.size();
    }

    public void updateAnnonces(List<Annonce> newAnnonces) {
        annonces.clear();
        annonces.addAll(newAnnonces);
        notifyDataSetChanged();
    }
}
