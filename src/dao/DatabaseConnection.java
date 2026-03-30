package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe DatabaseConnection - Gère la connexion à la base de données MySQL
 * Utilise le pattern Singleton pour n'avoir qu'une seule connexion
 */
public class DatabaseConnection {

    // Paramètres de connexion (XAMPP par défaut)
    private static final String URL = "jdbc:mysql://localhost:3306/videoclub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String UTILISATEUR = "root";   // Utilisateur MySQL par défaut sous XAMPP
    private static final String MOT_DE_PASSE = "";      // Pas de mot de passe par défaut sous XAMPP

    // Instance unique de la connexion (Singleton)
    private static Connection connexion = null;

    /**
     * Obtenir la connexion à la base de données
     * Si la connexion n'existe pas ou est fermée, on en crée une nouvelle
     */
    public static Connection getConnexion() {
        try {
            // Vérifier si la connexion existe et est encore valide
            if (connexion == null || connexion.isClosed()) {
                // Charger le driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Créer la connexion
                connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
                System.out.println("Connexion à la base de données réussie !");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver MySQL introuvable !");
            System.err.println("Vérifiez que mysql-connector-j.jar est dans le classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données !");
            System.err.println("Vérifiez que MySQL (XAMPP) est bien démarré.");
            e.printStackTrace();
        }
        return connexion;
    }

    /**
     * Fermer la connexion à la base de données
     */
    public static void fermerConnexion() {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
                System.out.println("Connexion à la base de données fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion.");
            e.printStackTrace();
        }
    }
}
