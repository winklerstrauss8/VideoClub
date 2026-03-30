package ui;

import javax.swing.*;
import java.awt.*;

/**
 * HomePanel - Page d'accueil de l'application
 * Thème clair
 */
public class HomePanel extends JPanel {

    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);

    public HomePanel() {
        setLayout(new BorderLayout());
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBackground(COULEUR_FOND);

        // En-tête
        JLabel labelBienvenue = new JLabel("Bienvenue sur VideoClub !");
        labelBienvenue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        labelBienvenue.setForeground(COULEUR_TEXTE);
        labelBienvenue.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenu.add(labelBienvenue);
        contenu.add(Box.createVerticalStrut(10));

        JLabel labelSousTitre = new JLabel("Système de gestion du club de location de cassettes vidéo");
        labelSousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        labelSousTitre.setForeground(new Color(110, 120, 140));
        labelSousTitre.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenu.add(labelSousTitre);
        contenu.add(Box.createVerticalStrut(30));


        // Stats
        JPanel panneauStats = new JPanel(new GridLayout(1, 4, 15, 0));
        panneauStats.setBackground(COULEUR_FOND);
        panneauStats.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panneauStats.add(creerCarteStat("\uD83C\uDFAC", "Titres", "Gérer les films"));
        panneauStats.add(creerCarteStat("\uD83D\uDCFC", "Cassettes", "Inventaire"));
        panneauStats.add(creerCarteStat("\uD83D\uDC64", "Abonnés", "Membres du club"));
        panneauStats.add(creerCarteStat("\uD83D\uDCC5", "Locations", "En cours"));
        contenu.add(panneauStats);
        contenu.add(Box.createVerticalStrut(20));


        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COULEUR_FOND);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }


    private JPanel creerCarteStat(String icone, String titre, String description) {
        JPanel carte = new JPanel();
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBackground(COULEUR_CARTE);
        carte.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JLabel labelIcone = new JLabel(icone, SwingConstants.CENTER);
        labelIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        labelIcone.setAlignmentX(Component.CENTER_ALIGNMENT);
        carte.add(labelIcone);
        carte.add(Box.createVerticalStrut(8));
        JLabel labelTitre = new JLabel(titre, SwingConstants.CENTER);
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelTitre.setForeground(COULEUR_TEXTE);
        labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        carte.add(labelTitre);
        carte.add(Box.createVerticalStrut(4));
        JLabel labelDesc = new JLabel(description, SwingConstants.CENTER);
        labelDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelDesc.setForeground(new Color(110, 120, 140));
        labelDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        carte.add(labelDesc);
        return carte;
    }
}
