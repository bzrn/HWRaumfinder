package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class LoginPanel extends JPanel {

    private JTextField username, password;

    public LoginPanel (){
        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel();
            ImageIcon logo = new ImageIcon("images/HWRaumfinder_Logo.png");
            logoLabel.setIcon(logo);
            logoLabel.setHorizontalAlignment(JTextField.CENTER);
        logoLabel.setBorder(new EmptyBorder(20,15,40,15));
        add(logoLabel, BorderLayout.NORTH);
        JPanel loginPanel = new JPanel(new GridLayout(3,1,0,10));
            ActionListener loginListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GUIFrame.getInstance().login(username.getText(), password.getText());
                }
            };
            username = new HintTextField("Nutzername");
                username.setHorizontalAlignment(JTextField.CENTER);
                username.addActionListener(loginListener);
            loginPanel.add(username);
            password = new HintPasswordField("Password");
                password.setHorizontalAlignment(JTextField.CENTER);
                password.addActionListener(loginListener);
            loginPanel.add(password);
            JButton loginBtn = new JButton("Login");
            loginBtn.addActionListener(loginListener);
            loginBtn.requestFocus();
            loginPanel.add(loginBtn);

        loginPanel.setBorder(new EmptyBorder(0,20,0,20));
        add(loginPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(10,0));
        JButton pwVergessenBtn = new JButton ("Passwort-Reset");
        pwVergessenBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	GUIFrame.getInstance().startPwReset();
            }
        });
        footer.add(pwVergessenBtn, BorderLayout.WEST);
        JLabel registrierLabel = new JLabel("Noch keinen Account?  →", SwingConstants.CENTER);
        footer.add(registrierLabel, BorderLayout.CENTER);
        JButton registrierButton = new JButton("Registrieren");
        registrierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUIFrame.getInstance().aktualisiereAnsicht(GUIFrame.REGISTRIERUNG);
            }
        });
        footer.add(registrierButton, BorderLayout.EAST);
        footer.setBorder(new EmptyBorder(10,20,20,20));
        add(footer, BorderLayout.SOUTH);
        setVisible(true);
    }
}

/**
 * Erstellt von Bart Kiers am 13.08.2013.
 * Heruntergeladen von https://stackoverflow.com/questions/1738966/java-jtextfield-with-input-hint.
 */
class HintTextField extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}

/**
 * In Anlehnung an HintTextField von Bart Kiers.
 */
class HintPasswordField extends JPasswordField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintPasswordField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}