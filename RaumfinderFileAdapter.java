package Verarbeitung;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class RaumfinderFileAdapter {
	
	private Raumfinder rf;
	private File raeumedatei;
	private File reservierungendatei;
	private File nutzerdatei;
	
	public RaumfinderFileAdapter (Raumfinder rf) {
		this.rf = rf;
		raeumedatei = new File ("FileSaver/raeume.raumfind");
		reservierungendatei = new File ("FileSaver/reservierungen.raumfind");
		nutzerdatei = new File ("FileSaver/nutzer.raumfind");
	}

    @SuppressWarnings("resource")
	public void save(){
    	
    	Raum[]speicherraum=new Raum[rf.getRaeume().size()];
    	rf.getRaeume().toArray(speicherraum);
    	
    	Reservierung[]speicherres=new Reservierung[rf.getReservierungen().size()];
    	rf.getReservierungen().toArray(speicherres);
    	
    	Nutzer[]speichernutzer=new Nutzer[rf.getNutzer().size()];
    	rf.getNutzer().toArray(speichernutzer);    	
    	
    	try{
    	
    	ObjectOutputStream out;
    	
    	out = new ObjectOutputStream (new FileOutputStream(raeumedatei));
    	out.writeObject(speicherraum);

    	out = new ObjectOutputStream (new FileOutputStream(reservierungendatei));
    	out.writeObject(speicherres);
    	
    	out = new ObjectOutputStream (new FileOutputStream(nutzerdatei));
    	out.writeObject(speichernutzer);
    	
    	out.close();
    	
    	}catch(IOException e){
    		System.err.println("Es gab ein Problem beim Speichern: IOException");
    		e.printStackTrace();
    		return;
    	}
    }

    @SuppressWarnings("resource")
	public void load() {
    	
    	try {
    	ObjectInputStream in;
    		
    	in = new ObjectInputStream (new FileInputStream(raeumedatei));
    	Raum[] laderaum=(Raum[])in.readObject();
    		
    	in = new ObjectInputStream (new FileInputStream(reservierungendatei));
    	Reservierung[] laderes=(Reservierung[])in.readObject();
    		
    	in = new ObjectInputStream (new FileInputStream(nutzerdatei));
    	Nutzer[] ladenutzer=(Nutzer[])in.readObject();
    	
    	in.close();

    	rf.setRaeume(new ArrayList<Raum> (Arrays.asList(laderaum)));
    	
    	rf.setReservierungen(new ArrayList<Reservierung> (Arrays.asList(laderes)));
    	
    	rf.setNutzer(new ArrayList<Nutzer> (Arrays.asList(ladenutzer)));
    	
    	} catch (IOException e1) {
    		System.err.println("Es gab ein Problem beim Laden: IOException");
    	} catch (ClassNotFoundException e2) {
    		System.err.println("Es gab ein Problem beim Laden: ClassNotFound");
    	}
    }
}