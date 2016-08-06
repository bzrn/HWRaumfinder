package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginPanel extends JPanel {

    private GUIFrame frame;

    public LoginPanel (GUIFrame parent){
        frame = parent;
        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel();
            ImageIcon logo = new ImageIcon("HWRaumfinder_Logo.png");
            logoLabel.setIcon(logo);
            logoLabel.setHorizontalAlignment(JTextField.CENTER);
        logoLabel.setBorder(new EmptyBorder(20,15,40,15));
        add(logoLabel, BorderLayout.NORTH);
        JPanel loginPanel = new JPanel(new GridLayout(3,1,0,10));
            JTextField username = new JTextField("Nutzername");
                username.setHorizontalAlignment(JTextField.CENTER);
            loginPanel.add(username);
            JPasswordField password = new JPasswordField("Password");
                password.setHorizontalAlignment(JTextField.CENTER);
            loginPanel.add(password);
            JButton loginBtn = new JButton("Login");
            loginBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        frame.login(username.getText(), password.getText());
                    }
                });
            loginPanel.add(loginBtn);
        loginPanel.setBorder(new EmptyBorder(0,20,0,20));
        add(loginPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridLayout(1,3,20,0));
        JButton pwVergessenBtn = new JButton ("Passwort-Reset");
        pwVergessenBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.startPwReset();
            }
        });
        footer.add(pwVergessenBtn);
        JLabel registrierLabel = new JLabel("Noch keinen Account?");
        footer.add(registrierLabel);
        JButton registrierButton = new JButton("Registrieren");
        registrierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.aktualisiereAnsicht(GUIFrame.REGISTRIERUNG);
            }
        });
        footer.add(registrierButton);
        footer.setBorder(new EmptyBorder(10,20,20,20));
        add(footer, BorderLayout.SOUTH);
        setVisible(true);
    }
}
