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

public class NutzerverwaltungsPanel extends JPanel {
    private GUIFrame frame;
    private String[] nutzer;
    private String[] bearbeiteterNutzerDaten = {"",""};

    private JTextField nutzerNameText, frageText, passwordText, antwortText;

    public NutzerverwaltungsPanel (GUIFrame parent, String[] nutzer){
        frame = parent;
        this.nutzer = nutzer;
        initialize();
    }

    private void initialize(){
    	setLayout(new GridLayout(1,2,5,5));
        setBorder(new EmptyBorder(10,10,5,5));
        
        JPanel nutzerPanel = new JPanel (new BorderLayout(0,5));
        nutzerPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Nutzerliste:"));

        JList<String> ergebnisList = new JList<>(nutzer);
        ergebnisList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ergebnisList.setLayoutOrientation(JList.VERTICAL);
        ergebnisList.setVisibleRowCount(-1);
        ergebnisList.setCellRenderer(getRenderer());

        JScrollPane ergebnisPanel = new JScrollPane(ergebnisList);
        nutzerPanel.add(ergebnisPanel, BorderLayout.CENTER);
        
        JButton nutzerWahlBtn = new JButton ("Nutzer bearbeiten >>");
        nutzerWahlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = ergebnisList.getSelectedValue();
                int admin = name.indexOf(" <Admin>");
                if (admin != -1) name = name.substring(0, admin);
                bearbeiteterNutzerDaten = frame.getNutzerDaten(name);
                befuelleFelderNutzer();
            }
        });
        nutzerPanel.add(nutzerWahlBtn, BorderLayout.SOUTH);
        
        add(nutzerPanel);
        
        
        JPanel nutzerBearbeitung = new JPanel(new GridLayout(5,2,5,5));
        nutzerBearbeitung.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Bearbeiten:"));
        	JLabel nutzerNameLbl = new JLabel ("Nutzername:");		// BORDERS FEHLEN
        	nutzerNameLbl.setBorder(new EmptyBorder(0,5,0,0));
        	nutzerBearbeitung.add(nutzerNameLbl);
            nutzerNameText = new JTextField(bearbeiteterNutzerDaten[0]);
        	nutzerBearbeitung.add(nutzerNameText);
        	JLabel passwortLbl = new JLabel("Passwort:");
        	passwortLbl.setBorder(new EmptyBorder(0,5,0,0));
        	passwortLbl.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        	nutzerBearbeitung.add(passwortLbl);
        	passwordText = new JPasswordField();
        	passwordText.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        	nutzerBearbeitung.add(passwordText);
        	JLabel frageLbl = new JLabel("Sicherheitsfrage:");
        	frageLbl.setBorder(new EmptyBorder(0,5,0,0));
        	nutzerBearbeitung.add(frageLbl);
        	frageText = new JTextField(bearbeiteterNutzerDaten[1]);
        	nutzerBearbeitung.add(frageText);
        	JLabel antwortLbl = new JLabel ("Antwort:");
        	antwortLbl.setBorder(new EmptyBorder(0,5,0,0));
        	antwortLbl.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        	nutzerBearbeitung.add(antwortLbl);
        	antwortText = new JTextField();
        	antwortText.setToolTipText("Leer lassen, wenn keine Änderung gewünscht");
        	nutzerBearbeitung.add(antwortText);
        	
        	JButton loeschBtn = new JButton ("Löschen");
        	loeschBtn.setForeground(Color.red);
        	loeschBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.loescheNutzer(bearbeiteterNutzerDaten[0]);
                }
            });
        	nutzerBearbeitung.add(loeschBtn);
        	
        	JButton weiterBtn = new JButton ("Ändern");
        	weiterBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	if (nutzerNameText.getText().isEmpty() || frageText.getText().isEmpty()) popupInputFehlt();
                	else frame.bearbeiteNutzer(bearbeiteterNutzerDaten[0], nutzerNameText.getText(), passwordText.getText(), frageText.getText(), antwortText.getText());
                }
            });
        	nutzerBearbeitung.add(weiterBtn);
        	
        	add (nutzerBearbeitung);
    }

    private void befuelleFelderNutzer() {
        nutzerNameText.setText(bearbeiteterNutzerDaten[0]);
        frageText.setText(bearbeiteterNutzerDaten[1]);
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
                "Nutzername und Sicherheitsfrage dürfen nicht leer sein!",
                "Fehler",
                JOptionPane.WARNING_MESSAGE);
    }
}