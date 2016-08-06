package Verarbeitung;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Nutzer implements Serializable {

    protected static long nutzerCounter = 0;
    protected long nutzerNr;
    protected String name, pwHash, sicherheitsFrage, sicherheitsAntwortHash;
    protected static MessageDigest messageDigest;		// more or less solved

    protected Nutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {

        nutzerNr = nutzerCounter++;
        this.name=name;
        this.sicherheitsFrage = sicherheitsFrage;
        if (sicherheitsFrage.charAt(sicherheitsFrage.length()-1)!='?') sicherheitsFrage += "?";

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            setPwHash(password);
            setSicherheitsAntwortHash(sicherheitsAntwort);
        } catch (NoSuchAlgorithmException wtf){
            System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
        }
    }

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
        if (sicherheitsFrage.charAt(sicherheitsFrage.length()-1)!='?') sicherheitsFrage += "?";
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

    public boolean checkPw (String password){
    	if (messageDigest==null) {
    		try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException wtf){
                System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
            }
    	}
        messageDigest.update(password.getBytes());
        if (pwHash.equals(new String (messageDigest.digest()))) return true;
        else return false;
    }

    public boolean checkFrage (String antwort){
    	if (messageDigest==null) {
    		try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException wtf){
                System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
            }
    	}
        messageDigest.update(antwort.getBytes());
        if (sicherheitsAntwortHash.equals(new String (messageDigest.digest()))) return true;
        else return false;
    }
}
