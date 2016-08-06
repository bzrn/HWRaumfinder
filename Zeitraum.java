package Verarbeitung;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

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
		erg += Integer.toString(c.get(Calendar.HOUR_OF_DAY));
		if (c.get(Calendar.HOUR_OF_DAY)==0) erg += "0";
		erg += ":"+Integer.toString(c.get(Calendar.MINUTE));
		if (c.get(Calendar.MINUTE)==0) erg += "0";
		c.setTime(ende);
		erg += " bis: " + Integer.toString(c.get(Calendar.HOUR_OF_DAY));
		if (c.get(Calendar.HOUR_OF_DAY)==0) erg += "0";
		erg += ":"+Integer.toString(c.get(Calendar.MINUTE));
		if (c.get(Calendar.MINUTE)==0) erg += "0";

		return erg;
	}


	
	//15 min Einheiten -> Pro Tag 4*24=96 (Schätzungsweise täglich 8-20 Uhr, sonntags geschlossen?!  32-80ste Einheit geöffnet
	//über GUI mit dropdown realisieren, dass nur 8-19:45 uhr und 15min Intervalle möglich sind


	//Methoden
	public int berechneDauer(Date start, Date ende){
		int stunden=ende.getHours()-start.getHours();
		int min=ende.getMinutes()-start.getMinutes();
		dauer= stunden*60+min;
		return dauer;
		}
	
	public boolean kollidiert(Zeitraum engpass){
		if (this.getStart().after(engpass.getStart())&&this.getStart().before(engpass.getEnde())){
			return true;
		}else if(this.getEnde().after(engpass.getStart())&&this.getEnde().before(engpass.getEnde())){
			return true;
		}else if(this.getEnde().after(engpass.getEnde())||this.getEnde().equals(engpass.getEnde())&&this.getStart().before(engpass.getStart())||this.getStart().equals(engpass.getStart())){
			return true;
		}
		return false;
	}
	
}