package com.example.concours_passerelle;

public class Note {

    private Long id;
    private String nom;
    private String concours;
    private String statut;
    private double note;
    private String filiere;
    private String email;
    private Boolean statuSendAdmin;
    private Boolean statusPublication;

    // Constructeur avec tous les paramètres
    public Note(Long id, String nom, String concours, String statut, double note, String filiere, String email, Boolean statuSendAdmin, Boolean statusPublication) {
        this.id = id;
        this.nom = nom;
        this.concours = concours;
        this.statut = statut;
        this.note = note;
        this.filiere = filiere;
        this.email = email;
        this.statuSendAdmin = statuSendAdmin;
        this.statusPublication = statusPublication;
    }

    // Constructeur sans id et statuSendAdmin, statusPublication
    public Note(String nom, String concours, String statut, double note, String filiere, String email) {
        this.nom = nom;
        this.concours = concours;
        this.statut = statut;
        this.note = note;
        this.filiere = filiere;
        this.email = email;
        this.statuSendAdmin = false;
        this.statusPublication = false;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getConcours() {
        return concours;
    }

    public void setConcours(String concours) {
        this.concours = concours;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatuSendAdmin() {
        return statuSendAdmin;
    }

    public void setStatuSendAdmin(Boolean statuSendAdmin) {
        this.statuSendAdmin = statuSendAdmin;
    }

    public Boolean getStatusPublication() {
        return statusPublication;
    }

    public void setStatusPublication(Boolean statusPublication) {
        this.statusPublication = statusPublication;
    }
}
