
import ui.LoginFrame;
import javax.swing.*;

/**
 * Classe Main - Point d'entrée de l'application VideoClub
 * Lance la fenêtre de connexion
 */
public class Main {

    public static void main(String[] args) {
        // Utiliser le look and feel du système pour un meilleur rendu
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si le look and feel ne fonctionne pas, on continue avec le défaut
            System.out.println("Look and Feel par défaut utilisé.");
        }

        // Lancer l'interface graphique dans le thread Swing (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame fenetreLogin = new LoginFrame();
                fenetreLogin.setVisible(true);
            }
        });
    }
}
