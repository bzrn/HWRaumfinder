package Oberflaeche;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class SuchPanel extends JPanel {

    private GUIFrame frame;
    private JTextField datumTextField;
    private Date start, ende;
    private Calendar c;
    private SimpleDateFormat sdf;

    /**
     * Create the application.
     */
    public SuchPanel(GUIFrame parent) {
        frame = parent;
        start = new Date (System.currentTimeMillis());
        ende = new Date (System.currentTimeMillis()+3600000);
        
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        
        c = Calendar.getInstance();
        c.setTime(start);
        
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        //JPanel panel = new JPanel();
        //this.add(panel, BorderLayout.CENTER);
		
		datumTextField = new JTextField();
		datumTextField.setColumns(10);
		datumTextField.setText(sdf.format(c.getTime()));
		
		//Suchbutton
		
		JButton btnSuchen = new JButton("Suchen");
		btnSuchen.setForeground(Color.red);
		
		
		// Checkboxen
		
		JCheckBox chckbxBeamer = new JCheckBox("Beamer");
		
		JCheckBox chckbxOhp = new JCheckBox("OHP");
		
		JCheckBox chckbxTafel = new JCheckBox("Tafel");
		
		JCheckBox chckbxSmartboard = new JCheckBox("Smartboard");
		
		JCheckBox chckbxWhiteboard = new JCheckBox("Whiteboard");
		
		JCheckBox chckbxComputerraum = new JCheckBox("Computerraum");
		
		
		//Label
		
		JLabel lblAusstattung = new JLabel("Ausstattung");
		
		JLabel lblZeitraum = new JLabel("Zeitraum");
		
		JLabel lblDatum = new JLabel("Datum");
		
		
		//Dropdown Menues
		
		JComboBox<String> comboKapazitaet = new JComboBox<String>(GUIFrame.kapazitaet);
		
		JComboBox<String> startStunde = new JComboBox<String>(GUIFrame.hour);
			startStunde.setSelectedItem(Integer.toString(c.get(Calendar.HOUR_OF_DAY)));
		JComboBox<String> startMinute = new JComboBox<String>(GUIFrame.min);
			startMinute.setSelectedItem(Integer.toString((c.get(Calendar.MINUTE)/15)*15));
		
		JComboBox<String> endStunde = new JComboBox<String>(GUIFrame.hour);
			endStunde.setSelectedItem(Integer.toString(c.get(Calendar.HOUR_OF_DAY)+1));
		JComboBox<String> endMinute = new JComboBox<String>(GUIFrame.min);
			endMinute.setSelectedItem(Integer.toString((c.get(Calendar.MINUTE)/15)*15));
		
		JLabel lblVon = new JLabel("von");
		JLabel lblBis = new JLabel("bis");
		
		JLabel label = new JLabel(":");
		JLabel lblUhr = new JLabel("Uhr");
		
		JLabel label_1 = new JLabel(":");
		JLabel label_2 = new JLabel("Uhr");
		
		
		
		
		JLabel lblMindestkapazitt = new JLabel("Mindestkapazität");
		GroupLayout gl_panel = new GroupLayout(this);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(33)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblDatum)
									.addGap(18)
									.addComponent(datumTextField, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(1)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblVon)
												.addComponent(lblBis))
											.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(startStunde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(endStunde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblMindestkapazitt)
										.addComponent(btnSuchen))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(label, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(startMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblUhr))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(comboKapazitaet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(endMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))))
								.addComponent(lblZeitraum))
							.addGap(52)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxWhiteboard)
						.addComponent(chckbxSmartboard)
						.addComponent(chckbxTafel)
						.addComponent(chckbxBeamer)
						.addComponent(chckbxOhp)
						.addComponent(chckbxComputerraum)
						.addComponent(lblAusstattung))
					.addGap(12))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblZeitraum)
						.addComponent(lblAusstattung))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(chckbxBeamer)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDatum)
							.addComponent(datumTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(9)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxOhp)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblVon)
							.addComponent(startStunde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblUhr)
							.addComponent(startMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxTafel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxSmartboard)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxWhiteboard)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxComputerraum))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBis)
								.addComponent(endStunde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1)
								.addComponent(endMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboKapazitaet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMindestkapazitt))
							.addGap(18)
							.addComponent(btnSuchen)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		this.setLayout(gl_panel);

        btnSuchen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean beamer, ohp, tafel, smartb, whiteb, computerr;

                try {
                	c.setTime(sdf.parse(datumTextField.getText()));
                	c.set(Calendar.HOUR_OF_DAY, Integer.parseInt((String)startStunde.getSelectedItem()));
                	c.set(Calendar.MINUTE, Integer.parseInt((String)startMinute.getSelectedItem()));
                	start = c.getTime();
                	
                	c.set(Calendar.HOUR_OF_DAY, Integer.parseInt((String)endStunde.getSelectedItem()));
                	c.set(Calendar.MINUTE, Integer.parseInt((String)endMinute.getSelectedItem()));
                	ende = c.getTime();
                	
                	if(chckbxBeamer.isSelected()) beamer = true;
                    else beamer = false;
                    if(chckbxOhp.isSelected()) ohp = true;
                    else ohp = false;
                    if(chckbxTafel.isSelected()) tafel = true;
                    else tafel = false;
                    if(chckbxSmartboard.isSelected()) smartb = true;
                    else smartb = false;
                    if(chckbxWhiteboard.isSelected()) whiteb = true;
                    else whiteb = false;
                    if(chckbxComputerraum.isSelected()) computerr = true;
                    else computerr = false;
                	
                	frame.sucheNachKriterien(start, ende, beamer, ohp, tafel, smartb, whiteb, computerr, Integer.parseInt((String)comboKapazitaet.getSelectedItem()));
                	
                } catch (ParseException ex) {
                	JOptionPane.showMessageDialog(frame,
                            "Bitte das Datum im Format TT.MM.JJJ eingeben!",
                            "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}