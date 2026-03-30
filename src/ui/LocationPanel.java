package ui;

import dao.LocationDAO;
import dao.AbonneDAO;
import dao.CassetteDAO;
import model.Location;
import model.Abonne;
import model.Cassette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

/**
 * LocationPanel - Enregistrement des locations (thème clair, boutons en bas)
 */
public class LocationPanel extends JPanel {

    private JTable tableau;
    private DefaultTableModel modeleTableau;
    private JComboBox<Abonne> comboAbonne;
    private JComboBox<Cassette> comboCassette;
    private JTextField champDate;
    private LocationDAO locationDAO;
    private AbonneDAO abonneDAO;
    private CassetteDAO cassetteDAO;

    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_SUCCES = new Color(40, 180, 100);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);

    public LocationPanel() {
        locationDAO = new LocationDAO();
        abonneDAO = new AbonneDAO();
        cassetteDAO = new CassetteDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Haut : titre + formulaire
        JPanel panneauNord = new JPanel();
        panneauNord.setLayout(new BoxLayout(panneauNord, BoxLayout.Y_AXIS));
        panneauNord.setBackground(COULEUR_FOND);

        JLabel labelTitre = new JLabel("\uD83D\uDCC5  Enregistrement des Locations");
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
        comboAbonne.setPreferredSize(new Dimension(180, 32));
        comboAbonne.setBackground(COULEUR_CHAMP);
        panneauForm.add(comboAbonne);

        panneauForm.add(creerLabel("Cassette :"));
        comboCassette = new JComboBox<>();
        comboCassette.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboCassette.setPreferredSize(new Dimension(220, 32));
        comboCassette.setBackground(COULEUR_CHAMP);
        panneauForm.add(comboCassette);

        panneauForm.add(creerLabel("Date :"));
        champDate = creerChamp(10);
        champDate.setText(LocalDate.now().toString());
        panneauForm.add(champDate);

        panneauNord.add(panneauForm);
        add(panneauNord, BorderLayout.NORTH);

        // Tableau
        String[] colonnes = {"N° Abonné", "Nom Abonné", "N° Cassette", "Titre Film", "Date Location"};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableau = new JTable(modeleTableau);
        configurerTableau(tableau);
        JScrollPane scroll = new JScrollPane(tableau);
        scroll.setBorder(BorderFactory.createLineBorder(COULEUR_BORDURE, 1));
        scroll.getViewport().setBackground(COULEUR_CARTE);
        add(scroll, BorderLayout.CENTER);

        // Boutons en bas
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panneauBoutons.setBackground(COULEUR_FOND);

        JButton btnLouer = creerBouton("Enregistrer la location", COULEUR_SUCCES, "icons/add.png");
        JButton btnRafraichir = creerBouton("Rafraîchir", COULEUR_ACCENT, "icons/refresh.png");

        JLabel labelRegle = new JLabel("  ⚠ Max 3 cassettes par abonné");
        labelRegle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelRegle.setForeground(new Color(220, 140, 20));

        panneauBoutons.add(btnLouer);
        panneauBoutons.add(btnRafraichir);
        panneauBoutons.add(labelRegle);
        add(panneauBoutons, BorderLayout.SOUTH);

        // Événements
        btnLouer.addActionListener(e -> {
            if (comboAbonne.getSelectedItem() == null || comboCassette.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un abonné et une cassette !"); return;
            }
            if (champDate.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La date est obligatoire !"); return;
            }
            Abonne abonne = (Abonne) comboAbonne.getSelectedItem();
            Cassette cassette = (Cassette) comboCassette.getSelectedItem();
            if (abonne.getNbLocation() >= 3) {
                JOptionPane.showMessageDialog(this,
                    abonne.getNomAbonne() + " a déjà 3 cassettes en location.\nIl doit d'abord en retourner une.",
                    "Limite atteinte", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Location loc = new Location();
            loc.setNumAbonne(abonne.getNumAbonne());
            loc.setNumCassette(cassette.getNumCassette());
            loc.setDateLocation(champDate.getText().trim());
            if (locationDAO.ajouterLocation(loc)) {
                abonneDAO.incrementerNbLocation(abonne.getNumAbonne());
                JOptionPane.showMessageDialog(this, "Location enregistrée !\n" + abonne.getNomAbonne() + " → " + cassette);
                rafraichirTout();
            }
        });
        btnRafraichir.addActionListener(e -> rafraichirTout());
        rafraichirTout();
    }

    private void rafraichirTout() {
        comboAbonne.removeAllItems();
        for (Abonne ab : abonneDAO.listerTout()) comboAbonne.addItem(ab);
        comboCassette.removeAllItems();
        for (Cassette c : cassetteDAO.listerDisponibles()) comboCassette.addItem(c);
        modeleTableau.setRowCount(0);
        for (Location loc : locationDAO.listerTout()) {
            modeleTableau.addRow(new Object[]{ loc.getNumAbonne(), loc.getNomAbonne(), loc.getNumCassette(), loc.getNomTitre(), loc.getDateLocation() });
        }
    }

    private JLabel creerLabel(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", Font.PLAIN, 13)); l.setForeground(COULEUR_TEXTE); return l; }
    private JTextField creerChamp(int cols) {
        JTextField c = new JTextField(cols); c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(cols*10,32)); c.setBackground(COULEUR_CHAMP); c.setForeground(COULEUR_TEXTE);
        c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COULEUR_BORDURE,1),BorderFactory.createEmptyBorder(4,8,4,8)));
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
