package Persistenz;

import Verarbeitung.Raumfinder;
import VerarbeitungInterfaces.RaumfinderIF;

import java.io.*;

public class RaumfinderFileAdapter implements RaumfinderFileAdapterIF {

	// Singleton-Implementierung
	private static RaumfinderFileAdapter ourInstance = new RaumfinderFileAdapter();

	private File datei;

	// Singleton-Konstruktor
	private  RaumfinderFileAdapter () {
		new File ("FileSaver/").mkdir();
		datei = new File ("FileSaver/persistenz.raumfind");
	}
	
	// Singleton-getInstance
    public static RaumfinderFileAdapter getInstance() {
    	return ourInstance;
    }

	@SuppressWarnings({ "resource", "static-access" })
	public void save(){

		try {
			ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream(datei));
			out.writeObject((RaumfinderIF)Raumfinder.getInstance());
		} catch(IOException e) {
			System.err.println("Es gab ein Problem beim Speichern: IOException");
			return;
		}
	}

	@SuppressWarnings({ "resource", "static-access" })
	public RaumfinderIF load() {

		try {
			ObjectInputStream in = new ObjectInputStream (new FileInputStream(datei));
			return (RaumfinderIF) in.readObject();
		} catch (IOException e) {
			System.err.println("Es gab ein Problem beim Einlesen des ResCounters: IOException");
		} catch (ClassNotFoundException t){
			System.err.println("Es gab ein Problem beim Laden: ClassNotFound");
		}
		return null;
	}
}