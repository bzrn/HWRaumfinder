package Verarbeitung;

import VerarbeitungInterfaces.ReservierungIF;

import java.io.Serializable;
import java.util.Date;

/**
 * <strong>Zweck:</strong> Ermöglicht die Erstellung von Reservierungsobjekten, denen eine eindeutige Reservierungsnummer sowie ein Zeitraum, ein Reservierer und ein Raum zugeordnet sind.
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 3.4
 * @author Alexander Reichenbach
 *
 */


public class Reservierung implements VerarbeitungInterfaces.ReservierungIF, Serializable {

    // Attribute
	private static final long serialVersionUID = 8584156051140726334L;

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

    // mit manuell gesetztem Zeitpunkt und Kommentar, für Verarbeitung.OnlineEinleser
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

    public static long getResCounter(){
        return resCounter;
    }

    public static void setResCounter(long resCount){
        Reservierung.resCounter=resCount;
    }

    public boolean kollidiert (Zeitraum zr) {
        if (error || storniert) return false;
        else return this.zeitraum.kollidiert(zr);
    }

    public String toString (boolean admin) {
        String erg;
        erg =	"Nummer:     "  + reservierungsNr                       + "\n" +
                "Raum:       "  + raum.getRaumBezeichnung()             + "\n" +
                "Zeitraum:   "  + zeitraum.toString()      				+ "\n" +
                "Kommentar:  ";

        if (!kommentar.isEmpty()) erg += kommentar;
        else erg += "Kein Kommentar";

        erg +=  "\n" +
                "reserviert am " + reservierungsZeitpunkt.toString() + "\n";

        if (admin){
            erg +=	"Inhaber:    "  + inhaber.getName()                     + "\n" +
                    "Error:      "  + error                                 + "\n" +
                    "Storniert:  "  + storniert;
        }

        return erg;
    }
}