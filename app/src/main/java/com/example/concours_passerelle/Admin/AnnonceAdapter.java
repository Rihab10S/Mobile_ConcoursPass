package com.example.concours_passerelle.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

    public AnnonceAdapter(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public static class AnnonceViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView visibilityTextView;
        public TextView statusTextView;
        public TextView typeTextView;
        public TextView contentTextView;
        public TextView createdAtTextView;
        public TextView annonceIdTextView; // Added for annonce_id
        public Button deleteButton;
        public Button editButton;

        public AnnonceViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.annonce_title); // Corrected ID
            visibilityTextView = itemView.findViewById(R.id.visibility_text); // Corrected ID
            statusTextView = itemView.findViewById(R.id.status_text); // Corrected ID
            typeTextView = itemView.findViewById(R.id.type_text); // Corrected ID
            contentTextView = itemView.findViewById(R.id.content_text); // Corrected ID
            createdAtTextView = itemView.findViewById(R.id.created_at_text);
            annonceIdTextView = itemView.findViewById(R.id.annonce_id); // New ID for annonce_id// Corrected ID
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
        // Set default values if the field is null
        holder.annonceIdTextView.setText(annonce.getId() != null ? annonce.getId().toString() : "ID inconnu");
        holder.titleTextView.setText(annonce.getTitle() != null ? annonce.getTitle() : "Titre inconnu");
        holder.visibilityTextView.setText(annonce.getVisibility() != null ? annonce.getVisibility() : "Inconnu");
        holder.statusTextView.setText(annonce.getStatus() != null ? annonce.getStatus() : "Inconnu");
        holder.typeTextView.setText(annonce.getType() != null ? annonce.getType() : "Inconnu");
        holder.contentTextView.setText(annonce.getContent() != null ? annonce.getContent() : "Pas de contenu");

        // Handle createdAt with null check
        holder.createdAtTextView.setText(annonce.getCreatedAt() != null ? annonce.getCreatedAt().toString() : "Date inconnue");

        // Event to delete the annonce
        holder.deleteButton.setOnClickListener(v -> {
            deleteAnnonce(annonce.getId(), position, holder.itemView.getContext());
        });

        // Event to edit the annonce
        holder.editButton.setOnClickListener(v -> {
            // Create an Intent to open the edit activity
            Intent intent = new Intent(holder.itemView.getContext(), EditAnnonceActivity.class);
            // Pass the annonce data to the edit activity
            intent.putExtra("annonceId", annonce.getId());
            intent.putExtra("annonceTitle", annonce.getTitle());
            intent.putExtra("annonceVisibility", annonce.getVisibility());
            intent.putExtra("annonceStatus", annonce.getStatus());
            intent.putExtra("annonceType", annonce.getType());
            intent.putExtra("annonceContent", annonce.getContent());
            intent.putExtra("annonceCreatedAt", annonce.getCreatedAt() != null ? annonce.getCreatedAt().toString() : "Date inconnue");
            holder.itemView.getContext().startActivity(intent);
        });
    }

    private void deleteAnnonce(Long annonceId, int position, Context context) {
        AnnonceApi api = RetrofitInstance.getAnnonceApi();

        // Make the API request to delete the annonce
        Call<Void> call = api.deleteAnnonce(annonceId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remove the item from the list
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

