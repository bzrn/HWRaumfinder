package Raumfinder;

import java.util.Date;

public class Zeitraum {
	
	//Attribute
	
	private Date start;
	private Date ende;
	private int dauer;
	
	//Konstruktor
	
	public Zeitraum (Date start, Date ende){
		this.start=start;
		this.ende=ende;
		this.dauer=berechneDauer(start, ende);
		
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
