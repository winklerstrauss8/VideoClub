package ui;

import dao.TitreDAO;
import dao.CategorieDAO;
import model.Titre;
import model.Categorie;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * TitrePanel - Gestion des titres de films (thème clair, boutons en bas)
 */
public class TitrePanel extends JPanel {

    private JTable tableau;
    private DefaultTableModel modeleTableau;
    private JTextField champNom, champAuteur, champDuree;
    private JComboBox<Categorie> comboCategorie;
    private TitreDAO titreDAO;
    private CategorieDAO categorieDAO;

    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_SUCCES = new Color(40, 180, 100);
    private static final Color COULEUR_DANGER = new Color(220, 55, 55);
    private static final Color COULEUR_MODIF = new Color(240, 160, 20);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);

    public TitrePanel() {
        titreDAO = new TitreDAO();
        categorieDAO = new CategorieDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Haut : titre + formulaire ---
        JPanel panneauNord = new JPanel();
        panneauNord.setLayout(new BoxLayout(panneauNord, BoxLayout.Y_AXIS));
        panneauNord.setBackground(COULEUR_FOND);

        JLabel labelTitre = new JLabel("\uD83C\uDFAC  Gestion des Titres");
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitre.setForeground(COULEUR_TEXTE);
        labelTitre.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauNord.add(labelTitre);
        panneauNord.add(Box.createVerticalStrut(10));

        // Formulaire horizontal
        JPanel panneauForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        panneauForm.setBackground(COULEUR_CARTE);
        panneauForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panneauForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        panneauForm.add(creerLabel("Nom :"));
        champNom = creerChamp(14);
        panneauForm.add(champNom);

        panneauForm.add(creerLabel("Auteur :"));
        champAuteur = creerChamp(12);
        panneauForm.add(champAuteur);

        panneauForm.add(creerLabel("Durée :"));
        champDuree = creerChamp(5);
        panneauForm.add(champDuree);

        panneauForm.add(creerLabel("Catégorie :"));
        comboCategorie = new JComboBox<>();
        comboCategorie.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboCategorie.setPreferredSize(new Dimension(140, 32));
        comboCategorie.setBackground(COULEUR_CHAMP);
        panneauForm.add(comboCategorie);

        panneauNord.add(panneauForm);
        add(panneauNord, BorderLayout.NORTH);

        // --- Tableau au centre ---
        String[] colonnes = {"ID", "Nom du Titre", "Auteur", "Durée (min)", "Catégorie"};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableau = new JTable(modeleTableau);
        configurerTableau(tableau);

        JScrollPane scroll = new JScrollPane(tableau);
        scroll.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE, 1));
        scroll.getViewport().setBackground(COULEUR_CARTE);
        add(scroll, BorderLayout.CENTER);

        // --- Boutons en bas ---
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
            if (!validerFormulaire()) return;
            Titre t = new Titre();
            t.setNomTitre(champNom.getText().trim());
            t.setAuteur(champAuteur.getText().trim());
            t.setDuree(Integer.parseInt(champDuree.getText().trim()));
            t.setIdCategorie(((Categorie) comboCategorie.getSelectedItem()).getIdCategorie());
            if (titreDAO.ajouter(t)) {
                JOptionPane.showMessageDialog(this, "Titre ajouté !");
                viderFormulaire(); chargerDonnees();
            }
        });

        btnModifier.addActionListener(e -> {
            int l = tableau.getSelectedRow();
            if (l == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez un titre !"); return; }
            if (!validerFormulaire()) return;
            int id = (int) modeleTableau.getValueAt(l, 0);
            Titre t = new Titre();
            t.setIdTitre(id);
            t.setNomTitre(champNom.getText().trim());
            t.setAuteur(champAuteur.getText().trim());
            t.setDuree(Integer.parseInt(champDuree.getText().trim()));
            t.setIdCategorie(((Categorie) comboCategorie.getSelectedItem()).getIdCategorie());
            if (titreDAO.modifier(t)) {
                JOptionPane.showMessageDialog(this, "Titre modifié !");
                viderFormulaire(); chargerDonnees();
            }
        });

        btnSupprimer.addActionListener(e -> {
            int l = tableau.getSelectedRow();
            if (l == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez un titre !"); return; }
            int id = (int) modeleTableau.getValueAt(l, 0);
            int choix = JOptionPane.showConfirmDialog(this, "Supprimer ce titre ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                if (titreDAO.supprimer(id)) {
                    JOptionPane.showMessageDialog(this, "Titre supprimé !");
                    viderFormulaire(); chargerDonnees();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible : des cassettes existent pour ce titre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVider.addActionListener(e -> viderFormulaire());

        tableau.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int l = tableau.getSelectedRow();
                if (l >= 0) {
                    champNom.setText((String) modeleTableau.getValueAt(l, 1));
                    champAuteur.setText((String) modeleTableau.getValueAt(l, 2));
                    champDuree.setText(String.valueOf(modeleTableau.getValueAt(l, 3)));
                    String catName = (String) modeleTableau.getValueAt(l, 4);
                    for (int i = 0; i < comboCategorie.getItemCount(); i++) {
                        if (comboCategorie.getItemAt(i).getLibelleCategorie().equals(catName)) {
                            comboCategorie.setSelectedIndex(i); break;
                        }
                    }
                }
            }
        });

        chargerCategories();
        chargerDonnees();
    }

    private boolean validerFormulaire() {
        if (champNom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire !"); return false;
        }
        try { Integer.parseInt(champDuree.getText().trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La durée doit être un nombre !"); return false;
        }
        if (comboCategorie.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une catégorie !"); return false;
        }
        return true;
    }

    private void chargerCategories() {
        comboCategorie.removeAllItems();
        for (Categorie c : categorieDAO.listerTout()) comboCategorie.addItem(c);
    }

    private void chargerDonnees() {
        modeleTableau.setRowCount(0);
        for (Titre t : titreDAO.listerTout()) {
            modeleTableau.addRow(new Object[]{ t.getIdTitre(), t.getNomTitre(), t.getAuteur(), t.getDuree(), t.getLibelleCategorie() });
        }
    }

    private void viderFormulaire() {
        champNom.setText(""); champAuteur.setText(""); champDuree.setText("");
        if (comboCategorie.getItemCount() > 0) comboCategorie.setSelectedIndex(0);
        tableau.clearSelection();
    }

    private JLabel creerLabel(String texte) {
        JLabel l = new JLabel(texte);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(COULEUR_TEXTE);
        return l;
    }
    private JTextField creerChamp(int cols) {
        JTextField c = new JTextField(cols);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(cols * 10, 32));
        c.setBackground(COULEUR_CHAMP);
        c.setForeground(COULEUR_TEXTE);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return c;
    }
    private JButton creerBouton(String txt, Color clr, String iconePath) {
        JButton b;
        if (iconePath != null && new java.io.File(iconePath).exists()) {
            b = new JButton(txt, new ImageIcon(iconePath));
        } else {
            b = new JButton(txt);
        }
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE); b.setBackground(clr);
        b.setPreferredSize(new Dimension(130, 36));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Color h = clr.brighter();
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(h); }
            public void mouseExited(MouseEvent e) { b.setBackground(clr); }
        });
        return b;
    }
    private void configurerTableau(JTable t) {
        t.setBackground(COULEUR_CARTE); t.setForeground(COULEUR_TEXTE);
        t.setSelectionBackground(new Color(210,225,250)); t.setSelectionForeground(COULEUR_TEXTE);
        t.setGridColor(COULEUR_BORDURE); t.setRowHeight(32);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTableHeader h = t.getTableHeader();
        h.setBackground(new Color(245,247,252)); h.setForeground(COULEUR_TEXTE);
        h.setFont(new Font("Segoe UI", Font.BOLD, 13));
        h.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE));
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < t.getColumnCount(); i++) t.getColumnModel().getColumn(i).setCellRenderer(cr);
    }
}
