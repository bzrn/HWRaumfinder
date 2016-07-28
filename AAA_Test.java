package Verarbeitung;

import Verarbeitung.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import Oberflaeche.*;

/**
 * Created by Alexander on 12.07.2016.
 */
public class AAA_Test {

    static private BufferedReader din = new BufferedReader(new InputStreamReader(System.in));
    static private Raumfinder rf;

    public static void main (String[] Args) throws IOException {

        rf = new Raumfinder(false);
        startConsole();

    }

    private static void startConsole() throws IOException{

        boolean exit = false;
        String eingabe;

        while (!exit) {
            System.out.print(
                    "\nBitte waehlen:\n"
                    +"\t1 - online einlesen\n"
                    +"\t11 - GUIFrame erstellen\n"
                    +"\t2 - Reservierungen ausgeben\n"
                    +"\t3 - neue Reservierung\n"
                    +"\t31 - Reservierung stornieren\n"
                    +"\t32 - Reservierung errorisieren\n"
                    +"\t33 - Reservierung suchen\n"
                    +"\t4 - alle Raeume ausgeben\n"
                    +"\t5 - Raum suchen\n"
                    +"\t6 - neuer Raum\n"
                    +"\t7 - alle Nutzer ausgeben\n"
                    +"\t8 - Nutzer suchen\n"
                    +"\t9 - neuer Nutzer\n"
                    +"\t0 - verlassen\n"
                    +"\nBitte entsprechende Ziffer eingeben: ");

            int modus = Integer.parseInt(din.readLine());
            switch (modus) {
                case 1:
                    System.out.println("Einlesen...");
                    rf.onlineEinlesen();
                    System.out.println("Fertig!");
                    break;
                case 11:
                    GUIFrame frame = new GUIFrame(rf);
                case 2:
                    System.out.println("A für alle, R für Raum:");
                    eingabe=din.readLine();
                    switch (eingabe) {
                        case "A":
                            System.out.println("Reservierungen im Raumfinder:");
                            for (int i=0; i<rf.getReservierungen().size(); i++) {
                                System.out.println(rf.getReservierungen().get(i).toString());
                                System.out.println("-----------------");
                            }
                            break;
                        case "R":
                            System.out.println("Existierenden Raumnamen eingeben:");
                            eingabe = din.readLine();
                            Raum r = rf.sucheKennung(eingabe);
                            if (r==null) break;
                            for (int i=0; i<r.getBelegung().size(); i++) {
                                System.out.println(r.getBelegung().get(i).toString());
                                System.out.println("-----------------");
                            }
                            break;
                        default:
                            System.out.println("Error.");
                    }
                    break;
                case 3:
                    System.out.println("Existierenden Nutzernamen eingeben:");
                    eingabe = din.readLine();
                    Nutzer n = rf.sucheNutzer(eingabe);
                    System.out.println("Existierenden Raumnamen eingeben:");
                    eingabe = din.readLine();
                    Raum r = rf.sucheKennung(eingabe);
                    System.out.println("Startzeitpunkt im Format \"19960101T020000\" eingeben:");
                    eingabe = din.readLine();
                    Date t1 = rf.getOnEinleser().icsStringToDate(eingabe);
                    System.out.println("Endzeitpunkt im Format \"19960101T020000\" eingeben:");
                    eingabe = din.readLine();
                    Date t2 = rf.getOnEinleser().icsStringToDate(eingabe);
                    if (rf.reservieren(r, (StandardNutzer)n, new Zeitraum(t1, t2))) System.out.println("Reservierung erfolgreich");
                    else System.out.println("Error.");
                    break;
                case 31:
                    System.out.println("Reservierungsnummer eingeben:");
                    eingabe = din.readLine();
                    Reservierung res = rf.sucheReservierung(Long.parseLong(eingabe));
                    if (res == null) System.out.println("Reservierung nicht gefunden.");
                    else {
                        rf.stornieren(res);
                        System.out.println("Reservierung Nr. " + res.getReservierungsNr() + " storniert.");
                    }
                    break;
                case 32:
                    System.out.println("Reservierungsnummer eingeben:");
                    eingabe = din.readLine();
                    Reservierung rez = rf.sucheReservierung(Long.parseLong(eingabe));
                    if (rez == null) System.out.println("Reservierung nicht gefunden.");
                    else {
                        rf.stornieren(rez);
                        System.out.println("Reservierung Nr. " + rez.getReservierungsNr() + " auf error gesetzt.");
                    }
                    break;
                case 33:
                    System.out.println("Reservierungsnummer eingeben:");
                    eingabe = din.readLine();
                    Reservierung rep = rf.sucheReservierung(Long.parseLong(eingabe));
                    if (rep == null) System.out.println("Reservierung nicht gefunden.");
                    else{
                        System.out.println("Reservierung  gefunden.");
                        System.out.println(rep.toString()+"\n");
                    }
                    break;
                case 4:
                    System.out.println("Räume:");
                    for (int i = 0; i < rf.getRaeume().size(); i++) {
                        System.out.println(rf.getRaeume().get(i).getRaumBezeichnung());
                    }
                    break;
                case 5:
                    System.out.println("Suche nach Raumnamen:");
                    eingabe = din.readLine();
                    if (rf.sucheKennung(eingabe) == null) System.out.println("Raum nicht gefunden.");
                    else System.out.println(rf.sucheKennung(eingabe).getRaumBezeichnung() + " gefunden.");
                    break;
                case 6:
                    System.out.println("Neuen Raumnamen eingeben:");
                    eingabe = din.readLine();
                    rf.addRaum(new Raum(eingabe, null, true));
                    break;
                case 7:
                    System.out.println("Nutzer:");
                    for (int i = 0; i < rf.getNutzer().size(); i++) {
                        System.out.println(rf.getNutzer().get(i).getName());
                    }
                    break;
                case 8:
                    System.out.println("Suche nach Nutzernamen:");
                    eingabe = din.readLine();
                    if (rf.sucheNutzer(eingabe) == null) System.out.println("Nutzer nicht gefunden.");
                    else System.out.println(rf.sucheNutzer(eingabe).getName() + " gefunden.");
                    break;
                case 9:
                    System.out.println("Neuen Nutzernamen eingeben:");
                    String nutzername = din.readLine();
                    System.out.println("Passwort eingeben:");
                    String passwort = din.readLine();
                    rf.legeNutzerAn(nutzername,passwort,"","",false);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe, nochmal.");
                    break;
            }
        }
    }
}
