package ui;

import dao.CategorieDAO;
import model.Categorie;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * CategoriePanel - Gestion des catégories (thème clair, boutons en bas)
 */
public class CategoriePanel extends JPanel {

    private JTable tableau;
    private DefaultTableModel modeleTableau;
    private JTextField champLibelle;
    private CategorieDAO categorieDAO;

    // Couleurs thème clair
    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_SUCCES = new Color(40, 180, 100);
    private static final Color COULEUR_DANGER = new Color(220, 55, 55);
    private static final Color COULEUR_MODIF = new Color(240, 160, 20);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);

    public CategoriePanel() {
        categorieDAO = new CategorieDAO();
        setLayout(new BorderLayout(15, 15));
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En-tête
        JLabel labelTitre = new JLabel("\u2630  Gestion des Catégories");
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitre.setForeground(COULEUR_TEXTE);
        add(labelTitre, BorderLayout.NORTH);

        // --- Formulaire en haut ---
        JPanel panneauFormulaire = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panneauFormulaire.setBackground(COULEUR_CARTE);
        panneauFormulaire.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel labelLib = new JLabel("Libellé :");
        labelLib.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelLib.setForeground(COULEUR_TEXTE);
        panneauFormulaire.add(labelLib);

        champLibelle = new JTextField(30);
        champLibelle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        champLibelle.setPreferredSize(new Dimension(300, 34));
        champLibelle.setBackground(COULEUR_CHAMP);
        champLibelle.setForeground(COULEUR_TEXTE);
        champLibelle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        panneauFormulaire.add(champLibelle);

        // Panneau contenant formulaire en haut
        JPanel panneauHaut = new JPanel(new BorderLayout());
        panneauHaut.setBackground(COULEUR_FOND);
        panneauHaut.add(panneauFormulaire, BorderLayout.CENTER);

        // Panneau combiné haut (titre déjà en NORTH, on met form dans un wrapper)
        JPanel panneauNord = new JPanel();
        panneauNord.setLayout(new BoxLayout(panneauNord, BoxLayout.Y_AXIS));
        panneauNord.setBackground(COULEUR_FOND);

        JLabel labelTitre2 = new JLabel("\u2630  Gestion des Catégories");
        labelTitre2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitre2.setForeground(COULEUR_TEXTE);
        labelTitre2.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauNord.add(labelTitre2);
        panneauNord.add(Box.createVerticalStrut(10));
        panneauFormulaire.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauFormulaire.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panneauNord.add(panneauFormulaire);

        // Supprimer l'ancien NORTH et mettre le nouveau
        remove(labelTitre);
        add(panneauNord, BorderLayout.NORTH);

        // --- Tableau au centre ---
        String[] colonnes = {"ID", "Libellé Catégorie"};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableau = new JTable(modeleTableau);
        configurerTableau(tableau);

        JScrollPane scrollTableau = new JScrollPane(tableau);
        scrollTableau.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE, 1));
        scrollTableau.getViewport().setBackground(COULEUR_CARTE);
        add(scrollTableau, BorderLayout.CENTER);

        // --- Boutons en bas du tableau ---
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panneauBoutons.setBackground(COULEUR_FOND);

        JButton btnAjouter = creerBouton("Ajouter", COULEUR_SUCCES, "icons/add.png");
        JButton btnModifier = creerBouton("Modifier", COULEUR_MODIF, "icons/edit.png");
        JButton btnSupprimer = creerBouton("Supprimer", COULEUR_DANGER, "icons/delete.png");
        JButton btnVider = creerBouton("Vider", new Color(140, 150, 170), "icons/clear.png");

        panneauBoutons.add(btnAjouter);
        panneauBoutons.add(btnModifier);
        panneauBoutons.add(btnSupprimer);
        panneauBoutons.add(btnVider);
        add(panneauBoutons, BorderLayout.SOUTH);

        // --- Événements ---

        btnAjouter.addActionListener(e -> {
            String libelle = champLibelle.getText().trim();
            if (libelle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un libellé !", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Categorie cat = new Categorie();
            cat.setLibelleCategorie(libelle);
            if (categorieDAO.ajouter(cat)) {
                JOptionPane.showMessageDialog(this, "Catégorie ajoutée avec succès !");
                viderFormulaire();
                chargerDonnees();
            }
        });

        btnModifier.addActionListener(e -> {
            int ligneSel = tableau.getSelectedRow();
            if (ligneSel == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez une catégorie !", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String libelle = champLibelle.getText().trim();
            if (libelle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un libellé !", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeleTableau.getValueAt(ligneSel, 0);
            Categorie cat = new Categorie(id, libelle);
            if (categorieDAO.modifier(cat)) {
                JOptionPane.showMessageDialog(this, "Catégorie modifiée !");
                viderFormulaire();
                chargerDonnees();
            }
        });

        btnSupprimer.addActionListener(e -> {
            int ligneSel = tableau.getSelectedRow();
            if (ligneSel == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez une catégorie !", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeleTableau.getValueAt(ligneSel, 0);
            int choix = JOptionPane.showConfirmDialog(this, "Supprimer cette catégorie ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                if (categorieDAO.supprimer(id)) {
                    JOptionPane.showMessageDialog(this, "Catégorie supprimée !");
                    viderFormulaire();
                    chargerDonnees();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible : catégorie utilisée par des titres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVider.addActionListener(e -> viderFormulaire());

        tableau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int ligne = tableau.getSelectedRow();
                if (ligne >= 0) {
                    champLibelle.setText((String) modeleTableau.getValueAt(ligne, 1));
                }
            }
        });

        chargerDonnees();
    }

    private void chargerDonnees() {
        modeleTableau.setRowCount(0);
        List<Categorie> liste = categorieDAO.listerTout();
        for (Categorie cat : liste) {
            modeleTableau.addRow(new Object[]{ cat.getIdCategorie(), cat.getLibelleCategorie() });
        }
    }

    private void viderFormulaire() {
        champLibelle.setText("");
        tableau.clearSelection();
    }

    private JButton creerBouton(String texte, Color couleur, String iconePath) {
        JButton btn;
        if (iconePath != null && new java.io.File(iconePath).exists()) {
            btn = new JButton(texte, new ImageIcon(iconePath));
        } else {
            btn = new JButton(texte);
        }
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(couleur);
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Color hover = couleur.brighter();
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(couleur); }
        });
        return btn;
    }

    private void configurerTableau(JTable t) {
        t.setBackground(COULEUR_CARTE);
        t.setForeground(COULEUR_TEXTE);
        t.setSelectionBackground(new Color(210, 225, 250));
        t.setSelectionForeground(COULEUR_TEXTE);
        t.setGridColor(COULEUR_BORDURE);
        t.setRowHeight(32);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setShowGrid(true);
        JTableHeader h = t.getTableHeader();
        h.setBackground(new Color(245, 247, 252));
        h.setForeground(COULEUR_TEXTE);
        h.setFont(new Font("Segoe UI", Font.BOLD, 13));
        h.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE));
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < t.getColumnCount(); i++) t.getColumnModel().getColumn(i).setCellRenderer(cr);
    }
}
