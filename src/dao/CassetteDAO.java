package dao;

import model.Cassette;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CassetteDAO - Accès aux données de la table 'cassette'
 * Gère les opérations CRUD pour les cassettes vidéo
 */
public class CassetteDAO {

    private Connection connexion = DatabaseConnection.getConnexion();

    /**
     * Ajouter une nouvelle cassette
     */
    public boolean ajouter(Cassette cassette) {
        String sql = "INSERT INTO cassette (date_achat, prix, id_titre) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, cassette.getDateAchat());
            ps.setDouble(2, cassette.getPrix());
            ps.setInt(3, cassette.getIdTitre());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la cassette : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifier une cassette existante
     */
    public boolean modifier(Cassette cassette) {
        String sql = "UPDATE cassette SET date_achat = ?, prix = ?, id_titre = ? WHERE num_cassette = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, cassette.getDateAchat());
            ps.setDouble(2, cassette.getPrix());
            ps.setInt(3, cassette.getIdTitre());
            ps.setInt(4, cassette.getNumCassette());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la cassette : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprimer une cassette par son numéro
     */
    public boolean supprimer(int numCassette) {
        String sql = "DELETE FROM cassette WHERE num_cassette = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numCassette);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la cassette : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupérer toutes les cassettes avec le nom du titre
     */
    public List<Cassette> listerTout() {
        List<Cassette> liste = new ArrayList<>();
        String sql = "SELECT c.*, t.nom_titre FROM cassette c " +
                     "JOIN titre t ON c.id_titre = t.id_titre " +
                     "ORDER BY c.num_cassette";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                liste.add(extraireCassette(rs));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cassettes : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Récupérer les cassettes disponibles (non louées)
     * Une cassette est disponible si elle n'apparaît pas dans la table location_cassette
     */
    public List<Cassette> listerDisponibles() {
        List<Cassette> liste = new ArrayList<>();
        String sql = "SELECT c.*, t.nom_titre FROM cassette c " +
                     "JOIN titre t ON c.id_titre = t.id_titre " +
                     "WHERE c.num_cassette NOT IN (SELECT num_cassette FROM location_cassette) " +
                     "ORDER BY c.num_cassette";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                liste.add(extraireCassette(rs));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cassettes disponibles : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Méthode utilitaire pour extraire un objet Cassette depuis un ResultSet
     */
    private Cassette extraireCassette(ResultSet rs) throws SQLException {
        Cassette cass = new Cassette();
        cass.setNumCassette(rs.getInt("num_cassette"));
        cass.setDateAchat(rs.getString("date_achat"));
        cass.setPrix(rs.getDouble("prix"));
        cass.setIdTitre(rs.getInt("id_titre"));
        cass.setNomTitre(rs.getString("nom_titre"));
        return cass;
    }
}
