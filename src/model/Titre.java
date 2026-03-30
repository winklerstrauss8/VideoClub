package model;

/**
 * Classe Titre - Représente un titre de film
 * Un titre appartient à une seule catégorie
 * Plusieurs cassettes peuvent exister pour un même titre
 */
public class Titre {

    // Attributs
    private int idTitre;            
    private String nomTitre;        
    private String auteur;          
    private int duree;              
    private int idCategorie;         
    private String libelleCategorie; 

    // Constructeur vide
    public Titre() {
    }

    // Constructeur complet
    public Titre(int idTitre, String nomTitre, String auteur, int duree, int idCategorie) {
        this.idTitre = idTitre;
        this.nomTitre = nomTitre;
        this.auteur = auteur;
        this.duree = duree;
        this.idCategorie = idCategorie;
    }

    // --- Getters et Setters ---

    public int getIdTitre() {
        return idTitre;
    }

    public void setIdTitre(int idTitre) {
        this.idTitre = idTitre;
    }

    public String getNomTitre() {
        return nomTitre;
    }

    public void setNomTitre(String nomTitre) {
        this.nomTitre = nomTitre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getLibelleCategorie() {
        return libelleCategorie;
    }

    public void setLibelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    // Pour l'affichage dans les combobox
    @Override
    public String toString() {
        return nomTitre + " (" + auteur + ")";
    }
}
