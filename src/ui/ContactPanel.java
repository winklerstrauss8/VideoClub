package ui;

import javax.swing.*;
import java.awt.*;

/**
 * ContactPanel - Page de contact du club (thème clair)
 */
public class ContactPanel extends JPanel {

    private static final Color COULEUR_FOND = new Color(235, 240, 248);
    private static final Color COULEUR_CARTE = Color.WHITE;
    private static final Color COULEUR_TEXTE = new Color(30, 30, 50);
    private static final Color COULEUR_ACCENT = new Color(55, 120, 250);
    private static final Color COULEUR_SUCCES = new Color(40, 180, 100);
    private static final Color COULEUR_BORDURE = new Color(210, 215, 225);
    private static final Color COULEUR_CHAMP = new Color(245, 247, 252);

    public ContactPanel() {
        setLayout(new BorderLayout());
        setBackground(COULEUR_FOND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBackground(COULEUR_FOND);

        // Titre
        JLabel labelTitre = new JLabel("\u2709  Contact");
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        labelTitre.setForeground(COULEUR_TEXTE);
        labelTitre.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenu.add(labelTitre);
        contenu.add(Box.createVerticalStrut(20));

        // Infos du club
        JPanel carteInfos = new JPanel();
        carteInfos.setLayout(new BoxLayout(carteInfos, BoxLayout.Y_AXIS));
        carteInfos.setBackground(COULEUR_CARTE);
        carteInfos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        carteInfos.setAlignmentX(Component.LEFT_ALIGNMENT);
        carteInfos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        JLabel labelInfoTitre = new JLabel("Informations du Club");
        labelInfoTitre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelInfoTitre.setForeground(COULEUR_ACCENT);
        labelInfoTitre.setAlignmentX(Component.LEFT_ALIGNMENT);
        carteInfos.add(labelInfoTitre);
        carteInfos.add(Box.createVerticalStrut(15));

        carteInfos.add(creerLigneInfo("\uD83C\uDFAC  Nom :", "VideoClub - Club de Location"));
        carteInfos.add(Box.createVerticalStrut(6));
        carteInfos.add(creerLigneInfo("\uD83D\uDCCD  Adresse :", "123 Rue du Cinéma, 75001 Paris"));
        carteInfos.add(Box.createVerticalStrut(6));
        carteInfos.add(creerLigneInfo("\uD83D\uDCDE  Téléphone :", "01 23 45 67 89"));
        carteInfos.add(Box.createVerticalStrut(6));
        carteInfos.add(creerLigneInfo("\u2709  Email :", "contact@videoclub.fr"));
        carteInfos.add(Box.createVerticalStrut(6));
        carteInfos.add(creerLigneInfo("\uD83D\uDD52  Horaires :", "Lundi - Samedi : 9h00 - 19h00"));
        carteInfos.add(Box.createVerticalStrut(4));
        carteInfos.add(creerLigneInfo("", "Dimanche : Fermé"));

        contenu.add(carteInfos);
        contenu.add(Box.createVerticalStrut(25));

        // Formulaire de contact
        JPanel carteForm = new JPanel();
        carteForm.setLayout(new BoxLayout(carteForm, BoxLayout.Y_AXIS));
        carteForm.setBackground(COULEUR_CARTE);
        carteForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        carteForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        carteForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));

        JLabel labelFormTitre = new JLabel("Nous contacter");
        labelFormTitre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelFormTitre.setForeground(COULEUR_ACCENT);
        labelFormTitre.setAlignmentX(Component.LEFT_ALIGNMENT);
        carteForm.add(labelFormTitre);
        carteForm.add(Box.createVerticalStrut(15));

        carteForm.add(creerLabel("Votre nom :"));
        carteForm.add(Box.createVerticalStrut(5));
        JTextField champNom = creerChampTexte();
        carteForm.add(champNom);
        carteForm.add(Box.createVerticalStrut(10));

        carteForm.add(creerLabel("Votre email :"));
        carteForm.add(Box.createVerticalStrut(5));
        JTextField champEmail = creerChampTexte();
        carteForm.add(champEmail);
        carteForm.add(Box.createVerticalStrut(10));

        carteForm.add(creerLabel("Votre message :"));
        carteForm.add(Box.createVerticalStrut(5));
        JTextArea champMessage = new JTextArea(5, 20);
        champMessage.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        champMessage.setBackground(COULEUR_CHAMP);
        champMessage.setForeground(COULEUR_TEXTE);
        champMessage.setCaretColor(COULEUR_TEXTE);
        champMessage.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        champMessage.setLineWrap(true);
        champMessage.setWrapStyleWord(true);
        champMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        champMessage.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        carteForm.add(champMessage);
        carteForm.add(Box.createVerticalStrut(15));

        JButton btnEnvoyer = new JButton("Envoyer le message");
        btnEnvoyer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnvoyer.setForeground(Color.WHITE);
        btnEnvoyer.setBackground(COULEUR_SUCCES);
        btnEnvoyer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnEnvoyer.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEnvoyer.setFocusPainted(false);
        btnEnvoyer.setBorderPainted(false);
        btnEnvoyer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnvoyer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btnEnvoyer.setBackground(COULEUR_SUCCES.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent e) { btnEnvoyer.setBackground(COULEUR_SUCCES); }
        });
        btnEnvoyer.addActionListener(e -> {
            if (champNom.getText().trim().isEmpty() || champEmail.getText().trim().isEmpty()
                || champMessage.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this,
                "Merci " + champNom.getText().trim() + " !\nVotre message a été envoyé.\nRéponse à : " + champEmail.getText().trim(),
                "Message envoyé", JOptionPane.INFORMATION_MESSAGE);
            champNom.setText(""); champEmail.setText(""); champMessage.setText("");
        });
        carteForm.add(btnEnvoyer);
        contenu.add(carteForm);

        JScrollPane scroll = new JScrollPane(contenu);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COULEUR_FOND);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel creerLigneInfo(String label, String valeur) {
        JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        ligne.setBackground(COULEUR_CARTE);
        ligne.setAlignmentX(Component.LEFT_ALIGNMENT);
        ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        if (!label.isEmpty()) {
            JLabel l = new JLabel(label);
            l.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
            l.setForeground(COULEUR_TEXTE);
            ligne.add(l);
        }
        JLabel v = new JLabel(valeur);
        v.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        v.setForeground(new Color(80, 90, 110));
        ligne.add(v);
        return ligne;
    }

    private JLabel creerLabel(String texte) {
        JLabel l = new JLabel(texte);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(COULEUR_TEXTE);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField creerChampTexte() {
        JTextField c = new JTextField();
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.setBackground(COULEUR_CHAMP);
        c.setForeground(COULEUR_TEXTE);
        c.setCaretColor(COULEUR_TEXTE);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COULEUR_BORDURE, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return c;
    }
}
