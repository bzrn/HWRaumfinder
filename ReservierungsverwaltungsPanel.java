package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexander on 27.07.2016.
 */
public class ReservierungsverwaltungsPanel extends JPanel {
    private GUIFrame frame;
    String[] reservierungen;

    public ReservierungsverwaltungsPanel(String[] reservierungen) {
        this.reservierungen=reservierungen;
        frame = GUIFrame.getInstance();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(10,5));
        setBorder(new EmptyBorder(10,10,5,5));

        JLabel einleitung1 = new JLabel("Aktive Reservierungen:");
        add(einleitung1, BorderLayout.NORTH);

        JList<String> ergebnisList = new JList<>(reservierungen);
        ergebnisList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ergebnisList.setLayoutOrientation(JList.VERTICAL);
        ergebnisList.setVisibleRowCount(-1);
        ergebnisList.setCellRenderer(getRenderer());

        JScrollPane ergebnisPanel = new JScrollPane(ergebnisList);
        add(ergebnisPanel);

        JPanel footer = new JPanel(new BorderLayout());
        JButton stornoBtn = new JButton ("Stornieren");
        if (frame.aktiverNutzerIsAdmin()){
            footer.add(stornoBtn, BorderLayout.WEST);
            JButton detailBtn = new JButton ("Details");
            footer.add(detailBtn, BorderLayout.EAST);
            detailBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String auswahl = ergebnisList.getSelectedValue();
                    if (auswahl == null){
                        popupAuswahlFehlt();
                    } else {
                        frame.zeigeReservierungsDetails(Long.parseLong(auswahl.substring(auswahl.indexOf("(")+1,auswahl.indexOf(")"))));
                    }
                }
            });
        } else {
        	footer.add(stornoBtn, BorderLayout.EAST);
        }
        
        add (footer, BorderLayout.SOUTH);

        stornoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String auswahl = ergebnisList.getSelectedValue();
                if (auswahl == null){
                    popupAuswahlFehlt();
                } else {
                    frame.storniereReservierung(Long.parseLong(auswahl.substring(auswahl.indexOf("(")+1, auswahl.indexOf(")"))));
                }  
            }
        });
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
    
    private void popupAuswahlFehlt() {
    	JOptionPane.showMessageDialog(frame,
                "Bitte eine Reservierung auswählen!",
                "Fehler",
                JOptionPane.WARNING_MESSAGE);
    }
}
