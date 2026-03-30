package dao;

import model.Abonne;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AbonneDAO - Accès aux données de la table 'abonne'
 * Gère les opérations CRUD pour les abonnés du club
 */
public class AbonneDAO {

    private Connection connexion = DatabaseConnection.getConnexion();

    /**
     * Ajouter un nouvel abonné
     */
    public boolean ajouter(Abonne abonne) {
        String sql = "INSERT INTO abonne (nom_abonne, adresse_abonne, date_abonnement, date_entree, nb_location) " +
                     "VALUES (?, ?, ?, ?, 0)";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, abonne.getNomAbonne());
            ps.setString(2, abonne.getAdresseAbonne());
            ps.setString(3, abonne.getDateAbonnement());
            ps.setString(4, abonne.getDateEntree());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'abonné : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifier un abonné existant
     */
    public boolean modifier(Abonne abonne) {
        String sql = "UPDATE abonne SET nom_abonne = ?, adresse_abonne = ?, " +
                     "date_abonnement = ?, date_entree = ? WHERE num_abonne = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, abonne.getNomAbonne());
            ps.setString(2, abonne.getAdresseAbonne());
            ps.setString(3, abonne.getDateAbonnement());
            ps.setString(4, abonne.getDateEntree());
            ps.setInt(5, abonne.getNumAbonne());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'abonné : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprimer un abonné par son numéro
     */
    public boolean supprimer(int numAbonne) {
        String sql = "DELETE FROM abonne WHERE num_abonne = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numAbonne);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'abonné : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupérer un abonné par son numéro
     */
    public Abonne trouverParId(int numAbonne) {
        String sql = "SELECT * FROM abonne WHERE num_abonne = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numAbonne);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Abonne ab = extraireAbonne(rs);
                rs.close();
                ps.close();
                return ab;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'abonné : " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupérer tous les abonnés
     */
    public List<Abonne> listerTout() {
        List<Abonne> liste = new ArrayList<>();
        String sql = "SELECT * FROM abonne ORDER BY num_abonne";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                liste.add(extraireAbonne(rs));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des abonnés : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Incrémenter le nombre de locations d'un abonné (+1)
     * Appelé lors d'une nouvelle location
     */
    public boolean incrementerNbLocation(int numAbonne) {
        String sql = "UPDATE abonne SET nb_location = nb_location + 1 WHERE num_abonne = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numAbonne);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'incrémentation de nb_location : " + e.getMessage());
            return false;
        }
    }

    /**
     * Décrémenter le nombre de locations d'un abonné (-1)
     * Appelé lors d'un retour de cassette
     */
    public boolean decrementerNbLocation(int numAbonne) {
        String sql = "UPDATE abonne SET nb_location = nb_location - 1 WHERE num_abonne = ? AND nb_location > 0";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, numAbonne);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la décrémentation de nb_location : " + e.getMessage());
            return false;
        }
    }

    /**
     * Méthode utilitaire pour extraire un objet Abonne depuis un ResultSet
     */
    private Abonne extraireAbonne(ResultSet rs) throws SQLException {
        Abonne ab = new Abonne();
        ab.setNumAbonne(rs.getInt("num_abonne"));
        ab.setNomAbonne(rs.getString("nom_abonne"));
        ab.setAdresseAbonne(rs.getString("adresse_abonne"));
        ab.setDateAbonnement(rs.getString("date_abonnement"));
        ab.setDateEntree(rs.getString("date_entree"));
        ab.setNbLocation(rs.getInt("nb_location"));
        return ab;
    }
}
