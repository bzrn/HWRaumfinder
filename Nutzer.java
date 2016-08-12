package Verarbeitung;

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
