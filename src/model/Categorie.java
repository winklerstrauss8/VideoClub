package model;

/**
 * Classe Categorie - Représente une catégorie de film
 * Exemple : Action, Comédie, Horreur, etc.
 */
public class Categorie {

    // Attributs
    private int idCategorie;           // Identifiant unique de la catégorie
    private String libelleCategorie;   // Nom de la catégorie

    // Constructeur vide (nécessaire pour les formulaires)
    public Categorie() {
    }

    // Constructeur avec tous les paramètres
    public Categorie(int idCategorie, String libelleCategorie) {
        this.idCategorie = idCategorie;
        this.libelleCategorie = libelleCategorie;
    }

    // --- Getters et Setters ---

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

    // Affichage dans les combobox et les listes
    @Override
    public String toString() {
        return libelleCategorie;
    }
}
