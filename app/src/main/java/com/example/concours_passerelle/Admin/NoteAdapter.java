package com.example.concours_passerelle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.concours_passerelle.Admin.EditNoteActivity;
import com.example.concours_passerelle.Admin.NoteApi;
import com.example.concours_passerelle.Admin.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private double seuil = -1; // Valeur par défaut (aucun seuil appliqué)

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public void setSeuil(double seuil) {
        this.seuil = seuil;
        notifyDataSetChanged();  // Re-render all the items when the seuil is updated
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView idTextView;
        public TextView nomTextView;
        public TextView concoursTextView;
        public TextView statutTextView;
        public TextView noteTextView;
        public TextView filiereTextView;
        public TextView emailTextView;
        public Button deleteButton;
        public Button editButton;

        public NoteViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id_text);
            nomTextView = itemView.findViewById(R.id.name_text);
            concoursTextView = itemView.findViewById(R.id.concours_text);
            statutTextView = itemView.findViewById(R.id.status_text);
            noteTextView = itemView.findViewById(R.id.note_text);
            filiereTextView = itemView.findViewById(R.id.filiere_text);
            emailTextView = itemView.findViewById(R.id.email_text);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);  // Ajouter le bouton Edit
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.idTextView.setText(String.valueOf(note.getId()));
        holder.nomTextView.setText(note.getNom());
        holder.concoursTextView.setText(note.getConcours());
        holder.statutTextView.setText(note.getStatut());
        holder.noteTextView.setText(String.valueOf(note.getNote()));
        holder.filiereTextView.setText(note.getFiliere());
        holder.emailTextView.setText(note.getEmail());

        // Appliquer la couleur et le style en fonction du seuil
        if (seuil >= 0) {
            if (note.getNote() < seuil) {
                // Si la note est en dessous du seuil
                holder.itemView.setBackgroundColor(Color.parseColor("#FFEAEA")); // Fond rouge clair
                holder.noteTextView.setTextColor(Color.parseColor("#D72638")); // Texte rouge foncé
                holder.noteTextView.setTypeface(Typeface.DEFAULT_BOLD); // Texte en gras
                holder.noteTextView.setBackground(createBorderDrawable("#FF5C5C")); // Bordure rouge
            } else {
                // Si la note est au-dessus du seuil
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")); // Fond blanc
                holder.noteTextView.setTextColor(Color.parseColor("#2D3436")); // Texte noir
                holder.noteTextView.setTypeface(Typeface.DEFAULT); // Texte normal
                holder.noteTextView.setBackground(null); // Pas de bordure
            }
        }

        // Événement pour supprimer la note
        holder.deleteButton.setOnClickListener(v -> {
            deleteNote(note.getId().intValue(), position, holder.itemView.getContext());
        });

        // Événement pour éditer la note
        holder.editButton.setOnClickListener(v -> {
            // Créer une Intent pour ouvrir l'activité d'édition
            Intent intent = new Intent(holder.itemView.getContext(), EditNoteActivity.class);
            // Passer les données de la note à l'activité d'édition
            intent.putExtra("noteId", note.getId());
            intent.putExtra("noteNom", note.getNom());
            intent.putExtra("noteConcours", note.getConcours());
            intent.putExtra("noteStatut", note.getStatut());
            intent.putExtra("noteFiliere", note.getFiliere());
            intent.putExtra("noteEmail", note.getEmail());
            intent.putExtra("noteValue", note.getNote());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    private GradientDrawable createBorderDrawable(String colorHex) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#FFEAEA")); // Fond rouge clair
        drawable.setCornerRadius(12); // Coins arrondis
        drawable.setStroke(2, Color.parseColor(colorHex)); // Bordure rouge foncé
        return drawable;
    }

    private void deleteNote(int noteId, int position, Context context) {
        NoteApi api = RetrofitInstance.getApi();

        // Effectuer la requête API pour supprimer la note
        Call<Void> call = api.deleteNote(noteId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Supprimer l'élément de la liste
                    notes.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Note supprimée avec succès", Toast.LENGTH_SHORT).show();
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
        return notes.size();
    }
    public List<Note> getCandidatsAuDessusDuSeuil() {
        List<Note> candidatsAuDessusDuSeuil = new ArrayList<>();
        for (Note note : notes) {
            if (note.getNote() >= seuil) {
                candidatsAuDessusDuSeuil.add(note);
            }
        }
        return candidatsAuDessusDuSeuil;
    }

    public void updateNotes(List<Note> newNotes) {
        notes.clear();
        notes.addAll(newNotes);
        notifyDataSetChanged();
    }
}
