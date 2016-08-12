package Verarbeitung;

import VerarbeitungInterfaces.RaumIF;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <strong>Zweck:</strong> Ermöglicht die Erstellung von Raum-Objekten, denen ein Name, eine bestimmte Ausstattung sowie Informationen über Buchbarkeit und Belegung zugeordnet sind. 
 * <p><strong>package Verarbeitung;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * <strong/>Zweck:</strong> Definiert einen Nutzer, dem Name, verschlüsseltes Passwort, eine Sicherheitsfrage und die verschlüsselte Antwort darauf zugeordnet sind
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 2.5
 * @author Alexander Reichenbach
 *
 */

public abstract class Nutzer implements VerarbeitungInterfaces.NutzerIF, Serializable {

    // Attribute
	private static final long serialVersionUID = 6677969877407899039L;
	
	protected String name, pwHash, sicherheitsFrage, sicherheitsAntwortHash;
    protected static MessageDigest messageDigest;		

    // Konstruktor
    protected Nutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {

        this.name=name;
        this.sicherheitsFrage = sicherheitsFrage;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            setPwHash(password);
            setSicherheitsAntwortHash(sicherheitsAntwort);
        } catch (NoSuchAlgorithmException wtf){
            System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
        }
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String password) {
    	if (messageDigest==null) {
    		try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException wtf){
                System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
            }
    	}
        messageDigest.update(password.getBytes());
        pwHash = new String(messageDigest.digest());
    }

    public String getSicherheitsFrage() {
        return sicherheitsFrage;
    }

    public void setSicherheitsFrage(String sicherheitsFrage) {
        this.sicherheitsFrage = sicherheitsFrage;
    }

    public String getSicherheitsAntwortHash() {
        return sicherheitsAntwortHash;
    }

    public void setSicherheitsAntwortHash(String sicherheitsAntwort) {
    	if (messageDigest==null) {
    		try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException wtf){
                System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
            }
    	}
        messageDigest.update(sicherheitsAntwort.getBytes());
        sicherheitsAntwortHash = new String(messageDigest.digest());
    }


    /**
     *<p><strong>Vorbedingungen:</strong> Es muss ein Passwort übergeben werden und einen Nutzer geben, auf den die Methode angewandt wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Passwort-Hash des Nutzers mit dem Hash des übergebenen Passwortes übereinstimmt.</p>
     * @param password übergebener String Passwort
     * @return <strong>true</strong> wenn die Passwort-Hashes übereinstimmen und <strong>false</strong>, falls das nicht der Fall ist
     */

    public boolean checkPw (String password){
    	if (messageDigest==null) { //Fehlermeldung wird generiert, falls kein Passwort-Hash vorhanden ist
    		try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException wtf){
                System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
            }
    	}
        messageDigest.update(password.getBytes());
        if (pwHash.equals(new String (messageDigest.digest()))) return true; //prüft, ob Passwort-Hash des Nutzers mit dem Hash des übergebenen Passwortes übereinstimmt
        else return false;
    }


    /**
     *<p><strong>Vorbedingungen:</strong> Es muss die Antwort auf eine Sicherheitsfrage übergeben werden und einen Nutzer geben, auf den die Methode angewandt wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Antwort-Hash des Nutzers mit dem Hash der übergebenen Antwort übereinstimmt.</p>
     * @param antwort übergebener String Antwort auf Sicherheitsfrage
     * @return <strong>true</strong> wenn die Antwort-Hashes übereinstimmen und <strong>false</strong>, falls das nicht der Fall ist
     */

    public boolean checkFrage (String antwort){
    	if (messageDigest==null) { //Fehlermeldung wird generiert, falls kein Antwort-Hash vorhanden ist
    		try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException wtf){
                System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
            }
    	}
        messageDigest.update(antwort.getBytes());
        if (sicherheitsAntwortHash.equals(new String (messageDigest.digest()))) return true; //prüft, ob Antwort-Hash des Nutzers mit dem Hash der übergebenen Antwort übereinstimmt
        else return false;
    }

    public boolean isAdmin() {
        if (this instanceof Admin) return true;
        else return false;
    }
}
:</strong></p>
 * @version 3.2
 * @author Alexander Reichenbach
 *
 */
public class Raum implements VerarbeitungInterfaces.RaumIF, Serializable {

    // Attribute
	
	private static final long serialVersionUID = -1970365805954633424L;
	
	private String raumBezeichnung;
    private ArrayList<Reservierung> belegung;
    private Ausstattung ausstattung;
    private boolean buchbar;


    // Konstruktor
    
    public Raum(String raumBezeichnung, Ausstattung ausstattung, boolean buchbar){
        this.raumBezeichnung = raumBezeichnung;
        this.belegung = new ArrayList<Reservierung>();
        this.ausstattung=ausstattung;
        this.buchbar = buchbar;
    }

    // Getter und Setter
    
    public String getRaumBezeichnung() {
        return raumBezeichnung;
    }

    public void setRaumBezeichnung(String raumBezeichnung) {
        this.raumBezeichnung = raumBezeichnung;
    }

    public ArrayList<Reservierung> getBelegung() {
        return belegung;
    }

    public Ausstattung getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(Ausstattung ausstattung) {
        this.ausstattung = ausstattung;
    }

    public void setAusstattung (boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet){
        setAusstattung(new Ausstattung(beamer, ohp, tafel, smartb, whiteb, computerr, kapazitaet));
    }

    public boolean isBuchbar() {
        return buchbar;
    }

    public void setBuchbar (boolean buchbar) { this.buchbar = buchbar; }


    /**
     *
     * @param a Ausstattung
     * @return
     */

    public int hatMindestausstattung (Ausstattung a) {
    	return ausstattung.hatMindestens(a);
    }


    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Raum geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Raum im übergebenen Zeitraum belegt ist.</p>
     * @param zr Zeitraum, der auf Kollision mit der Belegung des Raumes geprüft wird
     * @return <strong>true</strong> wenn Zeitraum und Belegung/Reservierungen nicht kollidieren, <strong>false</strong> wenn sie kollidieren
     */
    public boolean istFrei(Zeitraum zr) {
    	if (findeKollision(zr)==null) return true;
    	else return false;
    }


    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Raum geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Raum im übergebenen Zeitraum belegt ist.</p>
     * @param zr Zeitraum, der auf Kollision mit der Belegung des Raumes geprüft wird
     * @return das Reservierungsobjekt, mit dem der Zeitraum kollidiert, wenn eine Kollision vorliegt und "null", wenn keine Kollision vorliegt
     */

    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(belegung, zr);
    }


    /**
     * <p><strong>Vorbedingungen:</strong> Es muss eine Reservierung übergeben werden und es muss einen Raum geben, auf den die Methode angewendet werden kann.</p>
	 * <p><strong>Effekt:</strong> Fügt das Reservierung sortiert in die Reservierungs-/Belegungsiste ein, falls es nicht ohnehin schon in der Liste vorhanden ist.</p>
     * @param neu Reservierung, die der Belegungs-/Reservierungsliste des Raumes hinzugefügt werden soll
     * @return <strong>true</strong> wenn die Reservierung der Liste hinzugefügt wurde, <strong>false</strong> wenn die Reservierung nicht hinzugefügt wurde, da sie bereits in der Liste vorhanden war
     */
    
    public boolean addReservierung(Reservierung neu){
        if (!belegung.contains(neu)) {
            GlobaleMethoden.addReservierungtoArrayList(belegung, neu);
            return true;
        } else {
            return false;
        }
    }


    //entfernt eine Reservierung aus der Liste
    public void removeReservierung (Reservierung weg) {
    	belegung.remove(weg);
    }
    
    public String toString(){
    	return raumBezeichnung;
    }
}
