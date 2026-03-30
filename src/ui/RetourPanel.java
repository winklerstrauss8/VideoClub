package ui;

import dao.LocationDAO;
import dao.AbonneDAO;
import model.Location;
import model.Abonne;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * RetourPanel - Enregistrement des retours (thème clair, boutons en bas)
 */
public class RetourPanel extends JPanel {

    private JTable tableauLocations;
    private DefaultTableModel modeleTableau;
    private JComboBox<Abonne> comboAbonne;
    private JLabel labelInfo;
    private LocationDAO locationDAO;
    private AbonneDAO abonneDAO;

    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_DANGER = new Color(220, 55, 55);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);

    public RetourPanel() {
        locationDAO = new LocationDAO();
        abonneDAO = new AbonneDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Haut
        JPanel panneauNord = new JPanel();
        panneauNord.setLayout(new BoxLayout(panneauNord, BoxLayout.Y_AXIS));
        panneauNord.setBackground(COULEUR_FOND);

        JLabel labelTitre = new JLabel("\u21A9  Enregistrement des Retours");
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitre.setForeground(COULEUR_TEXTE);
        labelTitre.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauNord.add(labelTitre);
        panneauNord.add(Box.createVerticalStrut(10));

        JPanel panneauForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        panneauForm.setBackground(COULEUR_CARTE);
        panneauForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panneauForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        panneauForm.add(creerLabel("Abonné :"));
        comboAbonne = new JComboBox<>();
        comboAbonne.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboAbonne.setPreferredSize(new Dimension(220, 32));
        comboAbonne.setBackground(COULEUR_CHAMP);
        panneauForm.add(comboAbonne);

        labelInfo = new JLabel("");
        labelInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        labelInfo.setForeground(new Color(110, 120, 140));
        panneauForm.add(Box.createHorizontalStrut(20));
        panneauForm.add(labelInfo);

        panneauNord.add(panneauForm);
        add(panneauNord, BorderLayout.NORTH);

        // Tableau
        String[] colonnes = {"N° Cassette", "Titre du Film", "Date Location"};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableauLocations = new JTable(modeleTableau);
        configurerTableau(tableauLocations);
        JScrollPane scroll = new JScrollPane(tableauLocations);
        scroll.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE, 1));
        scroll.getViewport().setBackground(COULEUR_CARTE);
        add(scroll, BorderLayout.CENTER);

        // Boutons en bas
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panneauBoutons.setBackground(COULEUR_FOND);

        JButton btnAfficher = creerBouton("Afficher ses locations", COULEUR_ACCENT, "icons/categories.png");
        JButton btnRetourner = creerBouton("Retourner la cassette", COULEUR_DANGER, "icons/delete.png");
        JButton btnRafraichir = creerBouton("Rafraîchir", new Color(140, 150, 170), "icons/refresh.png");

        panneauBoutons.add(btnAfficher);
        panneauBoutons.add(btnRetourner);
        panneauBoutons.add(btnRafraichir);
        add(panneauBoutons, BorderLayout.SOUTH);

        // Événements
        btnAfficher.addActionListener(e -> {
            if (comboAbonne.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un abonné !"); return;
            }
            Abonne abonne = (Abonne) comboAbonne.getSelectedItem();
            chargerLocationsAbonne(abonne.getNumAbonne());
        });

        btnRetourner.addActionListener(e -> {
            int ligne = tableauLocations.getSelectedRow();
            if (ligne == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez une cassette à retourner !"); return;
            }
            if (comboAbonne.getSelectedItem() == null) return;
            Abonne abonne = (Abonne) comboAbonne.getSelectedItem();
            int numCassette = (int) modeleTableau.getValueAt(ligne, 0);
            String titre = (String) modeleTableau.getValueAt(ligne, 1);
            int choix = JOptionPane.showConfirmDialog(this,
                "Retour de la cassette n°" + numCassette + " - " + titre + "\nPar : " + abonne.getNomAbonne(),
                "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                if (locationDAO.supprimerLocation(abonne.getNumAbonne(), numCassette)) {
                    abonneDAO.decrementerNbLocation(abonne.getNumAbonne());
                    JOptionPane.showMessageDialog(this, "Retour enregistré ! Cassette n°" + numCassette + " disponible.");
                    chargerLocationsAbonne(abonne.getNumAbonne());
                    chargerAbonnes();
                }
            }
        });

        btnRafraichir.addActionListener(e -> {
            chargerAbonnes();
            modeleTableau.setRowCount(0);
            labelInfo.setText("");
        });

        chargerAbonnes();
    }

    private void chargerAbonnes() {
        comboAbonne.removeAllItems();
        for (Abonne ab : abonneDAO.listerTout()) comboAbonne.addItem(ab);
    }

    private void chargerLocationsAbonne(int numAbonne) {
        modeleTableau.setRowCount(0);
        List<Location> locations = locationDAO.listerParAbonne(numAbonne);
        for (Location loc : locations) {
            modeleTableau.addRow(new Object[]{ loc.getNumCassette(), loc.getNomTitre(), loc.getDateLocation() });
        }
        labelInfo.setText(locations.size() + " cassette(s) en location");
    }

    private JLabel creerLabel(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", Font.PLAIN, 13)); l.setForeground(COULEUR_TEXTE); return l; }
    private JButton creerBouton(String txt, Color clr, String iconePath) {
        JButton b;
        if (iconePath != null && new java.io.File(iconePath).exists()) {
            b = new JButton(txt, new ImageIcon(iconePath));
        } else {
            b = new JButton(txt);
        }
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE); b.setBackground(clr); b.setPreferredSize(new Dimension(190,36));
        b.setFocusPainted(false); b.setBorderPainted(false); b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Color h = clr.brighter();
        b.addMouseListener(new MouseAdapter() { public void mouseEntered(MouseEvent e){b.setBackground(h);} public void mouseExited(MouseEvent e){b.setBackground(clr);}});
        return b;
    }
    private void configurerTableau(JTable t) {
        t.setBackground(COULEUR_CARTE); t.setForeground(COULEUR_TEXTE);
        t.setSelectionBackground(new Color(210,225,250)); t.setSelectionForeground(COULEUR_TEXTE);
        t.setGridColor(COULEUR_BORDURE); t.setRowHeight(32); t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTableHeader h = t.getTableHeader(); h.setBackground(new Color(245,247,252));
        h.setForeground(COULEUR_TEXTE); h.setFont(new Font("Segoe UI", Font.BOLD, 13));
        h.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE));
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer(); cr.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < t.getColumnCount(); i++) t.getColumnModel().getColumn(i).setCellRenderer(cr);
    }
}
