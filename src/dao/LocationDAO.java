package dao;

import model.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LocationDAO - Accès aux données de la table 'location_cassette'
 * Gère les locations et les retours de cassettes
 */
public class LocationDAO {

    private Connection connexion = DatabaseConnection.getConnexion();

    /**
     * Enregistrer une nouvelle location
     * Si une location existe déjà pour ce couple (abonné, cassette),
     * on met à jour la date (INSERT ... ON DUPLICATE KEY UPDATE)
     */
    public boolean ajouterLocation(Location location) {
        String sql = "INSERT INTO location_cassette (num_abonne, num_cassette, date_location) " +
                     "VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE date_location = VALUES(date_location)";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, location.getNumAbonne());
            ps.setInt(2, location.getNumCassette());
            ps.setString(3, location.getDateLocation());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la location : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprimer une location (= enregistrer un retour)
     */
    public boolean supprimerLocation(int numAbonne, int numCassette) {
        String sql = "DELETE FROM location_cassette WHERE num_abonne = ? AND num_cassette = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numAbonne);
            ps.setInt(2, numCassette);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la location : " + e.getMessage());
            return false;
        }
    }

    /**
     * Lister toutes les locations en cours avec les noms (jointures)
     */
    public List<Location> listerTout() {
        List<Location> liste = new ArrayList<>();
        String sql = "SELECT l.*, a.nom_abonne, t.nom_titre " +
                     "FROM location_cassette l " +
                     "JOIN abonne a ON l.num_abonne = a.num_abonne " +
                     "JOIN cassette c ON l.num_cassette = c.num_cassette " +
                     "JOIN titre t ON c.id_titre = t.id_titre " +
                     "ORDER BY l.date_location DESC";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Location loc = new Location();
                loc.setNumAbonne(rs.getInt("num_abonne"));
                loc.setNumCassette(rs.getInt("num_cassette"));
                loc.setDateLocation(rs.getString("date_location"));
                loc.setNomAbonne(rs.getString("nom_abonne"));
                loc.setNomTitre(rs.getString("nom_titre"));
                liste.add(loc);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des locations : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Lister les locations d'un abonné spécifique (pour les retours)
     */
    public List<Location> listerParAbonne(int numAbonne) {
        List<Location> liste = new ArrayList<>();
        String sql = "SELECT l.*, a.nom_abonne, t.nom_titre " +
                     "FROM location_cassette l " +
                     "JOIN abonne a ON l.num_abonne = a.num_abonne " +
                     "JOIN cassette c ON l.num_cassette = c.num_cassette " +
                     "JOIN titre t ON c.id_titre = t.id_titre " +
                     "WHERE l.num_abonne = ? " +
                     "ORDER BY l.date_location DESC";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numAbonne);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Location loc = new Location();
                loc.setNumAbonne(rs.getInt("num_abonne"));
                loc.setNumCassette(rs.getInt("num_cassette"));
                loc.setDateLocation(rs.getString("date_location"));
                loc.setNomAbonne(rs.getString("nom_abonne"));
                loc.setNomTitre(rs.getString("nom_titre"));
                liste.add(loc);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des locations de l'abonné : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Vérifier l'authentification d'un utilisateur
     * Retourne true si le nom d'utilisateur et mot de passe sont corrects
     */
    public boolean authentifier(String nomUtilisateur, String motDePasse) {
        String sql = "SELECT * FROM utilisateur WHERE nom_utilisateur = ? AND mot_de_passe = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, nomUtilisateur);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();
            boolean existe = rs.next();
            rs.close();
            ps.close();
            return existe;
        } catch (SQLException e) {
            System.err.println("Erreur d'authentification : " + e.getMessage());
            return false;
        }
    }
}
