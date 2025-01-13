package com.example.concours_passerelle;

public class Note {

    private Long id;
    private String nom;
    private String concours;
    private String statutOral;
    private double note;
    private String filiere;
    private String cin;
    private Boolean statuSendAdmin;
    private Boolean statusPublication;


    // Constructeur avec tous les paramètres
    public Note(Long id, String nom, String concours, String statutOral, double note, String filiere,  String cin, Boolean statuSendAdmin, Boolean statusPublication) {
        this.id = id;
        this.nom = nom;
        this.concours = concours;
        this.statutOral= statutOral;
        this.note = note;
        this.filiere = filiere;
        this.cin = cin;  // Correctement assigner CIN
        this.statuSendAdmin = statuSendAdmin;
        this.statusPublication = statusPublication;
    }

    // Constructeur sans id et statuSendAdmin, statusPublication
    public Note(String nom, String concours, double note, String filiere, String cin) {
        this.nom = nom;
        this.concours = concours;
        this.note = note;
        this.filiere = filiere;
        this.cin = cin;
        this.statutOral = "R_Oral";
        this.statuSendAdmin = false;  // Par défaut
        this.statusPublication = false;  // Par défaut
    }




    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCIN() {
        return cin;
    }

    public void setCIN(String CIN) {
        this.cin = CIN; // Assurez-vous de modifier CIN et non id
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

    public String getStatutOral() {
        return statutOral;
    }

    public void setStatutOral(String status_orale) {
        this.statutOral = status_orale;
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
