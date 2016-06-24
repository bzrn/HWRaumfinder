import java.util.ArrayList;

/**
 * Created by mwolff on 19.06.16.
 */


public class Raumfinder implements RaumfinderIF {

    //Attribute
    private ArrayList<Raum> raeume;
    private ArrayList<Reservierung> reservierungen;
    //private ArrayList<Nutzer> nutzer;
    //private ArrayList<Dozent> dozenten;
    private OnlineEinleser onEinleser;        //muss Interface werden
    //private RaumfinderFileAdapterIF saver;

    //Konstruktor muss noch angepasst werden!
    public Raumfinder(ArrayList<Raum> raeume, OnlineEinleser onEinleser){
        this.raeume = raeume;
        this.onEinleser = onEinleser;

        this.raeume = onEinleser.einlesen();

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
    }

    public void save(){
    }

    public void load(){
    }

    public Raum sucheKennung(String raumKennung){ //sucht Raum nach Kennung, im Moment noch exakte Kennung
        for(int i = 0; i<raeume.size(); i++){
            if(raumKennung.equalsIgnoreCase(raeume.get(i).getRaumBezeichnung())){
                System.out.println("Cool.");
                return raeume.get(i);
            }
        }

        return null;
    }
    public void addRaum(Raum a){
        raeume.add(a);
    } //könnte überflüssig sein...
}
}
