package Oberflaeche;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrierungsPanel extends JPanel {

    GUIFrame frame;
    private JTextField nutzername, frage, antwort;
    private JPasswordField password1, password2;
    private JLabel lblPasswortWiederholen;
    private JLabel lblSicherheitsfrage;
    private JLabel lblAntwort;
    private JButton btnLosGehts;
    private JLabel label;
    private JPanel panel_1;
    private JLabel lblAngemeldetAlsXxyy;

    /**
     * Create the application.
     */
    public RegistrierungsPanel() {
        frame = GUIFrame.getInstance();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 50, 110, 30};
        gbl_panel.rowHeights = new int[]{26, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblNewLabel = new JLabel("Nutzername");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 2;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        nutzername = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 3;
        gbc_textField.gridy = 0;
        panel.add(nutzername, gbc_textField);
        nutzername.setColumns(10);

        JLabel lblPasswort = new JLabel("Passwort");
        GridBagConstraints gbc_lblPasswort = new GridBagConstraints();
        gbc_lblPasswort.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswort.anchor = GridBagConstraints.EAST;
        gbc_lblPasswort.gridx = 2;
        gbc_lblPasswort.gridy = 1;
        panel.add(lblPasswort, gbc_lblPasswort);

        password1 = new JPasswordField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 3;
        gbc_textField_1.gridy = 1;
        panel.add(password1, gbc_textField_1);
        password1.setColumns(10);

        lblPasswortWiederholen = new JLabel("Passwort wiederholen");
        GridBagConstraints gbc_lblPasswortWiederholen = new GridBagConstraints();
        gbc_lblPasswortWiederholen.insets = new Insets(0, 0, 5, 5);
        gbc_lblPasswortWiederholen.anchor = GridBagConstraints.EAST;
        gbc_lblPasswortWiederholen.gridx = 2;
        gbc_lblPasswortWiederholen.gridy = 2;
        panel.add(lblPasswortWiederholen, gbc_lblPasswortWiederholen);

        password2 = new JPasswordField();
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.insets = new Insets(0, 0, 5, 0);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 3;
        gbc_textField_2.gridy = 2;
        panel.add(password2, gbc_textField_2);
        password2.setColumns(10);

        label = new JLabel("?");
        label.setForeground(Color.red);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 1;
        gbc_label.gridy = 4;
        panel.add(label, gbc_label);
        label.setToolTipText("Wähle eine Sicherheitsfrage, die du beantworten kannst, falls du dein Passwort vergessen hast.");


        lblSicherheitsfrage = new JLabel("Sicherheitsfrage");
        GridBagConstraints gbc_lblSicherheitsfrage = new GridBagConstraints();
        gbc_lblSicherheitsfrage.insets = new Insets(0, 0, 5, 5);
        gbc_lblSicherheitsfrage.anchor = GridBagConstraints.EAST;
        gbc_lblSicherheitsfrage.gridx = 2;
        gbc_lblSicherheitsfrage.gridy = 4;
        panel.add(lblSicherheitsfrage, gbc_lblSicherheitsfrage);

        frage = new JTextField();
        GridBagConstraints gbc_textField_3 = new GridBagConstraints();
        gbc_textField_3.insets = new Insets(0, 0, 5, 0);
        gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_3.gridx = 3;
        gbc_textField_3.gridy = 4;
        panel.add(frage, gbc_textField_3);
        frage.setColumns(10);

        lblAntwort = new JLabel("Antwort");
        GridBagConstraints gbc_lblAntwort = new GridBagConstraints();
        gbc_lblAntwort.insets = new Insets(0, 0, 5, 5);
        gbc_lblAntwort.anchor = GridBagConstraints.EAST;
        gbc_lblAntwort.gridx = 2;
        gbc_lblAntwort.gridy = 5;
        panel.add(lblAntwort, gbc_lblAntwort);

        antwort = new JTextField();
        GridBagConstraints gbc_textField_4 = new GridBagConstraints();
        gbc_textField_4.insets = new Insets(0, 0, 5, 0);
        gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_4.gridx = 3;
        gbc_textField_4.gridy = 5;
        panel.add(antwort, gbc_textField_4);
        antwort.setColumns(10);

        btnLosGehts = new JButton("Registrieren");
        GridBagConstraints gbc_btnLosGehts = new GridBagConstraints();
        gbc_btnLosGehts.insets = new Insets(0, 0, 5, 0);
        gbc_btnLosGehts.gridx = 3;
        gbc_btnLosGehts.gridy = 6;
        panel.add(btnLosGehts, gbc_btnLosGehts);
        btnLosGehts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nutzername.getText().isEmpty() || password1.getText().isEmpty() || password2.getText().isEmpty() || frage.getText().isEmpty() || antwort.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Alle Felder müssen ausgefüllt werden!",
                            "Registrierung fehlgeschlagen",
                            JOptionPane.WARNING_MESSAGE);

                } else if (!password1.getText().equals(password2.getText())) {
                    JOptionPane.showMessageDialog(frame,
                            "Passwörter stimmen nicht überein!",
                            "Registrierung fehlgeschlagen",
                            JOptionPane.WARNING_MESSAGE);
                } else if (password1.getText().length()<8){
                	JOptionPane.showMessageDialog(frame,
                            "Das Passwort ist zu kurz, bitte mindestens 8 Zeichen eingeben!",
                            "Registrierung fehlgeschlagen",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.registrieren(nutzername.getText(), password1.getText(), frage.getText(), antwort.getText());
                }
            }
        });
    }
}