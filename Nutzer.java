import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Nutzer {
	
	protected static long nutzerCounter = 0;
	protected long nutzerNr;
	protected String name, pwHash, sicherheitsFrage, sicherheitsAntwortHash;
	protected static MessageDigest messageDigest;
	
	protected Nutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
		
		nutzerNr = nutzerCounter++;
		this.name=name;
		this.sicherheitsFrage = sicherheitsFrage;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			setPwHash(password);
			setSicherheitsAntwortHash(sicherheitsAntwort);
		} catch (NoSuchAlgorithmException wtf){
			System.err.println("Interner Fehler: Hash-Algorithmus nicht vorhanden [Nutzer-Konstr]");
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwHash() {
		return pwHash;
	}

	public void setPwHash(String password) {
		messageDigest.update(password.getBytes());
		pwHash = new String(messageDigest.digest());
	}

	public String getSicherheitsFrage() {
		return sicherheitsFrage;
	}

	public void setSicherheitsFrage(String sicherheitsFrage) {
		this.sicherheitsFrage = sicherheitsFrage;
	}

	public String getSicherheitsAntwortHash() {
		return sicherheitsAntwortHash;
	}

	public void setSicherheitsAntwortHash(String sicherheitsAntwort) {
		messageDigest.update(sicherheitsAntwort.getBytes());
		sicherheitsAntwortHash = new String(messageDigest.digest());
	}

	public boolean checkPw (String password){
		messageDigest.update(password.getBytes());
		if (pwHash.equals(new String (messageDigest.digest()))) return true;
		else return false;
	}
}
