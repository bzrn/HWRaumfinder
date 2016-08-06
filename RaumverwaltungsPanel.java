package Oberflaeche;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Verarbeitung.raum;

public class RaumverwaltungsPanel extends JPanel {
    private GUIFrame frame;
    private String[] raum;
    private String[] bearbeiteterraumDaten = {"",""};

    private JTextField raumNameText, frageText, passwordText, antwortText;

    public RaumverwaltungsPanel (GUIFrame parent, String[] raum){
        frame = parent;
        this.raum = raum;
        initialize();
    }

    private void initialize(){
        setLayout(new GridLayout(1,2,5,5));
        setBorder(new EmptyBorder(10,10,5,5));

        JPanel raumPanel = new JPanel (new BorderLayout(0,5));
        raumPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Raumliste:"));

        JList<String> ergebnisList = new JList<>(raum);
        ergebnisList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ergebnisList.setLayoutOrientation(JList.VERTICAL);
        ergebnisList.setVisibleRowCount(-1);
        ergebnisList.setCellRenderer(getRenderer());

        JScrollPane ergebnisPanel = new JScrollPane(ergebnisList);
        raumPanel.add(ergebnisPanel, BorderLayout.CENTER);

        JButton raumWahlBtn = new JButton ("Raum bearbeiten >>");
        raumWahlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bearbeiteterraumDaten = frame.getraumDaten(ergebnisList.getSelectedValue());
                befuelleFelderraum();
            }
        });
        raumPanel.add(raumWahlBtn, BorderLayout.SOUTH);

        add(raumPanel);


        JPanel raumBearbeitung = new JPanel(new GridLayout(5,2,5,5));
        raumBearbeitung.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Bearbeiten:"));
        JLabel raumNameLbl = new JLabel ("Raumname:");		// BORDERS FEHLEN
        raumNameLbl.setBorder(new EmptyBorder(0,5,0,0));
        raumBearbeitung.add(raumNameLbl);
        raumNameText = new JTextField(bearbeiteterraumDaten[0]);
        raumBearbeitung.add(raumNameText);
        JLabel passwortLbl = new JLabel("Passwort:");
        passwortLbl.setBorder(new EmptyBorder(0,5,0,0));
        passwortLbl.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        raumBearbeitung.add(passwortLbl);
        passwordText = new JPasswordField();
        passwordText.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        raumBearbeitung.add(passwordText);
        JLabel frageLbl = new JLabel("Sicherheitsfrage:");
        frageLbl.setBorder(new EmptyBorder(0,5,0,0));
        raumBearbeitung.add(frageLbl);
        frageText = new JTextField(bearbeiteterraumDaten[1]);
        raumBearbeitung.add(frageText);
        JLabel antwortLbl = new JLabel ("Antwort:");
        antwortLbl.setBorder(new EmptyBorder(0,5,0,0));
        antwortLbl.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        raumBearbeitung.add(antwortLbl);
        antwortText = new JTextField();
        antwortText.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        raumBearbeitung.add(antwortText);

        JButton loeschBtn = new JButton ("Löschen");
        loeschBtn.setForeground(Color.red);
        loeschBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.loescheRaum(bearbeiteterraumDaten[0]);
            }
        });
        raumBearbeitung.add(loeschBtn);

        JButton weiterBtn = new JButton ("Ändern");
        weiterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!(raumNameText.getText().isEmpty() || frageText.getText().isEmpty())) popupInputFehlt();
                else frame.bearbeiteRaum(bearbeiteterraumDaten[0], raumNameText.getText(), passwordText.getText(), frageText.getText(), antwortText.getText());
            }
        });
        raumBearbeitung.add(weiterBtn);

        add (raumBearbeitung);
    }

    private void befuelleFelderRaum() {
        raumNameText.setText(bearbeiteterraumDaten[0]);
        frageText.setText(bearbeiteterraumDaten[1]);
    }

    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

                return listCellRendererComponent;
            }
        };
    }

    private void popupInputFehlt() {
        JOptionPane.showMessageDialog(frame,
                "raumname und Sicherheitsfrage dürfen nicht leer sein!",
                "Fehler",
                JOptionPane.WARNING_MESSAGE);
    }
}