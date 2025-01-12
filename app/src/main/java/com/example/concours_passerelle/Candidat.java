package com.example.concours_passerelle;
public class Candidat {
    private Long idCandidat;
    private String nom;
    private String prenom;
    private String cin;
    private String codeEtudiant;
    private String filiereChoisi;
    private float notePremiereAnnee;
    private float noteDeuxiemeAnnee;
    private double noteMoyenne; // Champ calculé

    // Getters et Setters
    public Long getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(Long idCandidat) {
        this.idCandidat = idCandidat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {  // Getter pour le prénom
        return prenom;
    }

    public void setPrenom(String prenom) {  // Setter pour le prénom
        this.prenom = prenom;
    }
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCodeEtudiant() {
        return codeEtudiant;
    }

    public void setCodeEtudiant(String codeEtudiant) {
        this.codeEtudiant = codeEtudiant;
    }

    public String getFiliereChoisi() {
        return filiereChoisi;
    }

    public void setFiliereChoisi(String filiereChoisi) {
        this.filiereChoisi = filiereChoisi;
    }

    public float getNoteMoyenne() {
        return (notePremiereAnnee + noteDeuxiemeAnnee) / 2;
    }

    public String getNomComplet() {
        return nom + " " + prenom; // Combinaison du nom et prénom
    }
    public void setNoteMoyenne(double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }
}
