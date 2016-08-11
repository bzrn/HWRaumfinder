package VerarbeitungInterfaces;

import Verarbeitung.Reservierer;
import Verarbeitung.Zeitraum;
import Verarbeitung.Raum;

/**
 * Created by Alex on 11.08.2016.
 */
public interface ReservierungIF {
    long getReservierungsNr();
    Zeitraum getZeitraum();
    Raum getRaum();
    Reservierer getInhaber();
    boolean isStorniert();
    boolean isError();
    String toString (boolean admin);
}
