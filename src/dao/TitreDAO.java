package dao;

import model.Titre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TitreDAO - Accès aux données de la table 'titre'
 * Gère les opérations CRUD pour les titres de films
 */
public class TitreDAO {

    private Connection connexion = DatabaseConnection.getConnexion();

    /**
     * Ajouter un nouveau titre de film
     */
    public boolean ajouter(Titre titre) {
        String sql = "INSERT INTO titre (nom_titre, auteur, duree, id_categorie) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, titre.getNomTitre());
            ps.setString(2, titre.getAuteur());
            ps.setInt(3, titre.getDuree());
            ps.setInt(4, titre.getIdCategorie());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du titre : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifier un titre existant
     */
    public boolean modifier(Titre titre) {
        String sql = "UPDATE titre SET nom_titre = ?, auteur = ?, duree = ?, id_categorie = ? WHERE id_titre = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, titre.getNomTitre());
            ps.setString(2, titre.getAuteur());
            ps.setInt(3, titre.getDuree());
            ps.setInt(4, titre.getIdCategorie());
            ps.setInt(5, titre.getIdTitre());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du titre : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprimer un titre par son identifiant
     */
    public boolean supprimer(int idTitre) {
        String sql = "DELETE FROM titre WHERE id_titre = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, idTitre);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du titre : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupérer un titre par son identifiant (avec jointure catégorie)
     */
    public Titre trouverParId(int idTitre) {
        String sql = "SELECT t.*, c.libelle_categorie FROM titre t " +
                     "JOIN categorie c ON t.id_categorie = c.id_categorie " +
                     "WHERE t.id_titre = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, idTitre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Titre titre = extraireTitre(rs);
                rs.close();
                ps.close();
                return titre;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du titre : " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupérer tous les titres avec le nom de la catégorie
     */
    public List<Titre> listerTout() {
        List<Titre> liste = new ArrayList<>();
        String sql = "SELECT t.*, c.libelle_categorie FROM titre t " +
                     "JOIN categorie c ON t.id_categorie = c.id_categorie " +
                     "ORDER BY t.id_titre";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                liste.add(extraireTitre(rs));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des titres : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Méthode utilitaire pour extraire un objet Titre depuis un ResultSet
     */
    private Titre extraireTitre(ResultSet rs) throws SQLException {
        Titre titre = new Titre();
        titre.setIdTitre(rs.getInt("id_titre"));
        titre.setNomTitre(rs.getString("nom_titre"));
        titre.setAuteur(rs.getString("auteur"));
        titre.setDuree(rs.getInt("duree"));
        titre.setIdCategorie(rs.getInt("id_categorie"));
        titre.setLibelleCategorie(rs.getString("libelle_categorie"));
        return titre;
    }
}
