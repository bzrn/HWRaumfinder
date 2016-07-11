
public class Admin extends Nutzer {

	private boolean deletable;

	public Admin (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
		super (name, password, sicherheitsFrage, sicherheitsAntwort);
		this.deletable = true;
	}
	
	public Admin (String name, String password, String sicherheitsFrage, String sicherheitsAntwort, boolean deletable) {
		super (name, password, sicherheitsFrage, sicherheitsAntwort);
		this.deletable = deletable;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
}
