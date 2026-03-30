package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame - Fenêtre principale de l'application
 * Thème clair avec sidebar et CardLayout
 */
public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel panneauContenu;

    // Couleurs du thème clair
    private static final Color COULEUR_SIDEBAR = new Color(245, 247, 252);
    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_BOUTON = new Color(240, 242, 248);
    private static final Color COULEUR_BOUTON_HOVER = new Color(220, 228, 245);
    private static final Color COULEUR_BOUTON_ACTIF = new Color(55, 120, 250);
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_TEXTE_ACTIF = Color.WHITE;
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);

    private JButton boutonActif = null;

    public MainFrame() {
        setTitle("VideoClub - Gestion du Club de Location");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        setLayout(new BorderLayout());

        // --- Sidebar ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COULEUR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COULEUR_BORDURE));

        // Logo
        JPanel panneauLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panneauLogo.setBackground(COULEUR_SIDEBAR);
        panneauLogo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel labelLogo = new JLabel("\uD83C\uDFAC VideoClub");
        labelLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelLogo.setForeground(COULEUR_TEXTE);
        panneauLogo.add(labelLogo);
        sidebar.add(panneauLogo);
        sidebar.add(Box.createVerticalStrut(10));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(COULEUR_BORDURE);
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));

        // Boutons de navigation
        JButton btnAccueil     = creerBoutonNav("  Accueil", "accueil", "icons/home.png");
        JButton btnCategories  = creerBoutonNav("  Catégories", "categories", "icons/categories.png");
        JButton btnTitres      = creerBoutonNav("\uD83C\uDFAC  Titres", "titres", null);
        JButton btnCassettes   = creerBoutonNav("\uD83D\uDCFC  Cassettes", "cassettes", null);
        JButton btnAbonnes     = creerBoutonNav("\uD83D\uDC64  Abonnés", "abonnes", null);
        JButton btnLocation    = creerBoutonNav("\uD83D\uDCC5  Location", "location", null);
        JButton btnRetour      = creerBoutonNav("\u21A9  Retour", "retour", null);
        JButton btnContact     = creerBoutonNav("\u2709  Contact", "contact", null);

        sidebar.add(btnAccueil);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnCategories);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnTitres);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnCassettes);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnAbonnes);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnLocation);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnRetour);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnContact);

        sidebar.add(Box.createVerticalGlue());

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setForeground(COULEUR_BORDURE);
        sidebar.add(sep2);
        sidebar.add(Box.createVerticalStrut(5));

        JButton btnDeconnexion = creerBoutonNav("\u26A0  Déconnexion", "deconnexion", "icons/logout.png");
        btnDeconnexion.setForeground(new Color(220, 60, 60));
        sidebar.add(btnDeconnexion);
        sidebar.add(Box.createVerticalStrut(10));

        add(sidebar, BorderLayout.WEST);

        // --- Contenu central ---
        cardLayout = new CardLayout();
        panneauContenu = new JPanel(cardLayout);
        panneauContenu.setBackground(COULEUR_FOND);

        panneauContenu.add(new HomePanel(), "accueil");
        panneauContenu.add(new CategoriePanel(), "categories");
        panneauContenu.add(new TitrePanel(), "titres");
        panneauContenu.add(new CassettePanel(), "cassettes");
        panneauContenu.add(new AbonnePanel(), "abonnes");
        panneauContenu.add(new LocationPanel(), "location");
        panneauContenu.add(new RetourPanel(), "retour");
        panneauContenu.add(new ContactPanel(), "contact");

        add(panneauContenu, BorderLayout.CENTER);

        cardLayout.show(panneauContenu, "accueil");
        activerBouton(btnAccueil);
    }

    private JButton creerBoutonNav(String texte, String nomPage, String iconePath) {
        JButton bouton;
        if (iconePath != null && new java.io.File(iconePath).exists()) {
            bouton = new JButton(texte, new ImageIcon(iconePath));
        } else {
            bouton = new JButton(texte);
        }
        bouton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        bouton.setForeground(COULEUR_TEXTE);
        bouton.setBackground(COULEUR_BOUTON);
        bouton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setHorizontalAlignment(SwingConstants.LEFT);
        bouton.setBorderPainted(false);
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 10));

        bouton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (bouton != boutonActif) {
                    bouton.setBackground(COULEUR_BOUTON_HOVER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (bouton != boutonActif) {
                    bouton.setBackground(COULEUR_BOUTON);
                }
            }
        });

        bouton.addActionListener(e -> {
            if (nomPage.equals("deconnexion")) {
                int choix = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Voulez-vous vraiment vous déconnecter ?",
                    "Déconnexion",
                    JOptionPane.YES_NO_OPTION
                );
                if (choix == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginFrame().setVisible(true);
                }
            } else {
                cardLayout.show(panneauContenu, nomPage);
                activerBouton(bouton);
            }
        });

        return bouton;
    }

    private void activerBouton(JButton bouton) {
        if (boutonActif != null) {
            boutonActif.setBackground(COULEUR_BOUTON);
            boutonActif.setForeground(COULEUR_TEXTE);
        }
        boutonActif = bouton;
        boutonActif.setBackground(COULEUR_BOUTON_ACTIF);
        boutonActif.setForeground(COULEUR_TEXTE_ACTIF);
    }
}
