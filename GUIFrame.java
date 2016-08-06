package Oberflaeche;

import Verarbeitung.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexander on 26.07.2016.
 */
public class GUIFrame extends JFrame{

    private Nutzer aktiverNutzer;       // IF
    private Raumfinder rf;              // IF
    private Zeitraum tempZeitraum=null;	// IF
    
    private JPanel aktuelleAnsicht, header;
    private JLabel titelLabel;
    private JButton headerBtn;
    private String[] panelArgs;
    private String aktuelleAnsichtStr;

    //AnsichtsKonstanten:
    public static final String
            HAUPTMENUE = "Hauptmenü",
            LOGIN = "Log-In",
            REGISTRIERUNG = "Registrierung",
            KRITERIENSUCHE = "Kriteriensuche",
            ERGEBNISANZEIGE = "Suchergebnisse",
            RESERVIERUNG = "Raumreservierung",
            RESERVIERUNGSVERWALTUNG = "Reservierungsverwaltung",
            PASSWORTAENDERUNG = "Passwort ändern",
            SICHERHEITSFRAGE = "Sicherheitsfrage ändern",
            RAUMVERWALTUNG = "Raumverwaltung",
            NUTZERVERWALTUNG = "Nutzerverwaltung",
    		PASSWORTRESET = "Passwort zurücksetzen";

    public static final String[] min={"00","15","30","45"};
    public static final String[] hour={"08","09","10","11","12","13","14","15","16","17","18","19"};
    public static final String[] kapazitaet={"0", "10","15", "20", "25", "30"};


    public GUIFrame(Raumfinder rf) {     // IF
        super("HWRaumfinder");
        this.rf = rf;
        aktiverNutzer = null;
        initialize(LOGIN);
    }

    private void initialize(String AnsichtsKonstante) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            System.err.println("Exception at setting of look and feel");
        }

        setLayout(new BorderLayout(20,20));
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon("HWRaumfinder_Icon.png").getImage());

        setAktuelleAnsicht(AnsichtsKonstante);
        aktuelleAnsicht.setBorder(new EmptyBorder(0, 10, 5, 5));
        add(aktuelleAnsicht, BorderLayout.CENTER);

        header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(10, 10, 0, 5));
        add(header, BorderLayout.NORTH);

            titelLabel = new JLabel(AnsichtsKonstante);
            titelLabel.setFont(new Font("Corbel", Font.BOLD, 20));
            header.add(titelLabel, BorderLayout.CENTER);

            headerBtn = new JButton(HAUPTMENUE);
            headerBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    switch (aktuelleAnsichtStr){
                        case HAUPTMENUE:
                            logout();
                            break;
                        case PASSWORTRESET:
                        case REGISTRIERUNG:
                            aktualisiereAnsicht(LOGIN);
                            break;
                        default:
                            aktualisiereAnsicht(HAUPTMENUE);
                            break;
                    }
                }
            });
            header.add(headerBtn, BorderLayout.EAST);

        setVisible(true);
        header.setVisible(false);
    }

    private void setAktuelleAnsicht (String AnsichtsKonstante){

        aktuelleAnsichtStr=AnsichtsKonstante;

        switch (AnsichtsKonstante) {
            case HAUPTMENUE:
                aktuelleAnsicht = new HauptmenuePanel(this);
                break;
            case LOGIN:
                aktuelleAnsicht = new LoginPanel(this);
                break;
            case REGISTRIERUNG:
                aktuelleAnsicht = new RegistrierungsPanel(this);
                break;
            case KRITERIENSUCHE:
                aktuelleAnsicht = new SuchPanel(this);
                break;
            case ERGEBNISANZEIGE:
                aktuelleAnsicht = new SuchErgebnisPanel(this, panelArgs);
                break;
            case RESERVIERUNG:
                aktuelleAnsicht = new ReservierungsPanel(this);
                break;
            case RESERVIERUNGSVERWALTUNG:
            	if (aktiverNutzerIsAdmin()){
            		panelArgs = new String[rf.getReservierungen().size()];
                    for (int i=0; i<panelArgs.length; i++){
                    	Reservierung tempRes = rf.getReservierungen().get(i);
                    	panelArgs[i] = ("("+Long.toString(tempRes.getReservierungsNr()) + ")  " + tempRes.getZeitraum().toString() + " für Raum " + tempRes.getRaum() + " Reservierer: " + tempRes.getInhaber().getName());
                    	if (tempRes.isStorniert()) panelArgs[i] += " <sto>";
                    	if (tempRes.isError()) panelArgs[i] += " <err>";
                    }
            	} else {
            		panelArgs = new String[((StandardNutzer)aktiverNutzer).getReservierungen().size()];
                    for (int i=0; i<panelArgs.length; i++){
                    	Reservierung tempRes = ((StandardNutzer)aktiverNutzer).getReservierungen().get(i);
                    	panelArgs[i] = ("("+Long.toString(tempRes.getReservierungsNr()) + ")  " + tempRes.getZeitraum().toString() + " für Raum " + tempRes.getRaum());
                    }
            	}
                aktuelleAnsicht = new ReservierungsverwaltungsPanel(this,panelArgs);
                break;
            case PASSWORTAENDERUNG:
                aktuelleAnsicht = new PasswortaenderungsPanel(this);
                break;
            case SICHERHEITSFRAGE:
                panelArgs = new String[]{aktiverNutzer.getSicherheitsFrage()};
                aktuelleAnsicht = new SicherheitsfragenaenderungsPanel(this, panelArgs);
                break;
            case RAUMVERWALTUNG:
            	// Hier fehlt!
            	break;
            case NUTZERVERWALTUNG:
            	panelArgs = rf.getNutzerString();
            	aktuelleAnsicht = new NutzerverwaltungsPanel(this, panelArgs);
            	break;
            case PASSWORTRESET:
            	panelArgs = new String[] {aktiverNutzer.getSicherheitsFrage()};
            	aktuelleAnsicht = new PasswortresetPanel(this, panelArgs);
            	break;
            default:
                System.err.println("Error: Seite existiert nicht.");
                break;
        }
    }

    public void aktualisiereAnsicht (String AnsichtsKonstante) {
        getContentPane().remove(aktuelleAnsicht);
        setAktuelleAnsicht(AnsichtsKonstante);
        add(aktuelleAnsicht,BorderLayout.CENTER);
        repaint();
        header.setVisible(true);

        titelLabel.setText(AnsichtsKonstante);

        if (aktuelleAnsicht instanceof HauptmenuePanel) headerBtn.setText("Log-Out");
        else headerBtn.setText(HAUPTMENUE);

        if (aktuelleAnsicht instanceof LoginPanel) {
            header.setVisible(false);
        } else if (aktuelleAnsicht instanceof RegistrierungsPanel || aktuelleAnsicht instanceof PasswortresetPanel) {
            headerBtn.setText("zurück zum Log-In");
            header.setVisible(true);
        } else if (aktiverNutzer == null) {
            JOptionPane.showMessageDialog(this,
                    "Diese Ansicht ist nur für eingeloggte Nutzer erreichbar.",
                    "Zugriff verweigert",
                    JOptionPane.ERROR_MESSAGE);
            aktualisiereAnsicht(LOGIN);
        } else {
            header.setVisible(true);
        }
    }

    public void login (String name, String password) {
        aktiverNutzer = rf.authentifiziereNutzer(name, password);
        if (aktiverNutzer == null) {
            JOptionPane.showMessageDialog(this,
                    "Nutzername oder Passwort sind fehlerhaft.",
                    "Log-In nicht möglich",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            aktualisiereAnsicht(HAUPTMENUE);
        }
    }
    
    private void logout(){
    	aktiverNutzer = null;
        aktualisiereAnsicht(LOGIN);
        JOptionPane.showMessageDialog(this,
                "Erfolgreich ausgeloggt!",
                "Log-Out",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void registrieren (String name, String password, String sicherheitsFrage, String sicherheitsAntwort){
        if (rf.sucheNutzer(name)==null){
        	rf.legeNutzerAn(name,password,sicherheitsFrage,sicherheitsAntwort,false);
            JOptionPane.showMessageDialog(this,
                    "Nutzeraccount erstellt!\nDu kannst dich nun einloggen!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("checkMark.png"));
            aktualisiereAnsicht(LOGIN);
        } else {
        	JOptionPane.showMessageDialog(this,
                    "Nutzer existiert bereits.",
                    "Registrierung fehlgeschlagen",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void startPwReset(){
    	String nutzername = JOptionPane.showInputDialog(this,
    								"Bitte Nutzernamen eingeben:",
    								"Passwort zurücksetzen",
    								JOptionPane.OK_CANCEL_OPTION);
    	if (nutzername!=null){
    		Nutzer tempNutzer = rf.sucheNutzer(nutzername);
        	if (tempNutzer==null){
        		JOptionPane.showMessageDialog(this,
                        "Nutzer existiert nicht. Bitte erneut versuchen oder den Admin kontaktieren.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
        	} else {
        		aktiverNutzer = tempNutzer;		// aktiverNutzer gesetzt, alter Kommentar: aktiverNutzer temporär gesetzt, wird bei aktualisiereAnsicht wieder entfernt
        		aktualisiereAnsicht(PASSWORTRESET);
        	}
    	}
    }
    
    public void setzePasswortZurueck (String sicherheitsAntwort, String neuPW){
    	if(aktiverNutzer.checkFrage(sicherheitsAntwort)){		// wenn Antwort stimmt
    		aktiverNutzer.setPwHash(neuPW);
    		JOptionPane.showMessageDialog(this,
                    "Passwort zurückgesetzt!\nDu kannst dich nun einloggen!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("checkMark.png"));
            aktualisiereAnsicht(LOGIN);
    	} else {
    		JOptionPane.showMessageDialog(this,
                    "Die Antwort auf deine Sicherheitsfrage war falsch.\nBitte nochmal versuchen!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
    	}
    }

    public void sucheNachKriterien (Date start, Date ende, boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet){
    	tempZeitraum = new Zeitraum (start, ende);	 //merken bis zur Raumansicht, damit als default-Wert in Reservierungsmaske eingetragen werden kann
        ArrayList<String> tempPanelArgs = rf.suche(tempZeitraum, new Ausstattung(beamer,ohp,tafel,smartb,whiteb,computerr, kapazitaet));
        panelArgs = tempPanelArgs.toArray(new String[tempPanelArgs.size()]);
        aktualisiereAnsicht(ERGEBNISANZEIGE);
    }

    public void raumAuswaehlen (String kennung){
        if (tempZeitraum == null) {
            /*JOptionPane.showMessageDialog(this,
                    "Interner Fehler. Bitte nochmal versuchen.",
                    "Interner Fehler",
                    JOptionPane.ERROR_MESSAGE);
            aktualisiereAnsicht(HAUPTMENUE);*/		// raus, weil falsch wenn Aufruf über Kennungssuche
        	tempZeitraum=new Zeitraum(new Date(System.currentTimeMillis()), new Date (System.currentTimeMillis()+3600000));
        } else {
            aktualisiereAnsicht(RESERVIERUNG);
            ((ReservierungsPanel)aktuelleAnsicht).setup(kennung, tempZeitraum.getStart(), tempZeitraum.getEnde(), rf.sucheKennung(kennung).getAusstattung().toStringArray());
            getContentPane().remove(aktuelleAnsicht);	// nicht schön,
            add(aktuelleAnsicht,BorderLayout.CENTER);	// aber funktio-
            repaint();									// niert so
        }
    }

    public boolean pruefeVerfuegbarkeitRaum (String raumKennung, Date start, Date ende){
        return rf.pruefeVerfuegbarkeitRaum(raumKennung, new Zeitraum(start, ende));
    }
    
    public boolean raumBuchbar (String raumKennung) {
    	return rf.pruefeBuchbarkeitRaum(raumKennung);
    }

    public void reservieren(String raumKennung, Date start, Date ende){
    	Zeitraum zr = new Zeitraum (start, ende);
        if (rf.reservieren(rf.sucheKennung(raumKennung),(Reservierer)aktiverNutzer,zr)){
            JOptionPane.showMessageDialog(this,
                    "Reservierung erfolgreich! \nZurÃ¼ck ins HauptmenÃ¼.",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("checkMark.png"));
            aktualisiereAnsicht(HAUPTMENUE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Reservierung konnte nicht gebucht werden. \nMÃ¶glicherweise hast du in diesem Zeitraum schon einen Raum reserviert oder der Raum ist nicht buchbar.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void passwortAendern(String alt, String neu) {
        if (aktiverNutzer.checkPw(alt)){
            aktiverNutzer.setPwHash(neu);
            JOptionPane.showMessageDialog(this,
                    "Passwort erfolgreich geÄndert!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("checkMark.png"));
            aktualisiereAnsicht(HAUPTMENUE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Falsches aktuelles Passwort! \nPasswort konnte nicht geÄndert werden.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sicherheitsfrageAendern (String altAW, String neuFrage, String neuAW){
        if (aktiverNutzer.checkFrage(altAW)){
            aktiverNutzer.setSicherheitsFrage(neuFrage);
            aktiverNutzer.setSicherheitsAntwortHash(neuAW);
            JOptionPane.showMessageDialog(this,
                    "Sicherheitsfrage/-antwort erfolgreich geÄndert!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("checkMark.png"));
            aktualisiereAnsicht(HAUPTMENUE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Falsche Antwort auf aktuelle Sicherheitsfrage! \nÄnderung konnte nicht durchgeführt werden.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void zeigeReservierungsDetails (long reservierungsnr){
    	Reservierung tempRes = rf.sucheReservierung(reservierungsnr);		// IF
    	JOptionPane.showMessageDialog(this,
                tempRes.toString(true),
                "Reservierung" + tempRes.getReservierungsNr(),
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon("HWRaumfinder_Icon.png"));
    }
    
    public void storniereReservierung (long reservierungsnr) {
    	Reservierung tempRes = rf.sucheReservierung(reservierungsnr);
    	rf.stornieren(tempRes);
    	JOptionPane.showMessageDialog(this,
                "Reservierung Nummer " + tempRes.getReservierungsNr() + " wurde storniert!",
                "Erfolg",
                JOptionPane.WARNING_MESSAGE);
    	aktualisiereAnsicht(HAUPTMENUE);
    	aktualisiereAnsicht(RESERVIERUNGSVERWALTUNG);
    }

    public Nutzer getAktivenNutzer(){
        return aktiverNutzer;
    }
    
    public String getAktivenNutzerString(){
        return aktiverNutzer.getName();
    }
    
    public boolean aktiverNutzerIsAdmin(){
    	if (aktiverNutzer instanceof Admin) return true;
    	else return false;
    }
    
    public String[] getNutzerDaten (String nutzername){
    	Nutzer tempNutzer = rf.sucheNutzer(nutzername);		// IF
    	return new String[]{tempNutzer.getName(), tempNutzer.getSicherheitsFrage()};
    }
    
    public void bearbeiteNutzer (String nutzernameAlt, String nutzernameNeu, String passwort, String frage, String antwort) {
    	boolean[] aenderungen = new boolean[4];
    	Nutzer bearbeiteterNutzer = rf.sucheNutzer(nutzernameAlt);		//IF
        if (!nutzernameAlt.equals(nutzernameNeu)) {
        	bearbeiteterNutzer.setName(nutzernameNeu);
        	aenderungen[0] = true;
        } else aenderungen[0] = false;
        if (!passwort.isEmpty()) {
        	bearbeiteterNutzer.setPwHash(passwort);
        	aenderungen[1] = true;
        } else aenderungen[1] = false;
        if (!frage.equals(bearbeiteterNutzer.getSicherheitsFrage())) {
        	bearbeiteterNutzer.setSicherheitsFrage(frage);
        	aenderungen[2] = true;
        } else aenderungen[3] = false;
        if (!antwort.isEmpty()) {
        	bearbeiteterNutzer.setSicherheitsAntwortHash(antwort);
        	aenderungen[3] = true;
        } else aenderungen [3] = false;
        
        String bearbeitungsMessage = "Folgende Nutzerdaten wurden geändert:\n";
        if (aenderungen[0]) bearbeitungsMessage += "\tNutzername\n";
        if (aenderungen[1]) bearbeitungsMessage += "\tPasswort\n";
        if (aenderungen[2]) bearbeitungsMessage += "\tSicherheitsfrage\n";
        if (aenderungen[3]) bearbeitungsMessage += "\tSicherheitsantwort\n";
        
        JOptionPane.showMessageDialog(this,
                bearbeitungsMessage,
                "Nutzerdaten geändert",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void loescheNutzer (String nutzername){
    	rf.loescheNutzer(rf.sucheNutzer(nutzername));
    	JOptionPane.showMessageDialog(this,
                "Nutzer "+nutzername+" wurde gelöscht!",
                "Erfolg",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public Raumfinder getRaumfinder(){		// IF
        return rf;
    }
}
