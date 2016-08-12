package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <strong/>Zweck:<strong/> Definiert Design und Funktionalitäten des Passwortänderungs-Panels
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 2.0
 * @author Alexander Reichenbach
 *
 */
public class PasswortaenderungsPanel extends JPanel {
    private GUIFrame frame;

    public PasswortaenderungsPanel() {
        frame = GUIFrame.getInstance();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0,40,10,40));

        JPanel fields = new JPanel(new GridLayout(6,1));
        fields.setBorder(new EmptyBorder(0,0,20,0));
        JLabel altPWLbl = new JLabel("Aktuelles Passwort eingeben:");
        fields.add(altPWLbl);
        JPasswordField altPW = new JPasswordField();
        fields.add(altPW);
        JLabel neuPWLbl1 = new JLabel("Neues Passwort eingeben:");
        fields.add(neuPWLbl1);
        JPasswordField neuPW1 = new JPasswordField();
        fields.add(neuPW1);
        JLabel neuPWLbl2 = new JLabel("Neues Passwort wiederholen:");
        fields.add(neuPWLbl2);
        JPasswordField neuPW2 = new JPasswordField();
        fields.add(neuPW2);
        add(fields, BorderLayout.CENTER);

        JButton okBtn = new JButton("Änderung durchführen");
        //okBtn.setBorder(new EmptyBorder(5,0,10,0));
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!neuPW1.getText().equals(neuPW2.getText())){
                    JOptionPane.showMessageDialog(frame,
                            "Die neuen Passwörter stimmen nicht überein. Passwort konnte nicht geändert werden.",
                            "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                } else if (altPW.getText().isEmpty() || neuPW1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Alle Felder müssen ausgefüllt werden!",
                            "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                } else if (neuPW1.getText().length()<8){
                	JOptionPane.showMessageDialog(frame,
                            "Das Passwort ist zu kurz, bitte mindestens 8 Zeichen eingeben!",
                            "Änderung fehlgeschlagen",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.passwortAendern(altPW.getText(), neuPW1.getText());
                }

            }
        });
        add(okBtn,BorderLayout.SOUTH);
    }
}
