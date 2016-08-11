package Verarbeitung;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <strong/>Zweck:<strong/>
 * <p><strong>Änderungshistorie:</strong></p>
 * @version
 * @author Alexander Reichenbach
 * 
 */

public class StandardNutzer extends Nutzer implements Reservierer, Serializable {

    private ArrayList<Reservierung> reservierungen = new ArrayList<Reservierung>();

    public StandardNutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
    }

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public boolean addReservierung (Reservierung r) {
        if (!reservierungen.contains(r)) {
            GlobaleMethoden.addReservierungtoArrayList(reservierungen, r);
            return true;
        } else {
            return false;
        }
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
