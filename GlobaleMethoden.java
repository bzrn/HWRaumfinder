package Verarbeitung;

import java.util.ArrayList;
import java.util.Date;

/**
 * <strong/>Zweck:</strong> Definiert globale Methoden zum sortierten Einfügen von Resevierungen in eine ArrayList 
 * und zum Überprüfen von möglichen Zeitraumkollisionen in einer Reservierungsliste. 
 * Die Methoden finden in mehreren verschiedenen Klassen Anwendung.
 * <p><strong>Änderungshistorie:</strong> ...</p>
 * @version 1.2
 * @author Alexander Reichenbach
 *
 */
public class GlobaleMethoden {

	
	/**
	 * <p><strong>Vorbedingungen:</strong> Es müssen eine ArrayList vom Typ Reservierung und eine neue Reservierung als Parameter übergeben werden.</p>
	 * <p><strong>Effekt:</strong> Die neue Reservierung wird sortiert in die Liste eingefügt, chronologisch geordnet nach Startpunkt des reservierten Zeitraumes. </p>
	 * @param al ArrayList vom Typ Reservierung, in die die neue Reservierung eingefügt wird
	 * @param neu Reservierung, die in die ArrayList eingefügt wird
	 */
    public static void addReservierungtoArrayList (ArrayList<Reservierung> al, Reservierung neu) {
        Date neuStart=neu.getZeitraum().getStart(), tempStart;

        if (al.size()==0) { 					//Wenn die Reservierungsliste leer ist, wird das Reservierungsobjekt sofort hinzugefügt
            al.add(neu);
            return;
        }
        else { 									//Sonst wird die Reservierung nach Startpunkt des Zeitraumes sortiert in die Liste eingefügt
            for (int i=0; i<al.size(); i++) {
                tempStart = al.get(i).getZeitraum().getStart();
                if (neuStart.before(tempStart)) {
                    al.add(i, neu);
                    return;
                }
            }
            al.add(neu);
        }
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Es müssen eine ArrayList vom Typ Reservierung und ein Zeitraum übergeben werden.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob das Zeitraumobjekt mit einem Element der Reservierungsliste kollidiert.</p>
     * @param al eine ArrayList vom Typ Reservierung, die in der Methode durchlafen wird
     * @param zr ein Zeitraum, der auf Kollision geprüft wird
     * @return bei einer Kollision wird das Reservierungsobjekt aus der ArrayList zurückgegeben, mit dem der Zeitraum kollidiert, 
     * gibt es keine Kollision, wird "null" zurückgegeben 
     */
    public static Reservierung findeKollisioninArrayList (ArrayList<Reservierung> al, Zeitraum zr) {
        for (int i=0; i<al.size(); i++) {
            if (al.get(i).kollidiert(zr)) return al.get(i);
        }
        return null;
    }
}
