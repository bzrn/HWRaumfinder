package Verarbeitung;

import java.io.Serializable;
import java.util.ArrayList;

public class StandardNutzer extends Nutzer implements Reservierer, Serializable {

    private ArrayList<Reservierung> reservierungen = new ArrayList<Reservierung>();

    public StandardNutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
    }

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public void addReservierung (Reservierung r) {
        GlobaleMethoden.addReservierungtoArrayList(reservierungen, r);
    }

    public void removeReservierung (Reservierung weg) {
    	reservierungen.remove(weg);
    }
    
    public void setReservierungen(ArrayList<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    public boolean istFrei(Zeitraum zr) {
        if (findeKollision(zr)==null) return true;
        else return false;
    }

    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(reservierungen, zr);
    }
}