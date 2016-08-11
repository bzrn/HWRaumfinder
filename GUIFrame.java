package Oberflaeche;

import Verarbeitung.Raumfinder;
import Verarbeitung.Zeitraum;
import Verarbeitung.Reservierer;
import Verarbeitung.UnzulaessigerZeitraumException;
import VerarbeitungInterfaces.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexander on 26.07.2016.
 */
 class GUIFrame extends JFrame{
	 
	// Singleton-Implementierung:
	private static GUIFrame ourInstance = new GUIFrame();
	 
	// Attribute:
    private NutzerIF aktiverNutzer;       // IF
    private RaumfinderIF rf;
    private Zeitraum tempZeitraum=null;	// IF

    private JPanel aktuelleAnsicht, header, contentPanel, fixedPanel;
    private JLabel titelLabel;
    private JButton headerBtn;
    private String[] panelArgs;
    private String aktuelleAnsichtStr;
    private Timer automatischSpeichern;

    // Konstanten:
    static final String[] min={"00","15","30","45"};
    static final String[] hour={"08","09","10","11","12","13","14","15","16","17","18","19"};
    static final String[] kapazitaet={"0", "10","15", "20", "25", "30"};
    static final String
            HAUPTMENUE = "Hauptmenü",
            LOGIN = "Log-In",
            REGISTRIERUNG = "Registrierung",
            KRITERIENSUCHE = "Suche nach Kriterien",
            ERGEBNISANZEIGE = "Suchergebnisse",
            RESERVIERUNG = "Raumreservierung",
            RESERVIERUNGSVERWALTUNG = "Reservierungsverwaltung",
            PASSWORTAENDERUNG = "Passwort ändern",
            SICHERHEITSFRAGE = "Sicherheitsfrage ändern",
            RAUMVERWALTUNG = "Raumverwaltung",
            NUTZERVERWALTUNG = "Nutzerverwaltung",
    		PASSWORTRESET = "Passwort zurücksetzen";


     // Singleton-Konstruktor
     private GUIFrame() {
        super("HWRaumfinder");
        rf = Raumfinder.getInstance();
        aktiverNutzer = null;

        initialize(LOGIN);

        automatischSpeichern = new Timer(300000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
         automatischSpeichern.start();
    }
     
     // Singleton-getInstance()
    public static GUIFrame getInstance() {
    	return ourInstance;
    }

    private void initialize(String AnsichtsKonstante) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            System.err.println("Exception at setting of look and feel");
        }

        setLayout(new BorderLayout());
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setIconImage(new ImageIcon("HWRaumfinder_Icon.png").getImage());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    if (showExitDialog()==JOptionPane.OK_OPTION){
                    	save();
                        System.out.println("Programm geschlossen.");
                        System.exit(0);
                    }
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        fixedPanel = new JPanel(new GridBagLayout());

        contentPanel = new JPanel (new BorderLayout(20,20));
        contentPanel.setPreferredSize(new Dimension(520,300));
        contentPanel.setBorder(new LineBorder(Color.black, 2, true));

        setAktuelleAnsicht(AnsichtsKonstante);
        aktuelleAnsicht.setBorder(new EmptyBorder(0, 10, 5, 5));
        contentPanel.add(aktuelleAnsicht, BorderLayout.CENTER);

        header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(10, 10, 0, 5));
        contentPanel.add(header, BorderLayout.NORTH);

            titelLabel = new JLabel(AnsichtsKonstante);
            titelLabel.setFont(new Font("Corbel", Font.BOLD, 20));
            header.add(titelLabel, BorderLayout.CENTER);

            headerBtn = new JButton(HAUPTMENUE);
            headerBtn.addActionListener(e -> {

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
           });
            header.add(headerBtn, BorderLayout.EAST);
        fixedPanel.add(contentPanel);
        add(fixedPanel, BorderLayout.CENTER);
        setVisible(true);
        header.setVisible(false);
    }

    private void setAktuelleAnsicht (String AnsichtsKonstante){

        aktuelleAnsichtStr=AnsichtsKonstante;

        switch (AnsichtsKonstante) {
            case HAUPTMENUE:
                panelArgs = new String[rf.getRaeume().size()];
                for (int i=0; i<panelArgs.length; i++){
                    panelArgs[i] = rf.getRaeume().get(i).getRaumBezeichnung();
                }
                aktuelleAnsicht = new HauptmenuePanel(panelArgs);
                break;
            case LOGIN:
                aktuelleAnsicht = new LoginPanel();
                break;
            case REGISTRIERUNG:
                aktuelleAnsicht = new RegistrierungsPanel();
                break;
            case KRITERIENSUCHE:
                aktuelleAnsicht = new SuchPanel();
                break;
            case ERGEBNISANZEIGE:
                aktuelleAnsicht = new SuchErgebnisPanel(panelArgs);
                break;
            case RESERVIERUNG:
                aktuelleAnsicht = new ReservierungsPanel();
                break;
            case RESERVIERUNGSVERWALTUNG:
            	if (aktiverNutzerIsAdmin()){
            		panelArgs = new String[rf.getReservierungen().size()];
                    for (int i=0; i<panelArgs.length; i++){
                        ReservierungIF tempRes = rf.getReservierungen().get(i);
                        panelArgs[i] = ("("+Long.toString(tempRes.getReservierungsNr()) + ")  " + (tempRes.getZeitraum()).toString() + " für Raum " + tempRes.getRaum() + " Reservierer: " + tempRes.getInhaber().getName());
                        if (tempRes.isStorniert()) panelArgs[i] += " <sto>";
                        if (tempRes.isError()) panelArgs[i] += " <err>";
                    }
            	} else {
            		panelArgs = new String[((StandardNutzerIF)aktiverNutzer).getReservierungen().size()];
                    for (int i=0; i<panelArgs.length; i++){
                    	ReservierungIF tempRes = ((StandardNutzerIF)aktiverNutzer).getReservierungen().get(i);
                    	panelArgs[i] = ("("+Long.toString(tempRes.getReservierungsNr()) + ")  " + tempRes.getZeitraum().toString() + " für Raum " + tempRes.getRaum());
                    }
            	}
                aktuelleAnsicht = new ReservierungsverwaltungsPanel(panelArgs);
                break;
            case PASSWORTAENDERUNG:
                aktuelleAnsicht = new PasswortaenderungsPanel();
                break;
            case SICHERHEITSFRAGE:
                panelArgs = new String[]{aktiverNutzer.getSicherheitsFrage()};
                aktuelleAnsicht = new SicherheitsfragenaenderungsPanel(panelArgs);
                break;
            case RAUMVERWALTUNG:
                panelArgs = new String[rf.getRaeume().size()];
                for (int i=0; i<panelArgs.length; i++){
                    panelArgs[i] = rf.getRaeume().get(i).getRaumBezeichnung();
                }
            	aktuelleAnsicht = new RaumverwaltungsPanel(panelArgs);
            	break;
            case NUTZERVERWALTUNG:
            	panelArgs = rf.getNutzerString();
            	aktuelleAnsicht = new NutzerverwaltungsPanel(panelArgs);
            	break;
            case PASSWORTRESET:
            	panelArgs = new String[] {aktiverNutzer.getSicherheitsFrage()};
            	aktuelleAnsicht = new PasswortresetPanel(panelArgs);
            	break;
            default:
                System.err.println("Interner Fehler: Seite existiert nicht.");
                break;
        }
    }

     void aktualisiereAnsicht (String AnsichtsKonstante) {

        contentPanel.remove(aktuelleAnsicht);
        setAktuelleAnsicht(AnsichtsKonstante);
        contentPanel.add(aktuelleAnsicht,BorderLayout.CENTER);
        contentPanel.repaint();
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

     void login (String name, String password) {
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

     void registrieren (String name, String password, String sicherheitsFrage, String sicherheitsAntwort){
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

    void erstelleleerrenAdmin () {
        rf.legeNutzerAn("Leerer Admin", "xxxx", "Sicherheitsfrage definieren!", "xxxx", true);
        JOptionPane.showMessageDialog(this,
                "Leerer Admin wurde erstellt, bitte bearbeiten!",
                "Erfolg",
                JOptionPane.INFORMATION_MESSAGE);
        aktualisiereAnsicht(HAUPTMENUE);
        aktualisiereAnsicht(NUTZERVERWALTUNG);
    }

     void startPwReset(){
    	String nutzername = JOptionPane.showInputDialog(this,
    								"Bitte Nutzernamen eingeben:",
    								"Passwort zurücksetzen",
    								JOptionPane.OK_CANCEL_OPTION);
    	if (nutzername!=null){
    		NutzerIF tempNutzer = rf.sucheNutzer(nutzername);
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

     void setzePasswortZurueck (String sicherheitsAntwort, String neuPW){
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

     void sucheNachKriterien (Date start, Date ende, boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet){
    	try {
            ArrayList<String> tempPanelArgs = rf.suche(start, ende,beamer,ohp,tafel,smartb,whiteb,computerr, kapazitaet);
            panelArgs = tempPanelArgs.toArray(new String[tempPanelArgs.size()]);
            aktualisiereAnsicht(ERGEBNISANZEIGE);
        } catch (UnzulaessigerZeitraumException e) {
            JOptionPane.showMessageDialog(this,
                    "Der Beginn des Reservierungszeitraum muss vor dessen Ende liegen!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

     String[] getRaumDaten (String raumKennung) {
        RaumIF r = rf.sucheKennung(raumKennung);

        if (r == null) return new String[] {"","","","","","","","",""};
        else {
            AusstattungIF a = r.getAusstattung();
            return new String[] {r.getRaumBezeichnung(), Integer.toString(a.getKapazitaet()), Boolean.toString(r.isBuchbar()), Boolean.toString(a.isBeamer()), Boolean.toString(a.isOhp()), Boolean.toString(a.isTafel()), Boolean.toString(a.isSmartboard()), Boolean.toString(a.isWhiteboard()), Boolean.toString(a.isComputerraum())};
        }
    }

     void erstelleLeerenRaum (){
        rf.addRaum("Neuer Raum", false,false,false,false,false,false,0, false);
        JOptionPane.showMessageDialog(this,
                "Leerer Raum wurde erstellt, bitte bearbeiten!",
                "Erfolg",
                JOptionPane.INFORMATION_MESSAGE);
        aktualisiereAnsicht(HAUPTMENUE);
        aktualisiereAnsicht(RAUMVERWALTUNG);
    }

     void bearbeiteRaum (String altName, String neuName, String kapa, boolean buchbar, boolean beamer, boolean ohp, boolean tafel, boolean smartboard, boolean whiteboard, boolean computerraum){
        RaumIF tempRaum = rf.sucheKennung(altName);

        if (tempRaum == null){
            System.err.println("Interner Fehler: Zu ändernder Raum existiert nicht.");
        } else if (rf.sucheKennung(neuName)!=null) {
            JOptionPane.showMessageDialog(this,
                    "Raumname existiert bereits!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            tempRaum.setRaumBezeichnung(neuName);
            tempRaum.setAusstattung(beamer, ohp, tafel, smartboard, whiteboard, computerraum, Integer.parseInt(kapa));
            tempRaum.setBuchbar(buchbar);
        }
        aktualisiereAnsicht(HAUPTMENUE);
        aktualisiereAnsicht(RAUMVERWALTUNG);
    }

     void loescheRaum (String raumKennung) {
        if (rf.sucheKennung(raumKennung) == null) {
            System.err.println("Interner Fehler: Zu löschender Raum existiert nicht.");
        } else {
            rf.loescheRaum(rf.sucheKennung(raumKennung));
        }
        aktualisiereAnsicht(HAUPTMENUE);
        aktualisiereAnsicht(RAUMVERWALTUNG);
    }

     void raumAuswaehlen (String kennung){
        try{
            if (tempZeitraum == null) tempZeitraum=new Zeitraum(new Date(System.currentTimeMillis()), new Date (System.currentTimeMillis()+3600000));
            aktualisiereAnsicht(RESERVIERUNG);
            ((ReservierungsPanel)aktuelleAnsicht).setup(kennung, tempZeitraum.getStart(), tempZeitraum.getEnde(), rf.sucheKennung(kennung).getAusstattung().toStringArray());
        } catch (UnzulaessigerZeitraumException e) {
            System.err.println("Interner Fehler: Default-Zeitraum wurde falsch generiert.");
        }
    }

    boolean pruefeVerfuegbarkeitRaum(String raumKennung, Date start, Date ende){
        try{
            return rf.pruefeVerfuegbarkeitRaum(raumKennung, new Zeitraum(start, ende));
        } catch (UnzulaessigerZeitraumException e) {
            JOptionPane.showMessageDialog(this,
                    "Der Beginn des Reservierungszeitraum muss vor dessen Ende liegen!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

     boolean raumBuchbar (String raumKennung) {
    	return rf.pruefeBuchbarkeitRaum(raumKennung);
    }

     void reservieren(String raumKennung, Date start, Date ende){
         Date jetzt = new Date(System.currentTimeMillis());
         if (ende.after(jetzt)){
             try {
                 Zeitraum zr = new Zeitraum(start, ende);
                 if (rf.reservieren(rf.sucheKennung(raumKennung),(Reservierer)aktiverNutzer,zr)){     //StandardNutzer ist Reservierer
                     JOptionPane.showMessageDialog(this,
                             "Reservierung erfolgreich! \nZurück ins Hauptmenü.",
                             "Erfolg",
                             JOptionPane.INFORMATION_MESSAGE,
                             new ImageIcon("checkMark.png"));
                     aktualisiereAnsicht(HAUPTMENUE);
                 } else {
                     JOptionPane.showMessageDialog(this,
                             "Reservierung konnte nicht gebucht werden. \nMöglicherweise hast du in diesem Zeitraum schon einen Raum reserviert oder der Raum ist nicht buchbar.",
                             "Fehler",
                             JOptionPane.ERROR_MESSAGE);
                 }
             } catch (UnzulaessigerZeitraumException e) {
                 JOptionPane.showMessageDialog(this,
                         "Der Beginn des Reservierungszeitraum muss vor dessen Ende liegen!",
                         "Fehler",
                         JOptionPane.ERROR_MESSAGE);
             }
         } else {
             JOptionPane.showMessageDialog(this,
                     "Reservierung konnte nicht gebucht werden. \nDer Reservierungszeitraum darf nicht in der Vergangenheit liegen.",
                     "Fehler",
                     JOptionPane.ERROR_MESSAGE);
         }

    }

     void passwortAendern(String alt, String neu) {
        if (aktiverNutzer.checkPw(alt)){
            aktiverNutzer.setPwHash(neu);
            JOptionPane.showMessageDialog(this,
                    "Passwort erfolgreich geändert!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon("checkMark.png"));
            aktualisiereAnsicht(HAUPTMENUE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Falsches aktuelles Passwort! \nPasswort konnte nicht geändert werden.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void sicherheitsfrageAendern (String altAW, String neuFrage, String neuAW){
        if (aktiverNutzer.checkFrage(altAW)){
            aktiverNutzer.setSicherheitsFrage(neuFrage);
            aktiverNutzer.setSicherheitsAntwortHash(neuAW);
            JOptionPane.showMessageDialog(this,
                    "Sicherheitsfrage/-antwort erfolgreich geändert!",
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

     void zeigeReservierungsDetails (long reservierungsnr){
    	ReservierungIF tempRes = rf.sucheReservierung(reservierungsnr);		// IF
    	JOptionPane.showMessageDialog(this,
                tempRes.toString(true),
                "Reservierung" + tempRes.getReservierungsNr(),
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon("HWRaumfinder_Icon.png"));
    }

     void storniereReservierung (long reservierungsnr) {
    	Raumfinder.getInstance().stornieren(Raumfinder.getInstance().sucheReservierung(reservierungsnr));
    	JOptionPane.showMessageDialog(this,
                "Reservierung Nummer " + reservierungsnr + " wurde storniert!",
                "Erfolg",
                JOptionPane.WARNING_MESSAGE);
    	aktualisiereAnsicht(HAUPTMENUE);
    	aktualisiereAnsicht(RESERVIERUNGSVERWALTUNG);
    }

     String getAktivenNutzerString(){
        return aktiverNutzer.getName();
    }

     String[] getNutzerDaten (String nutzername){
    	NutzerIF tempNutzer = rf.sucheNutzer(nutzername);		// IF
    	return new String[]{tempNutzer.getName(), tempNutzer.getSicherheitsFrage()};
    }

     void bearbeiteNutzer (String nutzernameAlt, String nutzernameNeu, String passwort, String frage, String antwort) {
    	if (rf.sucheNutzer(nutzernameNeu)!=null){
            JOptionPane.showMessageDialog(this,
                    "Benutzername existiert bereits!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            boolean[] aenderungen = new boolean[4];
            NutzerIF bearbeiteterNutzer = rf.sucheNutzer(nutzernameAlt);		//IF
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

            aktualisiereAnsicht(HAUPTMENUE);
            aktualisiereAnsicht(NUTZERVERWALTUNG);
        }
    }

     void loescheNutzer (String nutzername){
        if (rf.loescheNutzer(rf.sucheNutzer(nutzername))){
            JOptionPane.showMessageDialog(this,
                    "Nutzer "+nutzername+" wurde gelöscht!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Dieser Admin ist nicht entfernbar!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
        aktualisiereAnsicht(HAUPTMENUE);
        aktualisiereAnsicht(NUTZERVERWALTUNG);
    }

     public void save (){
        rf.save();
        System.out.println("Aktuelle Konfiguration gespeichert.");
    }

     public void load () {
        rf.load();
        System.out.println("Gespeicherte Konfiguration geladen.");
    }

    public boolean aktiverNutzerIsAdmin() {
        if (aktiverNutzer.isAdmin()) return true;
        else return false;
    }

    private int showExitDialog (){
        if (aktiverNutzerIsAdmin()){
            return JOptionPane.showConfirmDialog(this,
                    "Programm wird gespeichert und geschlossen!",
                    "Byebye",
                    JOptionPane.OK_CANCEL_OPTION);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nur ein Adminisrator kann den HWRaumfinder schließen!",
                    "Zugriff verweigert",
                    JOptionPane.WARNING_MESSAGE);
            return JOptionPane.CANCEL_OPTION;
        }
    }
}
