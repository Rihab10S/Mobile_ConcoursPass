package com.example.concours_passerelle;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoordinateurAdapter extends RecyclerView.Adapter<CoordinateurAdapter.NoteViewHolder> {

    private List<Note> notes;


    public CoordinateurAdapter(List<Note> notes) {
        this.notes = notes;
    }



    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView idTextView;
        public TextView nomTextView;
        public TextView concoursTextView;
        public TextView statutTextView;
        public TextView noteTextView;
        public TextView filiereTextView;
        public TextView CINTextView;
        public Button editButtonoral;

        public NoteViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id_text);
            nomTextView = itemView.findViewById(R.id.name_text);
            concoursTextView = itemView.findViewById(R.id.concours_text);
            statutTextView = itemView.findViewById(R.id.status_text);
            noteTextView = itemView.findViewById(R.id.note_text);
            filiereTextView = itemView.findViewById(R.id.filiere_text);
            CINTextView = itemView.findViewById(R.id.CIN_text);
            editButtonoral = itemView.findViewById(R.id.edit_button_oral);
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Assurez-vous que le layout du cardview correspond à votre fichier item_note.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oral, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        // Log pour vérifier la valeur de note.getNote()
        Log.d("CoordinateurAdapter", "noteValue: " + note.getNote());

        // Affectation des données de l'objet Note aux TextViews
        holder.idTextView.setText(String.valueOf(note.getId()));
        holder.nomTextView.setText(note.getNom());
        holder.concoursTextView.setText(note.getConcours());
        holder.statutTextView.setText(note.getStatutOral());
        holder.noteTextView.setText(String.valueOf(note.getNote()));
        holder.filiereTextView.setText(note.getFiliere());
        holder.CINTextView.setText(note.getCIN());

        // Gestion de l'événement du bouton "Editer"
        holder.editButtonoral.setOnClickListener(v -> {
            // Créer une Intent pour ouvrir l'activité de modification (CoordinateurEditOralActivity)
            Intent intent = new Intent(holder.itemView.getContext(), CoordinateurEditOralActivity.class);

            // Passer les données de la note à l'activité d'édition via l'Intent
            intent.putExtra("noteId", note.getId());
            intent.putExtra("noteNom", note.getNom());
            intent.putExtra("noteConcours", note.getConcours());
            intent.putExtra("noteStatutOral", note.getStatutOral());
            intent.putExtra("noteFiliere", note.getFiliere());
            intent.putExtra("noteCIN", note.getCIN());
            intent.putExtra("noteValue", note.getNote());

            // Démarrer l'activité
            holder.itemView.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Permet d'actualiser la liste des notes dans l'adaptateur
    public void updateNotes(List<Note> newNotes) {
        notes.clear();
        notes.addAll(newNotes);
        notifyDataSetChanged();
    }


}
