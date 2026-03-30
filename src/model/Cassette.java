package model;

/**
 * Classe Cassette - Représente une cassette vidéo physique
 * Plusieurs cassettes peuvent exister pour un même titre
 */
public class Cassette {

    // Attributs
    private int numCassette;      // Numéro unique de la cassette
    private String dateAchat;     // Date d'achat (format yyyy-MM-dd)
    private double prix;          // Prix de la cassette
    private int idTitre;          // Référence vers le titre du film
    private String nomTitre;      // Pour l'affichage (jointure)

    // Constructeur vide
    public Cassette() {
    }

    // Constructeur complet
    public Cassette(int numCassette, String dateAchat, double prix, int idTitre) {
        this.numCassette = numCassette;
        this.dateAchat = dateAchat;
        this.prix = prix;
        this.idTitre = idTitre;
    }

    // --- Getters et Setters ---

    public int getNumCassette() {
        return numCassette;
    }

    public void setNumCassette(int numCassette) {
        this.numCassette = numCassette;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

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

    // Pour l'affichage dans les combobox
    @Override
    public String toString() {
        return "Cassette n°" + numCassette + " - " + nomTitre;
    }
}
