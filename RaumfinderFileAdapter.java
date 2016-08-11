package Persistenz;

import Verarbeitung.Raumfinder;
import VerarbeitungInterfaces.RaumfinderIF;

import java.io.*;
import java.util.Scanner;

public class RaumfinderFileAdapter implements RaumfinderFileAdapterIF {

	// Singleton-Implementierung
	private static RaumfinderFileAdapter ourInstance = new RaumfinderFileAdapter();

	private File datei, rescounterdatei;

	// Singleton-Konstruktor
	private  RaumfinderFileAdapter () {
		new File ("FileSaver/").mkdir();
		datei = new File ("FileSaver/persistenz.raumfind");
		rescounterdatei = new File ("FileSaver/rescounter.raumfind");
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

		long resCounter=Raumfinder.getInstance().getResCounter();
		try {
			FileWriter fileout = new FileWriter(rescounterdatei);
			fileout.write(String.valueOf(resCounter));
			fileout.close();
		} catch(IOException e){
			System.err.println("Es gab ein Problem beim Speichern des ResCounters: IOException");
		}
	}

	@SuppressWarnings({ "resource", "static-access" })
	public RaumfinderIF load() {

		try {
			Scanner sc = new Scanner(rescounterdatei);
			long resCounter = sc.nextLong();
			sc.close();
			ObjectInputStream in = new ObjectInputStream (new FileInputStream(datei));
			RaumfinderIF rfif = (RaumfinderIF) in.readObject();
			rfif.setResCounter(resCounter);
			return rfif;
		} catch (IOException e) {
			System.err.println("Es gab ein Problem beim Einlesen des ResCounters: IOException");
		} catch (ClassNotFoundException t){
			System.err.println("Es gab ein Problem beim Laden: ClassNotFound");
		}
		return null;
	}
}