package Verarbeitung;

import VerarbeitungInterfaces.StandardNutzerIF;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *<strong>Zweck:</strong> Ermöglicht das Erstellen von StandardNutzer-Objekten, denen, im Gegensatz zu Objekten der Mutterklasse "Nutzer", Reservierungen zugeorndet werden können. Diese werden als Liste gespeichert.
 * <p><strong>Änderungshistorie:</strong> </p>
 * @version 2.3
 * @author Alexander Reichenbach
 *
 */

public class StandardNutzer extends Nutzer implements VerarbeitungInterfaces.StandardNutzerIF, Reservierer, Serializable {

   // Attribute
	private static final long serialVersionUID = -6698216101275037529L;
	
	private ArrayList<Reservierung> reservierungen = new ArrayList<Reservierung>();

	// Konstruktor
    public StandardNutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
    }

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }

    
    /**
     * <p><strong>Vorbedingungen:</strong> Es muss eine Reservierung übergeben werden und es muss einen Standardnutzer geben, auf den die Methode angewendet werden kann.</p>
	 * <p><strong>Effekt:</strong> Fügt das Reservierung sortiert in die Reservierungsliste ein, falls es nicht ohnehin schon in der Liste vorhanden ist.</p>
     * @param r Reservierung, die der Reservierungsliste des Nutzers hinzugefügt werden soll
     * @return <strong>true</strong> wenn die Reservierung der Liste hinzugefügt wurde, <strong>false</strong> wenn die Reservierung nicht hinzugefügt wurde, da sie bereits in der Liste vorhanden war
     */
    public boolean addReservierung (Reservierung r) {
        if (!reservierungen.contains(r)) {
            GlobaleMethoden.addReservierungtoArrayList(reservierungen, r); 
            return true;
        } else {
            return false;
        }
    }

    //entfernt eine Reservierung aus der Reservierungsliste
    public void removeReservierung (Reservierung weg) {
    	reservierungen.remove(weg);
    }
    
    public void setReservierungen(ArrayList<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Standartnutzer geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Zeitraum mit einer der Reservierungen des Nutzers kollidiert.</p>
     * @param zr Zeitraum, der auf Kollision mit den Reservierungen des Nutzers geprüft wird
     * @return <strong>true</strong> wenn Zeitraum und Reservierungen nicht kollidieren, <strong>false</strong> wenn sie kollidieren
     */
    public boolean istFrei(Zeitraum zr) {
        if (findeKollision(zr)==null) return true;
        else return false;
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Standartnutzer geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Zeitraum mit einer der Reservierungen des Nutzers kollidiert.</p>
     * @param zr Zeitraum, der auf Kollision mit den Reservierungen des Nutzers geprüft wird
     * @return das Reservierungsobjekt, mit dem der Zeitraum kollidiert, wenn eine Kollision vorliegt und "null", wenn keine Kollision vorliegt
     */
    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(reservierungen, zr);
    }
}
