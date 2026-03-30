package ui;

import dao.CassetteDAO;
import dao.TitreDAO;
import model.Cassette;
import model.Titre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * CassettePanel - Gestion des cassettes (thème clair, boutons en bas)
 */
public class CassettePanel extends JPanel {

    private JTable tableau;
    private DefaultTableModel modeleTableau;
    private JTextField champDateAchat, champPrix;
    private JComboBox<Titre> comboTitre;
    private CassetteDAO cassetteDAO;
    private TitreDAO titreDAO;

    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_SUCCES = new Color(40, 180, 100);
    private static final Color COULEUR_DANGER = new Color(220, 55, 55);
    private static final Color COULEUR_MODIF = new Color(240, 160, 20);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);

    public CassettePanel() {
        cassetteDAO = new CassetteDAO();
        titreDAO = new TitreDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Haut
        JPanel panneauNord = new JPanel();
        panneauNord.setLayout(new BoxLayout(panneauNord, BoxLayout.Y_AXIS));
        panneauNord.setBackground(COULEUR_FOND);

        JLabel labelTitre = new JLabel("\uD83D\uDCFC  Gestion des Cassettes");
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
        panneauForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        panneauForm.add(creerLabel("Date achat :"));
        champDateAchat = creerChamp(10);
        panneauForm.add(champDateAchat);
        panneauForm.add(creerLabel("Prix (€) :"));
        champPrix = creerChamp(7);
        panneauForm.add(champPrix);
        panneauForm.add(creerLabel("Titre :"));
        comboTitre = new JComboBox<>();
        comboTitre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboTitre.setPreferredSize(new Dimension(200, 32));
        comboTitre.setBackground(COULEUR_CHAMP);
        panneauForm.add(comboTitre);

        panneauNord.add(panneauForm);
        add(panneauNord, BorderLayout.NORTH);

        // Tableau
        String[] colonnes = {"N° Cassette", "Date Achat", "Prix (€)", "Titre du Film"};
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
        JButton btnAjouter = creerBouton("Ajouter", COULEUR_SUCCES, "icons/add.png");
        JButton btnModifier = creerBouton("Modifier", COULEUR_MODIF, "icons/edit.png");
        JButton btnSupprimer = creerBouton("Supprimer", COULEUR_DANGER, "icons/delete.png");
        JButton btnVider = creerBouton("Vider", new Color(140, 150, 170), "icons/clear.png");
        panneauBoutons.add(btnAjouter);
        panneauBoutons.add(btnModifier);
        panneauBoutons.add(btnSupprimer);
        panneauBoutons.add(btnVider);
        add(panneauBoutons, BorderLayout.SOUTH);

        // Événements
        btnAjouter.addActionListener(e -> {
            if (!validerFormulaire()) return;
            Cassette c = new Cassette();
            c.setDateAchat(champDateAchat.getText().trim());
            c.setPrix(Double.parseDouble(champPrix.getText().trim()));
            c.setIdTitre(((Titre) comboTitre.getSelectedItem()).getIdTitre());
            if (cassetteDAO.ajouter(c)) {
                JOptionPane.showMessageDialog(this, "Cassette ajoutée !");
                viderFormulaire(); chargerDonnees();
            }
        });
        btnModifier.addActionListener(e -> {
            int l = tableau.getSelectedRow();
            if (l == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une cassette !"); return; }
            if (!validerFormulaire()) return;
            int num = (int) modeleTableau.getValueAt(l, 0);
            Cassette c = new Cassette();
            c.setNumCassette(num);
            c.setDateAchat(champDateAchat.getText().trim());
            c.setPrix(Double.parseDouble(champPrix.getText().trim()));
            c.setIdTitre(((Titre) comboTitre.getSelectedItem()).getIdTitre());
            if (cassetteDAO.modifier(c)) {
                JOptionPane.showMessageDialog(this, "Cassette modifiée !");
                viderFormulaire(); chargerDonnees();
            }
        });
        btnSupprimer.addActionListener(e -> {
            int l = tableau.getSelectedRow();
            if (l == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une cassette !"); return; }
            int num = (int) modeleTableau.getValueAt(l, 0);
            int choix = JOptionPane.showConfirmDialog(this, "Supprimer cette cassette ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                if (cassetteDAO.supprimer(num)) {
                    JOptionPane.showMessageDialog(this, "Supprimée !");
                    viderFormulaire(); chargerDonnees();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible : cassette en location.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnVider.addActionListener(e -> viderFormulaire());

        tableau.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int l = tableau.getSelectedRow();
                if (l >= 0) {
                    champDateAchat.setText(String.valueOf(modeleTableau.getValueAt(l, 1)));
                    champPrix.setText(String.valueOf(modeleTableau.getValueAt(l, 2)));
                    String titreNom = (String) modeleTableau.getValueAt(l, 3);
                    for (int i = 0; i < comboTitre.getItemCount(); i++) {
                        if (comboTitre.getItemAt(i).getNomTitre().equals(titreNom)) {
                            comboTitre.setSelectedIndex(i); break;
                        }
                    }
                }
            }
        });

        chargerTitres(); chargerDonnees();
    }

    private boolean validerFormulaire() {
        if (champDateAchat.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Date obligatoire !"); return false; }
        try { Double.parseDouble(champPrix.getText().trim()); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Prix invalide !"); return false; }
        if (comboTitre.getSelectedItem() == null) { JOptionPane.showMessageDialog(this, "Sélectionnez un titre !"); return false; }
        return true;
    }
    private void chargerTitres() { comboTitre.removeAllItems(); for (Titre t : titreDAO.listerTout()) comboTitre.addItem(t); }
    private void chargerDonnees() {
        modeleTableau.setRowCount(0);
        for (Cassette c : cassetteDAO.listerTout()) {
            modeleTableau.addRow(new Object[]{ c.getNumCassette(), c.getDateAchat(), c.getPrix(), c.getNomTitre() });
        }
    }
    private void viderFormulaire() {
        champDateAchat.setText(""); champPrix.setText("");
        if (comboTitre.getItemCount() > 0) comboTitre.setSelectedIndex(0);
        tableau.clearSelection();
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
        b.setForeground(Color.WHITE); b.setBackground(clr); b.setPreferredSize(new Dimension(130,36));
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
