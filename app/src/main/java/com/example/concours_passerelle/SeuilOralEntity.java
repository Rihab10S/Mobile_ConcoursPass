package com.example.concours_passerelle;

public class SeuilOralEntity {
    private Long id;    // Identifiant unique
    private float seuil; // Valeur du seuil

    // Constructeur par défaut
    public SeuilOralEntity() {}

    // Constructeur avec paramètres
    public SeuilOralEntity(Long id, float seuil) {
        this.id = id;
        this.seuil = seuil;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getSeuil() {
        return seuil;
    }

    public void setSeuil(float seuil) {
        this.seuil = seuil;
    }


}
