package Verarbeitung;		// Changed

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Zweck:
 * @author
 * @version
 * Änderungshistorie:
 * Created by mwolff on 19.06.16.
 * Hanna am 23. Juni
 */
public class Raum implements Serializable {

    private String raumBezeichnung;
    private ArrayList<Reservierung> belegung;
    private Ausstattung ausstattung;
    private boolean buchbar;


    public Raum(String raumBezeichnung, Ausstattung ausstattung, boolean buchbar){
        this.raumBezeichnung = raumBezeichnung;
        this.belegung = new ArrayList<Reservierung>();
        this.ausstattung=ausstattung;
        this.buchbar = buchbar;
    }

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

    public boolean isBuchbar() {
        return buchbar;
    }

    public void setBuchbar (boolean buchbar) { this.buchbar = buchbar; }
    
    public int hatMindestausstattung (Ausstattung a) {
    	return ausstattung.hatMindestens(a);
    }
    
    public boolean istFrei(Zeitraum zr) {
    	if (findeKollision(zr)==null) return true;
    	else return false;
    }

    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(belegung, zr);
    }

    public void addReservierung(Reservierung neu){
        GlobaleMethoden.addReservierungtoArrayList(belegung, neu);
    }
    
    public void removeReservierung (Reservierung weg) {
    	belegung.remove(weg);
    }
    
    public String toString(){
    	return raumBezeichnung;
    }
}
