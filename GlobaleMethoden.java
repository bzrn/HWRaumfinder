package Verarbeitung;

import java.util.ArrayList;
import java.util.Date;

public class GlobaleMethoden {

    public static void addReservierungtoArrayList (ArrayList<Reservierung> al, Reservierung neu) {
        Date neuStart=neu.getZeitraum().getStart(), tempStart;

        if (al.size()==0) {
            al.add(neu);
            return;
        }
        else if (al.size()<=10) {
            for (int i=0; i<al.size(); i++) {
                tempStart = al.get(i).getZeitraum().getStart();
                if (neuStart.before(tempStart)) {
                    al.add(i, neu);
                    return;
                }
            }
            al.add(neu);
        }
        /*else {

    		int i=0, j=reservierungen.size()-1, tempIndex;

    		while (i<=j) {

    			tempIndex=(i+j)/2;
    			tempStart= reservierungen.get(tempIndex).getZeitraum().getStart();

//    			if (tempStart.equals(neuStart) || (reservierungen.get(i).getZeitraum().getStart().before(neuStart)&&reservierungen.get(j).getZeitraum().getStart().after(neuStart))){
//    				reservierungen.add(tempIndex, neu);
//    				return;
//    			}

    			if (neuStart.after(tempStart)) {
    				if (neuStart.before(reservierungen.get(j).getZeitraum().getStart())) i=tempIndex+1;
    				else

    			}
    			else if (neuStart.before(tempStart) && neuStart.after (reservierungen.get(j).getZeitraum().getStart()))	j=tempIndex-1;
    			else {
    				reservierungen.add(tempIndex, neu);
    			}
    		}
    	}*/
    }

    public static Reservierung findeKollisioninArrayList (ArrayList<Reservierung> al, Zeitraum zr) {
        for (int i=0; i<al.size(); i++) {
            if (al.get(i).kollidiert(zr)) return al.get(i);
        }
        return null;
    }
}
