package Verarbeitung;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by mwolff on 19.06.16.
 */


public class Raumfinder implements RaumfinderIF {

    //Attribute
    private ArrayList<Raum> raeume;
    private ArrayList<Reservierung> reservierungen;
    private ArrayList<Nutzer> nutzer;
    private OnlineEinleser onEinleser;        //muss Interface werden
    //private RaumfinderFileAdapterIF saver;


    // Standardkonstruktor
    public Raumfinder (boolean einlesen) {

        raeume = new ArrayList<Raum>();
        reservierungen = new ArrayList<Reservierung>();
        nutzer = new ArrayList<Nutzer>();
        onEinleser = new OnlineEinleser(this);
        if (einlesen) onlineEinlesen();
    }

    // manueller Konstruktor
    public Raumfinder(ArrayList<Raum> raeume, ArrayList<Reservierung> reservierungen, ArrayList<Nutzer> nutzer, OnlineEinleser onEinleser){

        this.raeume = raeume;
        this.reservierungen = reservierungen;
        this.nutzer=nutzer;
        this.onEinleser = onEinleser;
            onlineEinlesen();
    }

    // Raumsuche anhand von Kriterien (Zeitraum || Ausstattung)
    public ArrayList<String> suche (Zeitraum s, Ausstattung a){

        // Variableninitialisierung
    	int offset = 0;
    	ConcurrentSkipListMap<Integer,String> erg = new ConcurrentSkipListMap<>();

        // Durchlaufen aller RÃ¤ume
    	for (int i=0; i<raeume.size(); i++) {
    		Raum r = raeume.get(i);
    		int score = r.hatMindestausstattung(a);     // Bewertung der Relevanz des Suchergebnisses
        	if (score > 0 && r.istFrei(s)) {            // bei erfÃ¼lten Suchkriterien
        		erg.put((score*1000)+(offset++) , r.getRaumBezeichnung());   // als Ergebnis abspeichern (invers geordnet nach Relevanz)
        	}
        }
    	return (new ArrayList<String>(erg.descendingMap().values()));     // Konstruktion und Reversion der Werte
    }

    // automatische Erstellung einer Reservierung ohne Kommentar
    public boolean reservieren (Raum r, Reservierer n, Zeitraum s) {
        Reservierung neu = new Reservierung (r, n, s);
        return reservieren (neu,false);
    }

    // automatische Erstellung einer Reservierung mit Kommentar
    public boolean reservieren (Raum r, Reservierer n, Zeitraum s, String kommentar) {
    	Reservierung neu = new Reservierung (r, n, s, kommentar);
    	return reservieren (neu, false);
    }

    // interne bzw. manuelle Erstellung einer Reservierung
    public boolean reservieren (Reservierung neu, boolean overwrite) {

        // Variableninitialisierung
        boolean kollisionRaum = false, kollisionInh = false;
        Raum raum = neu.getRaum();
        Zeitraum zr = neu.getZeitraum();
        StandardNutzer sn = null;
        if  (neu.getInhaber() instanceof StandardNutzer) sn = (StandardNutzer)neu.getInhaber();

        // mÃ¶gliche Kollisionen suchen
        if (!raum.istFrei(zr)) kollisionRaum = true;
        if (sn != null) if (!sn.istFrei(zr))  kollisionInh = true;

        // keine Kollisionen:
        // Reservierung einstellen
        if (!kollisionRaum && !kollisionInh && raum.isBuchbar()) {
            this.addReservierung(neu);
            raum.addReservierung(neu);
            if (sn != null) sn.addReservierung(neu);
            return true;
        }

        // Kollisionen existieren
        else {
            // Im overwrite-Modus:
            // Kollisionen beseitigen (durch Errors) und Reservierung einstellen
            if (overwrite) {
                if (kollisionRaum) {
                    raum.findeKollision(zr).setError(true);
                }
                if (kollisionInh) {
                    sn.findeKollision(zr).setError(true);
                }
                this.addReservierung(neu);
                raum.addReservierung(neu);
                if (sn != null) sn.addReservierung(neu);
                return true;
            }
            // Nicht im overwrite-Modus:
            // Reservierung verwerfen
            else return false;
        }
    }

    public void stornieren (Reservierung r) {
        r.setStorniert(true);
        r.getRaum().getBelegung().remove(r);
        if (r.getInhaber() instanceof StandardNutzer) ((StandardNutzer)r.getInhaber()).getReservierungen().remove(r);
    }

    public void onlineEinlesen(){
        onEinleser.einlesen();
    }

    public void save(){
        //Schnittstelle zu PersistenzAdapter
    }

    public void load(){
        //Schnittstelle zu PersistenzAdapter
    }

    public Raum sucheKennung(String raumKennung){
        for(int i = 0; i<raeume.size(); i++){
            if(raumKennung.equalsIgnoreCase(raeume.get(i).getRaumBezeichnung())){
                return raeume.get(i);
            }
        }
        return null;
    }
  

    public Nutzer sucheNutzer(String nutzerName){
        for(int i = 0; i<nutzer.size(); i++){
            if(nutzerName.equalsIgnoreCase(nutzer.get(i).getName())){
                return nutzer.get(i);
            }
        }
        return null;
    }

    public Reservierung sucheReservierung(long reservierungsNummer){ //sucht Res nach Nr
        for(int i = 0; i<reservierungen.size(); i++){
            if(reservierungsNummer==reservierungen.get(i).getReservierungsNr()){
                return reservierungen.get(i);
            }
        }
        return null;
    }

    public boolean pruefeVerfuegbarkeitRaum (String raumKennung, Zeitraum zr){
        return sucheKennung(raumKennung).istFrei(zr);
    }

    public Nutzer authentifiziereNutzer (String name, String password) {
        Nutzer erg = sucheNutzer(name);
        if(!erg.checkPw(password)) erg=null;
        return erg;
    }

    public void legeNutzerAn (String name, String password, String sicherheitsFrage, String sicherheitsAntwort, boolean admin) {
        if(admin){
            addNutzer(new Admin (name,password,sicherheitsFrage,sicherheitsAntwort));       // deletable-Erweiterung?
        } else {
            addNutzer(new StandardNutzer(name,password,sicherheitsFrage,sicherheitsAntwort));
        }
    }
    
    public void loescheNutzer (Nutzer n) {
    	nutzer.remove(n);
    }

    private void addNutzer(Nutzer n){
        nutzer.add(n);

        // SORTIERUNG!
    }

    public ArrayList<Nutzer> getNutzer() {
        return nutzer;
    }
    
    public String[] getNutzerString() {
    	String[] erg = new String[nutzer.size()];
    	for (int i=0; i<erg.length; i++) {
    		erg[i] = nutzer.get(i).getName();
    	}
    	return erg;
    }

    public void addRaum(Raum a){    //kÃ¶nnte Ã¼berflÃ¼ssig sein... //nicht Ã¼berflÃ¼ssig, sortierung muss hier implementiert werden <alex>
        raeume.add(a);
    }

    public ArrayList<Raum> getRaeume() {
        return raeume;
    }
    
    private void addReservierung(Reservierung neu){
    	GlobaleMethoden.addReservierungtoArrayList(reservierungen, neu);
    	}

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }
     public OnlineEinleser getOnEinleser () {
         return onEinleser;
     }
}
