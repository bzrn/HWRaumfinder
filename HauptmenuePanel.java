package Oberflaeche;

import javafx.scene.paint.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Alexander on 26.07.2016.
 */
public class HauptmenuePanel extends JPanel {
    private GUIFrame frame;
    private String[] raeume;

    public HauptmenuePanel (String[] raeume){
        frame = GUIFrame.getInstance();
        this.raeume = raeume;
        initialize();
    }

    private void initialize(){
        BorderLayout layout = new BorderLayout();
        layout.setVgap(20);
        setLayout(layout);
        setBorder(new EmptyBorder(10, 10, 5, 5));

        if (!frame.aktiverNutzerIsAdmin()){
        	JPanel grussPanel = new JPanel(new GridLayout(2,1,0,5));
            JLabel gruss1 = new JLabel("Hallo "+frame.getAktivenNutzerString()+"!");
            grussPanel.add(gruss1);
            JLabel gruss2 = new JLabel("Wähle eine der folgenden Aktionen aus:");
            grussPanel.add(gruss2);
            add (grussPanel, BorderLayout.NORTH);
        } else {
            JButton closeButton = new JButton("HWRaumfinder speichern und schließen");
            closeButton.setFont(new Font(closeButton.getFont().getName(), Font.BOLD, closeButton.getFont().getSize()));
            closeButton.setForeground(Color.RED);
            closeButton.setBackground(Color.RED);
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            });
            add (closeButton, BorderLayout.NORTH);
        }
        
        JPanel body = new JPanel(new GridLayout(1,2,20,0));
        JPanel suchPanel = new JPanel (new GridLayout(2,1,0,10));
            suchPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Raumsuche"));
            JButton kennungssucheBtn = new JButton("Suche nach Raumkennung");
            kennungssucheBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String eingabe = (String)JOptionPane.showInputDialog(
                            frame,
                            "Raumkennung auswählen:",
                            "Raumsuche nach Kennung",
                            JOptionPane.PLAIN_MESSAGE,
                            new ImageIcon("HWRaumfinder_Icon.png"),
                            raeume,
                            0);
                    if (eingabe!=null) {
                        frame.raumAuswaehlen(eingabe);
                    }
                }
            });
            suchPanel.add(kennungssucheBtn);
            JButton kriteriensucheBtn = new JButton("Suche nach Kriterien");
        kriteriensucheBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.aktualisiereAnsicht(GUIFrame.KRITERIENSUCHE);
            }
        });
            suchPanel.add(kriteriensucheBtn);
        body.add(suchPanel);
        
        JPanel verwaltungsPanel = new JPanel();
        if (frame.aktiverNutzerIsAdmin()) {
        	verwaltungsPanel.setLayout(new GridLayout(5,1,0,5));
            verwaltungsPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Administration"));
            JButton reservierungenBtn = new JButton("Reservierungen verwalten");
            reservierungenBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.aktualisiereAnsicht(GUIFrame.RESERVIERUNGSVERWALTUNG);
                }
            });
            verwaltungsPanel.add(reservierungenBtn);
            JButton raumBtn = new JButton("Räume verwalten");
            raumBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.aktualisiereAnsicht(GUIFrame.RAUMVERWALTUNG);
                }
            });
            verwaltungsPanel.add(raumBtn);
            JButton nutzerBtn = new JButton("Nutzer verwalten");
            nutzerBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.aktualisiereAnsicht(GUIFrame.NUTZERVERWALTUNG);
                }
            });
            verwaltungsPanel.add(nutzerBtn);
        } else {
        	verwaltungsPanel.setLayout(new GridLayout(3,1,0,5));
            verwaltungsPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Account"));
            JButton reservierungenBtn = new JButton("Reservierungen verwalten");
            reservierungenBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.aktualisiereAnsicht(GUIFrame.RESERVIERUNGSVERWALTUNG);
                }
            });
            verwaltungsPanel.add(reservierungenBtn);
        }
        JButton passwortBtn = new JButton("Passwort ändern");
        passwortBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.aktualisiereAnsicht(GUIFrame.PASSWORTAENDERUNG);
            }
        });
        verwaltungsPanel.add(passwortBtn);
        JButton sicherheitsfrageBtn = new JButton("Sicherheitsfrage ändern");
        sicherheitsfrageBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.aktualisiereAnsicht(GUIFrame.SICHERHEITSFRAGE);
            }
        });
        verwaltungsPanel.add(sicherheitsfrageBtn);
        
        body.add(verwaltungsPanel);
        add(body, BorderLayout.CENTER);

        JLabel impressum = new JLabel("© 2016 HWRaumfinder GmbH & Co. KG");
        impressum.setHorizontalAlignment(SwingConstants.CENTER);
        add(impressum, BorderLayout.SOUTH);

    }
}
