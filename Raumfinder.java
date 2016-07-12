import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by mwolff on 19.06.16.
 */


public class Raumfinder { //implements RaumfinderIF {

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
        //onEinleser = new OnlineEinleser(this);
            //onEinleser.einlesen();
    }

    public Raumfinder(ArrayList<Raum> raeume, ArrayList<Reservierung> reservierungen, OnlineEinleser onEinleser){

        this.raeume = raeume;
        this.reservierungen = reservierungen;
        this.onEinleser = onEinleser;
            onEinleser.einlesen();
    }

    public ArrayList<Raum> suche (Zeitraum s, Ausstattung a){
    	
    	int offset = 0;
    	ConcurrentSkipListMap<Integer,Raum> erg = new ConcurrentSkipListMap<Integer,Raum>();
    	
    	for (int i=0; i<raeume.size(); i++) {
    		Raum r = raeume.get(i);
    		int score = r.hatMindestausstattung(a);
        	if (score > 0 && r.istFrei(s)) {
        		erg.put((score*1000)+(offset++) , r);
        	}
        }
    	return (new ArrayList<Raum>(erg.values()));
    }

    public void reservieren (Raum r, Reservierer n, Zeitraum s) {
    	Reservierung neu = new Reservierung (r, n, s);
    	this.addReservierung(neu);
    	//r.addReservierung(neu);
    	//if (n instanceof StandardNutzer)((StandardNutzer)n).addReservierung(neu);
    }

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

    public ArrayList<Raum> getRaeume() {
        return raeume;
    }
    
    private void addReservierung(Reservierung neu){
    	
    	Date neuStart=neu.getZeitraum().getStart(), tempStart;
    	
    	if (reservierungen.size()==0) {
    		reservierungen.add(neu);
    		return;
    	}
    	else if (reservierungen.size()<=10) {
    		for (int i=0; i<reservierungen.size(); i++) {
    			tempStart = reservierungen.get(i).getZeitraum().getStart();
        		if (neuStart.before(tempStart)) {
        			reservierungen.add(i, neu);
        			return;
        		}
    		}
    		reservierungen.add(neu);
    	}
    	
    	/*else {
        
    		int i=0, j=reservierungen.size()-1, tempIndex;
    		
    		while (i<=j) {
    			
    			tempIndex=(i+j)/2;
    			tempStart= reservierungen.get(tempIndex).getZeitraum().getStart();
    			
//    			if (tempStart.equals(neuStart) || (reservierungen.get(i).getZeitraum().getStart().before(neuStart)&&reservierungen.get(j).getZeitraum().getStart().after(neuStart))){
//    				reservierungen.add(tempIndex, neu);
//    				return;
//    			}
    			
    			if (neuStart.after(tempStart)) {
    				if (neuStart.before(reservierungen.get(j).getZeitraum().getStart())) i=tempIndex+1;
    				else 
    			
    			}
    			else if (neuStart.before(tempStart) && neuStart.after (reservierungen.get(j).getZeitraum().getStart()))	j=tempIndex-1;
    			else {
    				reservierungen.add(tempIndex, neu);
    			}
    		}
    	}*/
    }

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }
}
