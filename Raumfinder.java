import java.util.ArrayList;

/**
 * Created by mwolff on 19.06.16.
 */


public class Raumfinder implements RaumfinderIF {

    //Attribute
    private ArrayList<Raum> raeume;
    private ArrayList<Reservierung> reservierungen;
    private ArrayList<Nutzer> nutzer;
    //private ArrayList<Dozent> dozenten;   // Dozenten wollten wir doch nicht speichern
    private OnlineEinleser onEinleser;        //muss Interface werden
    //private RaumfinderFileAdapterIF saver;

    //Konstruktor muss noch angepasst werden!

    public Raumfinder () {

        raeume = new ArrayList<Raum>();
        reservierungen = new ArrayList<Reservierung>();
        nutzer = new ArrayList<Nutzer>(); // noch nicht implementiert
        onEinleser = new OnlineEinleser(this);
            onEinleser.einlesen();
    }

    public Raumfinder(ArrayList<Raum> raeume, ArrayList<Reservierung> reservierungen, OnlineEinleser onEinleser){

        this.raeume = raeume;
        this.reservierungen = reservierungen;
        this.onEinleser = onEinleser;
            onEinleser.einlesen();
    }

    public ArrayList<Raum> getRaeume() {
        return raeume;
    }

    public Raum[] suche (Raum r, Zeitraum s){ // genaue Realisierung noch unklar
        return null;
    }

    //public void reservieren (Nutzer n, Raum r, Zeitraum s) {
    //}

    public void onlineEinlesen(){
        onEinleser.einlesen();
    }

    public void save(){
    }

    public void load(){
    }

    public Raum sucheKennung(String raumKennung){ //sucht Raum nach Kennung, im Moment noch exakte Kennung
        for(int i = 0; i<raeume.size(); i++){
            if(raumKennung.equalsIgnoreCase(raeume.get(i).getRaumBezeichnung())){
                return raeume.get(i);
            }
        }
        return null;
    }

    public void addRaum(Raum a){    //könnte überflüssig sein... //nicht überflüssig, sortierung muss hier implementiert werden <alex>
        raeume.add(a);
    }

    public ArrayList<Raum> getRaeume () { return raeume;}
}
