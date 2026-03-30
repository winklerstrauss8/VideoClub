package ui;

import dao.LocationDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginFrame - Fenêtre d'authentification
 * Thème clair, agrandie et redimensionnable
 */
public class LoginFrame extends JFrame {

    // Composants de l'interface
    private JTextField champUtilisateur;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JLabel labelErreur;

    // Couleurs du thème clair
    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_PANNEAU = Color.WHITE;
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_TEXTE_SEC = new Color(110, 120, 140);
    private static final Color COULEUR_ERREUR = new Color(220, 50, 50);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);

    public LoginFrame() {
        // Configuration de la fenêtre (agrandie et redimensionnable)
        setTitle("VideoClub - Connexion");
        setSize(600, 700);
        setMinimumSize(new Dimension(450, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true); // ← Possibilité d'agrandir et réduire

        // Panneau principal avec fond clair
        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new GridBagLayout());
        panneauPrincipal.setBackground(COULEUR_FOND);

        // Panneau du formulaire (carte centrale)
        JPanel panneauFormulaire = new JPanel();
        panneauFormulaire.setLayout(new BoxLayout(panneauFormulaire, BoxLayout.Y_AXIS));
        panneauFormulaire.setBackground(COULEUR_PANNEAU);
        panneauFormulaire.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(50, 50, 50, 50)
        ));
        panneauFormulaire.setPreferredSize(new Dimension(420, 500));

        // Icône / Logo
        JLabel labelIcone = new JLabel("\uD83C\uDFAC", SwingConstants.CENTER);
        labelIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
        labelIcone.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauFormulaire.add(labelIcone);
        panneauFormulaire.add(Box.createVerticalStrut(10));

        // Titre
        JLabel labelTitre = new JLabel("VideoClub", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 32));
        labelTitre.setForeground(COULEUR_TEXTE);
        labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauFormulaire.add(labelTitre);

        // Sous-titre
        JLabel labelSousTitre = new JLabel("Connectez-vous pour continuer", SwingConstants.CENTER);
        labelSousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelSousTitre.setForeground(COULEUR_TEXTE_SEC);
        labelSousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panneauFormulaire.add(labelSousTitre);
        panneauFormulaire.add(Box.createVerticalStrut(35));

        // Label "Nom d'utilisateur"
        JLabel labelUser = new JLabel("Nom d'utilisateur");
        labelUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelUser.setForeground(COULEUR_TEXTE);
        labelUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauFormulaire.add(labelUser);
        panneauFormulaire.add(Box.createVerticalStrut(6));

        // Champ de saisie utilisateur
        champUtilisateur = new JTextField();
        champUtilisateur.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        champUtilisateur.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        champUtilisateur.setAlignmentX(Component.LEFT_ALIGNMENT);
        champUtilisateur.setBackground(COULEUR_CHAMP);
        champUtilisateur.setForeground(COULEUR_TEXTE);
        champUtilisateur.setCaretColor(COULEUR_TEXTE);
        champUtilisateur.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panneauFormulaire.add(champUtilisateur);
        panneauFormulaire.add(Box.createVerticalStrut(18));

        // Label "Mot de passe"
        JLabel labelMdp = new JLabel("Mot de passe");
        labelMdp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelMdp.setForeground(COULEUR_TEXTE);
        labelMdp.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauFormulaire.add(labelMdp);
        panneauFormulaire.add(Box.createVerticalStrut(6));

        // Champ de saisie mot de passe
        champMotDePasse = new JPasswordField();
        champMotDePasse.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        champMotDePasse.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        champMotDePasse.setAlignmentX(Component.LEFT_ALIGNMENT);
        champMotDePasse.setBackground(COULEUR_CHAMP);
        champMotDePasse.setForeground(COULEUR_TEXTE);
        champMotDePasse.setCaretColor(COULEUR_TEXTE);
        champMotDePasse.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panneauFormulaire.add(champMotDePasse);
        panneauFormulaire.add(Box.createVerticalStrut(22));

        // Label d'erreur (caché par défaut)
        labelErreur = new JLabel("", SwingConstants.CENTER);
        labelErreur.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelErreur.setForeground(COULEUR_ERREUR);
        labelErreur.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelErreur.setVisible(false);
        panneauFormulaire.add(labelErreur);
        panneauFormulaire.add(Box.createVerticalStrut(10));

        // Bouton de connexion
        boutonConnexion = new JButton("Se connecter");
        boutonConnexion.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boutonConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonConnexion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        boutonConnexion.setBackground(COULEUR_ACCENT);
        boutonConnexion.setForeground(Color.WHITE);
        boutonConnexion.setFocusPainted(false);
        boutonConnexion.setBorderPainted(false);
        boutonConnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panneauFormulaire.add(boutonConnexion);

        // Ajouter le formulaire au panneau principal
        panneauPrincipal.add(panneauFormulaire);
        add(panneauPrincipal);

        // --- Événements ---

        boutonConnexion.addActionListener(e -> tenterConnexion());

        champMotDePasse.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tenterConnexion();
                }
            }
        });

        boutonConnexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boutonConnexion.setBackground(new Color(40, 100, 220));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boutonConnexion.setBackground(COULEUR_ACCENT);
            }
        });
    }

    /**
     * Tenter la connexion avec les identifiants saisis
     */
    private void tenterConnexion() {
        String utilisateur = champUtilisateur.getText().trim();
        String motDePasse = new String(champMotDePasse.getPassword());

        if (utilisateur.isEmpty() || motDePasse.isEmpty()) {
            labelErreur.setText("Veuillez remplir tous les champs !");
            labelErreur.setVisible(true);
            return;
        }

        LocationDAO locationDAO = new LocationDAO();
        if (locationDAO.authentifier(utilisateur, motDePasse)) {
            dispose();
            new MainFrame().setVisible(true);
        } else {
            labelErreur.setText("Nom d'utilisateur ou mot de passe incorrect !");
            labelErreur.setVisible(true);
            champMotDePasse.setText("");
        }
    }
}
