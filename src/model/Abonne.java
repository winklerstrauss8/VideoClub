package model;

/**
 * Classe Abonne - Représente un abonné du club
 * Un abonné peut louer au maximum 3 cassettes à la fois
 */
public class Abonne {

    // Attributs
    private int numAbonne;            // Numéro unique de l'abonné
    private String nomAbonne;         // Nom et prénom de l'abonné
    private String adresseAbonne;     // Adresse postale
    private String dateAbonnement;    // Date de l'abonnement
    private String dateEntree;        // Date d'entrée au club
    private int nbLocation;           // Nombre de cassettes actuellement louées (max 3)

    // Constructeur vide
    public Abonne() {
    }

    // Constructeur complet
    public Abonne(int numAbonne, String nomAbonne, String adresseAbonne,
                  String dateAbonnement, String dateEntree, int nbLocation) {
        this.numAbonne = numAbonne;
        this.nomAbonne = nomAbonne;
        this.adresseAbonne = adresseAbonne;
        this.dateAbonnement = dateAbonnement;
        this.dateEntree = dateEntree;
        this.nbLocation = nbLocation;
    }

    // --- Getters et Setters ---

    public int getNumAbonne() {
        return numAbonne;
    }

    public void setNumAbonne(int numAbonne) {
        this.numAbonne = numAbonne;
    }

    public String getNomAbonne() {
        return nomAbonne;
    }

    public void setNomAbonne(String nomAbonne) {
        this.nomAbonne = nomAbonne;
    }

    public String getAdresseAbonne() {
        return adresseAbonne;
    }

    public void setAdresseAbonne(String adresseAbonne) {
        this.adresseAbonne = adresseAbonne;
    }

    public String getDateAbonnement() {
        return dateAbonnement;
    }

    public void setDateAbonnement(String dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }

    public String getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(String dateEntree) {
        this.dateEntree = dateEntree;
    }

    public int getNbLocation() {
        return nbLocation;
    }

    public void setNbLocation(int nbLocation) {
        this.nbLocation = nbLocation;
    }

    // Pour l'affichage dans les combobox
    @Override
    public String toString() {
        return numAbonne + " - " + nomAbonne;
    }
}
