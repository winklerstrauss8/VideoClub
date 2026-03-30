package model;

/**
 * Classe Location - Représente une location de cassette
 * Clé primaire composite : (numAbonne, numCassette)
 * On ne garde que la dernière date de location pour un abonné et une cassette
 */
public class Location {

    // Attributs
    private int numAbonne;         // Référence vers l'abonné
    private int numCassette;       // Référence vers la cassette
    private String dateLocation;   // Date de la location
    private String nomAbonne;      // Pour l'affichage (jointure)
    private String nomTitre;       // Pour l'affichage (jointure)

    // Constructeur vide
    public Location() {
    }

    // Constructeur avec les clés et la date
    public Location(int numAbonne, int numCassette, String dateLocation) {
        this.numAbonne = numAbonne;
        this.numCassette = numCassette;
        this.dateLocation = dateLocation;
    }

    // --- Getters et Setters ---

    public int getNumAbonne() {
        return numAbonne;
    }

    public void setNumAbonne(int numAbonne) {
        this.numAbonne = numAbonne;
    }

    public int getNumCassette() {
        return numCassette;
    }

    public void setNumCassette(int numCassette) {
        this.numCassette = numCassette;
    }

    public String getDateLocation() {
        return dateLocation;
    }

    public void setDateLocation(String dateLocation) {
        this.dateLocation = dateLocation;
    }

    public String getNomAbonne() {
        return nomAbonne;
    }

    public void setNomAbonne(String nomAbonne) {
        this.nomAbonne = nomAbonne;
    }

    public String getNomTitre() {
        return nomTitre;
    }

    public void setNomTitre(String nomTitre) {
        this.nomTitre = nomTitre;
    }

    @Override
    public String toString() {
        return "Location: Abonné " + numAbonne + " - Cassette " + numCassette
                + " le " + dateLocation;
    }
}
