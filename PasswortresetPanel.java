package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexander on 27.07.2016.
 */
public class PasswortresetPanel extends JPanel {
    private GUIFrame frame;
    private String aktuelleFrage;

    public PasswortresetPanel(String[] frage) {
        frame = GUIFrame.getInstance();
        aktuelleFrage=frage[0];
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0,40,10,40));

        JPanel fields = new JPanel(new GridLayout(6,1));
        fields.setBorder(new EmptyBorder(0,0,20,0));
        JLabel frage = new JLabel(aktuelleFrage);
        frage.setToolTipText("Bitte aktuelle Sicherheitsfrage beantworten!");
        fields.add(frage);
        JTextField AW = new JTextField();
        fields.add(AW);
        JLabel neuPWLbl1 = new JLabel("Neues Passwort eingeben:");
        fields.add(neuPWLbl1);
        JPasswordField neuPW1 = new JPasswordField();
        fields.add(neuPW1);
        JLabel neuPWLbl2 = new JLabel("Neues Passwort wiederholen:");
        fields.add(neuPWLbl2);
        JPasswordField neuPW2 = new JPasswordField();
        fields.add(neuPW2);
        add(fields, BorderLayout.CENTER);

        JButton okBtn = new JButton("Passwort zurücksetzen");
        //okBtn.setBorder(new EmptyBorder(5,0,10,0));
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!neuPW1.getText().equals(neuPW2.getText())){
                    JOptionPane.showMessageDialog(frame,
                            "Die neuen Passwörter stimmen nicht überein.\nPasswort konnte nicht geändert werden.",
                            "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                } else if (AW.getText().isEmpty() || neuPW1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Alle Felder müssen ausgefüllt werden!",
                            "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                } else if (neuPW1.getText().length()<8){
                	JOptionPane.showMessageDialog(frame,
                            "Das Passwort ist zu kurz, bitte mindestens 8 Zeichen eingeben!",
                            "Registrierung fehlgeschlagen",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.setzePasswortZurueck(AW.getText(), neuPW1.getText());
                }

            }
        });
        add(okBtn,BorderLayout.SOUTH);
    }
}
