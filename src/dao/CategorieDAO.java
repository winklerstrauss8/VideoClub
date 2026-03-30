package dao;

import model.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CategorieDAO - Accès aux données de la table 'categorie'
 * Opérations CRUD : Créer, Lire, Modifier, Supprimer
 */
public class CategorieDAO {

    // Récupérer la connexion depuis notre singleton
    private Connection connexion = DatabaseConnection.getConnexion();

    /**
     * Ajouter une nouvelle catégorie dans la base
     */
    public boolean ajouter(Categorie categorie) {
        String sql = "INSERT INTO categorie (libelle_categorie) VALUES (?)";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, categorie.getLibelleCategorie());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0; // true si l'insertion a réussi
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la catégorie : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifier une catégorie existante
     */
    public boolean modifier(Categorie categorie) {
        String sql = "UPDATE categorie SET libelle_categorie = ? WHERE id_categorie = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, categorie.getLibelleCategorie());
            ps.setInt(2, categorie.getIdCategorie());
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la catégorie : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprimer une catégorie par son identifiant
     */
    public boolean supprimer(int idCategorie) {
        String sql = "DELETE FROM categorie WHERE id_categorie = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, idCategorie);
            int resultat = ps.executeUpdate();
            ps.close();
            return resultat > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la catégorie : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupérer une catégorie par son identifiant
     */
    public Categorie trouverParId(int idCategorie) {
        String sql = "SELECT * FROM categorie WHERE id_categorie = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setInt(1, idCategorie);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categorie cat = new Categorie();
                cat.setIdCategorie(rs.getInt("id_categorie"));
                cat.setLibelleCategorie(rs.getString("libelle_categorie"));
                rs.close();
                ps.close();
                return cat;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la catégorie : " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupérer toutes les catégories
     */
    public List<Categorie> listerTout() {
        List<Categorie> liste = new ArrayList<>();
        String sql = "SELECT * FROM categorie ORDER BY id_categorie";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // Parcourir les résultats et créer les objets Categorie
            while (rs.next()) {
                Categorie cat = new Categorie();
                cat.setIdCategorie(rs.getInt("id_categorie"));
                cat.setLibelleCategorie(rs.getString("libelle_categorie"));
                liste.add(cat);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des catégories : " + e.getMessage());
        }
        return liste;
    }
}
