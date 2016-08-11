package Verarbeitung;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <strong/>Zweck:<strong/>
 * <p><strong>Änderungshistorie:</strong></p>
 * @version
 * @author Alexander Reichenbach
 * 
 */

public class Raum implements Serializable {
	
	// Attribute
	
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

    //Getter und Setter
    
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

    public void setBuchbar (boolean buchbar) { 
    	this.buchbar = buchbar; 
    }
    
    /**
     * 
     * @param a
     * @return
     */
    
    public int hatMindestausstattung (Ausstattung a) {
    	return ausstattung.hatMindestens(a);
    }
    
    /**
     * 
     * @param zr
     * @return
     */
    
    public boolean istFrei(Zeitraum zr) {
    	if (findeKollision(zr)==null) return true;
    	else return false;
    }

    /**
     * 
     * @param zr
     * @return
     */
    
    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(belegung, zr);
    }

    
    /**
     * 
     * @param neu
     * @return
     */
    
    public boolean addReservierung(Reservierung neu){
        if (!belegung.contains(neu)) {
            GlobaleMethoden.addReservierungtoArrayList(belegung, neu);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @param weg
     */
    
    public void removeReservierung (Reservierung weg) {
    	belegung.remove(weg);
    }
    
    public String toString(){
    	return raumBezeichnung;
    }
}
