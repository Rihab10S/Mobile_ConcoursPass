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
    private String dateNaissance;
    private String email;
    private String etablissement;
    private String genre;
    private String mentionBac;
    private String pays;
    private String releveBac;
    private String releveDiplomeAnnee1;
    private String releveDiplomeAnnee2;
    private String serieBac;
    private String tel;
    private String titreDiplome;
    private String ville;
    private String copiediplome;



    public void setCopiediplome(String copiediplome) {
        this.copiediplome = copiediplome;
    }
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
        return nom + " " + prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public float getNotePremiereAnnee() {
        return notePremiereAnnee;
    }

    public void setNotePremiereAnnee(float notePremiereAnnee) {
        this.notePremiereAnnee = notePremiereAnnee;
    }

    public float getNoteDeuxiemeAnnee() {
        return noteDeuxiemeAnnee;
    }

    public void setNoteDeuxiemeAnnee(float noteDeuxiemeAnnee) {
        this.noteDeuxiemeAnnee = noteDeuxiemeAnnee;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMentionBac() {
        return mentionBac;
    }

    public void setMentionBac(String mentionBac) {
        this.mentionBac = mentionBac;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getReleveBac() {
        return releveBac;
    }

    public void setReleveBac(String releveBac) {
        this.releveBac = releveBac;
    }

    public String getReleveDiplomeAnnee1() {
        return releveDiplomeAnnee1;
    }

    public void setReleveDiplomeAnnee1(String releveDiplomeAnnee1) {
        this.releveDiplomeAnnee1 = releveDiplomeAnnee1;
    }

    public String getReleveDiplomeAnnee2() {
        return releveDiplomeAnnee2;
    }

    public void setReleveDiplomeAnnee2(String releveDiplomeAnnee2) {
        this.releveDiplomeAnnee2 = releveDiplomeAnnee2;
    }

    public String getSerieBac() {
        return serieBac;
    }

    public void setSerieBac(String serieBac) {
        this.serieBac = serieBac;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitreDiplome() {
        return titreDiplome;
    }

    public void setTitreDiplome(String titreDiplome) {
        this.titreDiplome = titreDiplome;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}

