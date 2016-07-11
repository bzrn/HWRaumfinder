/**
 * Created by Alexander on 25.06.2016.
 */

public class Dozent implements Reservierer {

    String name;

    public Dozent (String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
