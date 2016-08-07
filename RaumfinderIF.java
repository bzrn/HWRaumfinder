package Verarbeitung;

import java.util.ArrayList;

public interface RaumfinderIF {

    // Raum-Methoden
    ArrayList<String> suche(Zeitraum s, Ausstattung a);
    Raum sucheKennung(String raumKennung);
    boolean pruefeVerfuegbarkeitRaum(String raumKennung, Zeitraum zr);
    boolean pruefeBuchbarkeitRaum(String raumKennung);
    void addRaum(Raum a);
    void loescheRaum(Raum a);
    ArrayList<Raum> getRaeume();
    void setRaeume(ArrayList<Raum> raeume);

    // Reservierungs-Methoden
    boolean reservieren (Raum r, Reservierer n, Zeitraum s);
    void stornieren(Reservierung r);
    Reservierung sucheReservierung(long reservierungsNummer) ;
    ArrayList<Reservierung> getReservierungen();
    void setReservierungen(ArrayList<Reservierung> reservierungen);
    long getResCounter();
    void setResCounter(long resCount);

    // Nutzer-Methoden
    Nutzer sucheNutzer(String nutzerName);
    Nutzer authentifiziereNutzer(String name, String password);
    void legeNutzerAn(String name, String password, String sicherheitsFrage, String sicherheitsAntwort, boolean admin);
    boolean loescheNutzer(Nutzer n);
    void addNutzer(Nutzer n);
    ArrayList<Nutzer> getNutzer();
    void setNutzer(ArrayList<Nutzer> nutzer);
    String[] getNutzerString();

    // Sonstige Methoden
    void onlineEinlesen();
    void save();
    void load();
}
