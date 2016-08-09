package Persistenz;

import Verarbeitung.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RaumfinderFileAdapter implements RaumfinderFileAdapterIF {

	private File raeumedatei;
	private File reservierungendatei;
	private File nutzerdatei;
	private File rescounterdatei;

	public RaumfinderFileAdapter () {
		new File ("FileSaver/").mkdir();
		raeumedatei = new File ("FileSaver/raeume.raumfind");
		reservierungendatei = new File ("FileSaver/reservierungen.raumfind");
		nutzerdatei = new File ("FileSaver/nutzer.raumfind");
		rescounterdatei = new File ("FileSaver/rescounter.raumfind");
	}

	@SuppressWarnings({ "resource", "static-access" })
	public void save(){

		Raum[]speicherraum=new Raum[Raumfinder.getInstance().getRaeume().size()];
		Raumfinder.getInstance().getRaeume().toArray(speicherraum);

		Reservierung[]speicherres=new Reservierung[Raumfinder.getInstance().getReservierungen().size()];
		Raumfinder.getInstance().getReservierungen().toArray(speicherres);

		Nutzer[]speichernutzer=new Nutzer[Raumfinder.getInstance().getNutzer().size()];
		Raumfinder.getInstance().getNutzer().toArray(speichernutzer);


		try{

			ObjectOutputStream out;

			out = new ObjectOutputStream (new FileOutputStream(raeumedatei));
			out.writeObject(speicherraum);

			out = new ObjectOutputStream (new FileOutputStream(reservierungendatei));
			out.writeObject(speicherres);

			out = new ObjectOutputStream (new FileOutputStream(nutzerdatei));
			out.writeObject(speichernutzer);

			out.close();

		} catch(IOException e) {

			System.err.println("Es gab ein Problem beim Speichern: IOException");
			e.printStackTrace();
			return;
		}

		long resCounter=Raumfinder.getInstance().getResCounter();

		try {
			FileWriter fileout = new FileWriter(rescounterdatei);
			fileout.write(String.valueOf(resCounter));
			fileout.close();
		} catch(IOException e){
			System.err.println("Es gab ein Problem beim Speichern des ResCounters: IOException");
			e.printStackTrace();
			return;
		}
	}

	@SuppressWarnings({ "resource", "static-access" })
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

			Scanner sc = new Scanner(rescounterdatei);
			long resCounter = sc.nextLong();
			sc.close();
/*
			int c;
			int i=0;
			char [] resCounterChar = new char [30];
			try{
				FileReader fin = new FileReader(rescounterdatei);
				while ((c = fin.read()) != -1){
					resCounterChar [i]=((char)c);
					i++;
				}
				fin.close();

				resCounterString = String.valueOf(resCounterChar);
			} catch (IOException e) {
				System.err.println("Es gab ein Problem beim Einlesen des ResCounters: IOException");
			}
*/

			Raumfinder.getInstance().setResCounter(resCounter);		//Long.parseLong(resCounterString));

			Raumfinder.getInstance().setRaeume(new ArrayList<Raum> (Arrays.asList(laderaum)));

			Raumfinder.getInstance().setReservierungen(new ArrayList<Reservierung> (Arrays.asList(laderes)));

			Raumfinder.getInstance().setNutzer(new ArrayList<Nutzer> (Arrays.asList(ladenutzer)));

		} catch (IOException e1) {
			System.err.println("Es gab ein Problem beim Laden: IOException");
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			System.err.println("Es gab ein Problem beim Laden: ClassNotFound");
		}
	}
}