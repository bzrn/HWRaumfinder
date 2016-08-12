package Oberflaeche;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * <strong>Zweck:</strong> Definiert Design und Funktionalitäten der Raumverwaltung
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 2.4
 * @author Alexander Reichenbach
 *
 */
public class RaumverwaltungsPanel extends JPanel {
    private GUIFrame frame;
    private String[] raum;
    private String[] bearbeiteterRaumDaten = {"",""};

    private JTextField raumNameText, kapaText;
    private JCheckBox chckbxBeamer, chckbxOhp, chckbxTafel, chckbxSmartboard, chckbxWhiteboard, chckbxComputerraum, chckbxBuchbar;

    public RaumverwaltungsPanel (String[] raum){
        frame = GUIFrame.getInstance();
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

        JPanel footer = new JPanel (new BorderLayout(5,5));
        
        JButton neuerRaumBtn = new JButton ("Neu");
        neuerRaumBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.erstelleLeerenRaum();
            }
        });
        footer.add(neuerRaumBtn, BorderLayout.WEST);
        
        JButton raumWahlBtn = new JButton ("Bearbeiten >>");
        raumWahlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bearbeiteterRaumDaten = frame.getRaumDaten(ergebnisList.getSelectedValue());
                befuelleFelderRaum();
            }
        });
        footer.add(raumWahlBtn, BorderLayout.CENTER);
        
        raumPanel.add(footer, BorderLayout.SOUTH);
        
        add(raumPanel);
    
    // ------------------------------

        JPanel raumBearbeitung = new JPanel(new BorderLayout(5,5));
        raumBearbeitung.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.darkGray, 1), "Bearbeiten:"));
        
            JPanel bearbeitungTop = new JPanel (new GridLayout(3,1,5,5));
                bearbeitungTop.setBorder(new EmptyBorder(0,5,0,0));
                JPanel raumName = new JPanel (new GridLayout(1,2,5,5));
                    JLabel raumNameLbl = new JLabel ("Raumname:");
                    raumName.add(raumNameLbl);
                    raumNameText = new JTextField(bearbeiteterRaumDaten[0]);
                    raumName.add(raumNameText);
                bearbeitungTop.add(raumName);
                JPanel kapa = new JPanel (new GridLayout(1,2,5,5));
                JLabel kapaLbl = new JLabel ("Kapazität:");
                kapa.add(kapaLbl);
                kapaText = new JTextField(bearbeiteterRaumDaten[1]);
                kapa.add(kapaText);
                bearbeitungTop.add(kapa);
                chckbxBuchbar = new JCheckBox("Buchbar");
                bearbeitungTop.add(chckbxBuchbar);
            raumBearbeitung.add(bearbeitungTop, BorderLayout.NORTH);
        
            JPanel bearbeitungBody = new JPanel (new BorderLayout(5,5));
            bearbeitungBody.setBorder(new EmptyBorder(0,5,0,0));
                JPanel bb1 = new JPanel (new GridLayout(3,1,5,5));
                    chckbxBeamer = new JCheckBox("Beamer");
                bb1.add(chckbxBeamer);
                    chckbxOhp = new JCheckBox("OHP");
                bb1.add(chckbxOhp);
                    chckbxTafel = new JCheckBox("Tafel");
                bb1.add(chckbxTafel);
            bearbeitungBody.add(bb1, BorderLayout.WEST);
                JPanel bb2 = new JPanel (new GridLayout(3,1,5,5));
                    chckbxSmartboard = new JCheckBox("Smartboard");
                bb2.add(chckbxSmartboard);
                    chckbxWhiteboard = new JCheckBox("Whiteboard");
                bb2.add(chckbxWhiteboard);
                    chckbxComputerraum = new JCheckBox("Computerraum");
                bb2.add(chckbxComputerraum);
            bearbeitungBody.add(bb2, BorderLayout.CENTER);
            
            raumBearbeitung.add(bearbeitungBody, BorderLayout.CENTER);
        
            JPanel bearbeitungFooter = new JPanel(new GridLayout(1,2,10,10));
                JButton loeschBtn = new JButton ("Löschen");
                loeschBtn.setForeground(Color.red);
                loeschBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (raumNameText.getText().isEmpty() || kapaText.getText().isEmpty()) popupInputFehlt();
                        else frame.loescheRaum(bearbeiteterRaumDaten[0]);
                    }
                });
                bearbeitungFooter.add(loeschBtn);
        
                JButton weiterBtn = new JButton ("Ändern");
                weiterBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (raumNameText.getText().isEmpty() || kapaText.getText().isEmpty()) popupInputFehlt();
                        else frame.bearbeiteRaum(bearbeiteterRaumDaten[0], raumNameText.getText(), kapaText.getText(), chckbxBuchbar.isSelected(), chckbxBeamer.isSelected(), chckbxOhp.isSelected(), chckbxTafel.isSelected(), chckbxSmartboard.isSelected(), chckbxWhiteboard.isSelected(), chckbxComputerraum.isSelected());
                    }
                });
                bearbeitungFooter.add(weiterBtn);
            raumBearbeitung.add(bearbeitungFooter, BorderLayout.SOUTH);

        add (raumBearbeitung);
    }

    private void befuelleFelderRaum() {
        raumNameText.setText(bearbeiteterRaumDaten[0]);
        kapaText.setText(bearbeiteterRaumDaten[1]);

        if (bearbeiteterRaumDaten[2].equals("true")) chckbxBuchbar.setSelected(true);
        if (bearbeiteterRaumDaten[3].equals("true")) chckbxBeamer.setSelected(true);
        if (bearbeiteterRaumDaten[4].equals("true")) chckbxOhp.setSelected(true);
        if (bearbeiteterRaumDaten[5].equals("true")) chckbxTafel.setSelected(true);
        if (bearbeiteterRaumDaten[6].equals("true")) chckbxSmartboard.setSelected(true);
        if (bearbeiteterRaumDaten[7].equals("true")) chckbxWhiteboard.setSelected(true);
        if (bearbeiteterRaumDaten[8].equals("true")) chckbxComputerraum.setSelected(true);
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
                "Bitte Raum auswählen!\nName und Kapazität dürfen nicht leer sein!",
                "Fehler",
                JOptionPane.WARNING_MESSAGE);
    }
}
