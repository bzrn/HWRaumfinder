package Verarbeitung;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * <strong>Zweck:</strong> Ermöglicht das Erzeugen von Zeitraum-Objekten mit einer bestimmten Dauer, Start- und Endzeitpunkt
 * <p><strong>Änderungshistorie:</strong> ... </p>
 *
 * @version 2.2
 * @author Hanna Behnke, Alexander Reichenbach
 *
 */

//@SuppressWarnings("deprecation")
public class Zeitraum implements Serializable {
	
	//Attribute
	
	private Date start;
	private Date ende;
	private int dauer;
	
	//Konstruktor
	
	public Zeitraum (Date start, Date ende) throws UnzulaessigerZeitraumException {
		if (!ende.after(start)) throw new UnzulaessigerZeitraumException();
		else {
			this.start=start;
			this.ende=ende;
			this.dauer=berechneDauer(start, ende);
		}
	}

	// create-Methode
	public static Zeitraum createZeitraum (Date start, Date ende) throws UnzulaessigerZeitraumException{
		return new Zeitraum(start, ende);
	}
	
	//getter und setter
	
	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
		berechneDauer(start, ende);
	}


	public Date getEnde() {
		return ende;
	}


	public void setEnde(Date ende) {
		this.ende = ende;
		berechneDauer(start, ende);
	}


	public int getDauer() {
		return dauer;
	}

	public String toString(){
		String erg="";

		Calendar c = Calendar.getInstance();
		c.setTime(start);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		erg += sdf.format(c.getTime()) + " von: ";
		if (c.get(Calendar.HOUR_OF_DAY)<=9) erg += "0";
		erg += Integer.toString(c.get(Calendar.HOUR_OF_DAY))+":";
		if (c.get(Calendar.MINUTE)<=9) erg += "0";
		erg += Integer.toString(c.get(Calendar.MINUTE))+ " bis: ";
		c.setTime(ende);
		if (c.get(Calendar.HOUR_OF_DAY)<=9) erg += "0";
		erg +=  Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + ":";
		if (c.get(Calendar.MINUTE)<=9) erg += "0";
		erg += Integer.toString(c.get(Calendar.MINUTE));

		return erg;
	}


	/**
	 * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum-Objekt vorhanden sein, auf dem die Methode aufgerufen werden kann.</p>
	 * <p><strong>Effekt:</strong> Die Dauer des Zeitraumes wird in Minuten berechnet.</p>
	 * @param start der Start des Zeitraumes wird als Datumsobjekt übergeben
	 * @param ende das Ende des Zeitraumes wird als Datumsobjekt übergeben
	 * @return <strong>dauer</strong> gibt die Dauer des Zeitraumes in Minuten zurück
	 */

	public int berechneDauer(Date start, Date ende){
		int stunden=ende.getHours()-start.getHours();
		int min=ende.getMinutes()-start.getMinutes();
		dauer= stunden*60+min;
		return dauer;
		}


	/**
	 * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum-Objekt vorhanden sein, auf dem die Methode aufgerufen werden kann, außerdem muss ein Zeitraum "engpass" übergeben werden.</p>
	 * <p><strong>Effekt:</strong> Die Dauer des Zeitraumes wird in Minuten berechnet.</p>
	 * @param engpass ein zweiter Zeitraum, der auf Überschneidungen mit dem Zeitraum geprüft wird, auf den die Methode angewendet wird
	 * @return <strong>true</strong>, wenn die Zeiträume kollidieren, <strong>false</strong>, wenn sie nicht kollidieren.
	 */

	public boolean kollidiert(Zeitraum engpass){
		
		Date engpassStart = engpass.getStart();
		Date engpassEnde = engpass.getEnde();
	
	
		if (start.equals(engpassStart) && ende.equals(engpassEnde)){ 		// Kollision, da die Zeiträume identisch sind
  			return true;
		}else if(start.before(engpassStart) && ende.after(engpassEnde)){	// Kollision, da der Zeitraum "engpass" innerhalb des betrachteten Zeitraumes liegt
  			return true;
 		}else if(start.after(engpassStart) && ende.before(engpassEnde)){	// Kollision, da der betrachtete Zeitraum innerhalb des Zeitraumes "engpass" liegt
  			return true;
 		}else if(ende.after(engpassStart) && ende.before(engpassEnde)){		// Kollision, da sich die Zeiträume überschneiden, das Ende des betrachteten Zeitraumes liegt innerhalb des Zeitraumes "engpass"
  			return true;
 		}else if(start.after(engpassStart) && start.before(engpassEnde)){	// Kollision, da sich die Zeiträume überschneiden, der Start des betrachteten Zeitraumes liegt innerhalb des Zeitraumes "engpass"
  			return true;
  		}
  		return false;
  	}
}
