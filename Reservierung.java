package Verarbeitung;

import java.util.Date;

/**
 * Zweck:
 * @author
 * @version
 * Änderungshistorie:
 * Created by mwolff on 19.06.16.
 * Hanna am 3. Juni
 */

// Attribute

public class Reservierung {

    private static long resCounter = 0;

    private long reservierungsNr;
    private Raum raum;
    private Reservierer inhaber;
    private Zeitraum zeitraum;
    private Date reservierungsZeitpunkt;
    private String kommentar;
    private boolean error;
    private boolean storniert;


    // Konstruktoren
    //  ->  reservierungsNr und reservierungsZeitpunkt werden automatisch gesetzt
    //  ->  error und storniert sind bei Objektkonstruktion false

    // mit Kommentar
    public Reservierung(Raum raum, Reservierer inhaber, Zeitraum zeitraum, String kommentar) {

        this.reservierungsNr = resCounter++;
        this.raum = raum;
        this.inhaber = inhaber;
        this.zeitraum = zeitraum;
        this.reservierungsZeitpunkt = new Date (System.currentTimeMillis());
        this.kommentar = kommentar;
        this.error = false;
        this.storniert = false;
    }

    // ohne Kommentar
    public Reservierung(Raum raum, Reservierer inhaber, Zeitraum zeitraum) {

        this.reservierungsNr = resCounter++;
        this.raum = raum;
        this.inhaber = inhaber;
        this.zeitraum = zeitraum;
        this.reservierungsZeitpunkt = new Date (System.currentTimeMillis());
        this.kommentar = "";
        this.error = false;
        this.storniert = false;
    }
    
    // mit mauell gesetztem Zeitpunkt und Kommentar, für OnlineEinleser
    public Reservierung(Raum raum, Reservierer inhaber, Zeitraum zeitraum, Date zeitpunkt) {

        this.reservierungsNr = resCounter++;
        this.raum = raum;
        this.inhaber = inhaber;
        this.zeitraum = zeitraum;
        this.reservierungsZeitpunkt = zeitpunkt;
        this.kommentar = "";
        this.error = false;
        this.storniert = false;
    }

    //Getter und Setter

    public long getReservierungsNr() {
        return reservierungsNr;
    }
    public void setReservierungsNr(long reservierungsNr) {
        this.reservierungsNr = reservierungsNr;
    }
    public Raum getRaum() {
        return raum;
    }
    public void setRaum(Raum raum) {
        this.raum = raum;
    }
    public Reservierer getInhaber() {
        return inhaber;
    }
    public void setInhaber(Reservierer inhaber) {
        this.inhaber = inhaber;
    }
    public Zeitraum getZeitraum() {
        return zeitraum;
    }
    public void setZeitraum(Zeitraum zeitraum) {
        this.zeitraum = zeitraum;
    }
    public Date getReservierungsZeitpunkt() {
        return reservierungsZeitpunkt;
    }
    public void setReservierungsZeitpunkt(Date reservierungsZeitpunkt) {
        this.reservierungsZeitpunkt = reservierungsZeitpunkt;
    }
    public String getKommentar() {
        return kommentar;
    }
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
    public boolean isError() {
        return error;
    }
    public void setError(boolean error) {
        this.error = error;
    }
    public boolean isStorniert() {
        return storniert;
    }
    public void setStorniert(boolean storniert) {
        this.storniert = storniert;
    }
    
    public boolean kollidiert (Zeitraum zr) {
        if (error || storniert) return false;
        else return this.zeitraum.kollidiert(zr);
    }

    public String toString () {
        return  "reservierungsNr:\t\t"  + reservierungsNr                       + "\n" +
                "Kommentar:\t\t"        + kommentar                             + "\n" +
                "Raum:\t\t"             + raum.getRaumBezeichnung()             + "\n" +
                "Inhaber:\t\t"          + inhaber.getName()                     + "\n" +
                "Zeitraum:\t\t"           + zeitraum.getStart().toString()      + "\n" +
                "resZeitpunkr:\t\t"     + reservierungsZeitpunkt.toString()     + "\n" +
                "Error:\t\t"            + error                                 + "\n" +
                "Storniert:\t\t"        + storniert;
    }


}
