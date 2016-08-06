/**
 * Created by Alexander on 25.06.2016.
 */

package Verarbeitung;

import java.io.Serializable;

public class Dozent implements Reservierer, Serializable {

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
