import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class OnlineEinleser {

    private int fileCounter;
    private RaumfinderIF raumfinder;

    private final String SPEICHERORT = "C:\\Users\\Alexander\\Projekte\\IdeaProjects\\HWRaumfinder\\StundenplanFiles\\";


	public static void main (String[] Args) throws IOException {

		OnlineEinleser el = new OnlineEinleser(null);
		el.einlesen();
		//el.readEvents("C:\\Users\\Alexander\\Desktop\\elektrotechnik-semester1-kurs.ics");
		//el.einlesen();
	}

    public OnlineEinleser (RaumfinderIF parent){
        this.raumfinder=parent;
    }

    public void einlesen() {

        ArrayList<Reservierung> resses = new ArrayList<Reservierung>();

        download();

        for (int i=0; i<fileCounter; i++) {
            resses.addAll(readEvents(SPEICHERORT + "events_" + i + ".ics"));
        }

        for (int i=0; i<resses.size(); i++) {
            System.out.println(resses.get(i).toString());
            System.out.println("-----------");
        }
    }

    private void download(){

        URL tempURL;
        String tempString;
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;

        final String URLSTART = "http://moodle.hwr-berlin.de/fb2-stundenplan/download.php?doctype=.ics&url=./fb2-stundenplaene/";

        String[] url1 = {        // normal: alle mit a: 1-6, abc  || alle ohne a: 1-6, kein kurs    //"bank/semester1/kursa",
                "bank/",
                "handel/",
                "immobilien/",
                "industrie/",
                "wi/"
        };
        String[] url2 = {                       // nur ein Kurs je Semester
                "bauwesen/semester1/kurs",
                "dl/semester1/kurs",
                "elektrotechnik/semester1/kurs",
                "fm/semester1/kurs",
                "iba/semester1/kurs",
                "informatik/semester1/kurs",
                "maschinenbau/semester1/kurs",
                "steuern/semester1/kurs"
        };
        String[] url3 = {
                "ppm/semester1/kurs",	//sem 1-4
                "ppm/semester2/kurs",
                "ppm/semester3/kurs",
                "ppm/semester4/kurs",

                "spedition/semester1/kursa", //kurs a,b
                "spedition/semester1/kursb",
                "spedition/semester2/kursa",
                "spedition/semester2/kursb",
                "spedition/semester3/kursa",
                "spedition/semester3/kursb",
                "spedition/semester4/kursa",
                "spedition/semester4/kursb",
                "spedition/semester5/kursa",
                "spedition/semester5/kursb",
                "spedition/semester6/kursa",
                "spedition/semester6/kursb",

                "versicherung/semester1/kursa", //kurs a,b
                "versicherung/semester1/kursb",
                "versicherung/semester2/kursa",
                "versicherung/semester2/kursb",
                "versicherung/semester3/kursa",
                "versicherung/semester3/kursb",
                "versicherung/semester4/kursa",
                "versicherung/semester4/kursb",
                "versicherung/semester5/kursa",
                "versicherung/semester5/kursb",
                "versicherung/semester6/kursa",
                "versicherung/semester6/kursb",

                "tourismus/semester1/kurs",	// kein System
                "tourismus/semester1/kursa",
                "tourismus/semester1/kursb",
                "tourismus/semester2/kurs",
                "tourismus/semester2/kursa",
                "tourismus/semester2/kursb",
                "tourismus/semester3/kurs",
                "tourismus/semester4/kurs",
                "tourismus/semester5/kurs",
                "tourismus/semester5/kursa",
                "tourismus/semester5/kursb",
                "tourismus/semester6/kurs",
                "tourismus/semester6/kursa",
                "tourismus/semester6/kursb",

                "6B_151_153",
                "Gapp",
                "Jurscha",
                "Krause_Andreas",
                "Tchepki",
                "Wagner_Ralf",
                "Wittmuess",
                "wannemacher"
        };

        try {

            fileCounter = 0;

            // url1
            for (int i = 0; i < url1.length; i++) {
                for (int j = 1; j <= 6; j++) {
                    for (int k = 97; k <= 99; k++) {        // k repräsentiert char-Werte für a,b,c
                        tempString = url1[i] + "semester" + j + "/kurs" + (char) k;
                        tempURL = new URL(URLSTART + tempString);
                        download (tempURL, new File (SPEICHERORT + "events_" + fileCounter + ".ics"), rbc, fos);
                    }
                }
            }

            // url2
            for (int i = 0; i < url2.length; i++) {
                for (int j = 1; j <= 6; j++) {
                    tempString = url2[i] + "semester" + j + "/kurs";
                    tempURL = new URL(URLSTART + tempString);
                    download (tempURL, new File (SPEICHERORT + "events_" + fileCounter + ".ics"), rbc, fos);
                }
            }

            //url3
            for (int i = 0; i < url3.length; i++) {
                tempString = url3[i];
                tempURL = new URL (URLSTART + tempString);
                download (tempURL, new File (SPEICHERORT + "events_" + fileCounter + ".ics"), rbc, fos);
            }

            if (rbc != null) rbc.close();
            if (fos!= null) fos.close();
        }
        catch (MalformedURLException e) {
            System.err.println("Interner Fehler: Fehlerhafte URL [download]");
        }
        catch (IOException e) {
            System.err.println("Interner Fehler: rbc/fos konnte nicht geschlossen werden [download]");
            e.printStackTrace();
        }
    }

    private void download (URL website, File ziel, ReadableByteChannel rbc, FileOutputStream fos) throws IOException{
        rbc = Channels.newChannel(website.openStream());
        fos = new FileOutputStream(ziel);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fileCounter++;
    }

    /*
    public static void downloadFileFromURL(String urlString, File destination) {
        try {
            URL website = new URL(urlString);
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    private ArrayList<Reservierung> readEvents (String filepath) {

        ArrayList<Reservierung> reservierungen =new ArrayList<Reservierung>();

        String strName, strBesitzer, strStart, strEnde, strRaum, tempStr;
        String tempDatei = readICS(filepath);

        Raum tempRaum;
        Reservierer tempBesitzer;
        Zeitraum tempZeitraum;

        try {
            while (tempDatei.indexOf("SUMMARY")>0){
                tempDatei = substringAfter(tempDatei, "SUMMARY:");
                tempStr = tempDatei.substring(0,tempDatei.indexOf("LOCATION")-2);
                strName = tempStr.substring(0, tempStr.indexOf("\\;")) + " " + tempStr.substring(tempStr.indexOf("\\;")+2, tempStr.lastIndexOf("\\;"));     //ERROR HERE
                strBesitzer = tempStr.substring(tempStr.lastIndexOf(";")+1, tempStr.length());
                    strBesitzer = strBesitzer.replaceAll("\\\\", "");
                tempDatei = substringAfter(tempDatei, "LOCATION:CL: ");
                strRaum = tempDatei.substring(0,tempDatei.indexOf("DESCRIPTION")-2);
                tempDatei = substringAfter(tempDatei,"DTSTART;TZID=Europe/Berlin:");
                strStart = tempDatei.substring(0,tempDatei.indexOf("DTEND")-2);
                tempDatei = substringAfter(tempDatei, "DTEND;TZID=Europe/Berlin:");
                strEnde = tempDatei.substring(0,tempDatei.indexOf("PRIORITY")-2);

                /*
                System.out.println(strName);
                System.out.println(strBesitzer);
                System.out.println(strRaum);
                System.out.println(strStart);
                System.out.println(strEnde);
                System.out.println("-----------");
                */

                //tempRaum = raumfinder.sucheKennung(strRaum);  zu Testzwecken auskommentiert
                tempRaum = null;    // Test
                    if (tempRaum == null) {
                        tempRaum = new Raum (
                                        strRaum,
                                        null,       // Ausstattung null
                                        (short) 0,  // Kapazität null
                                        true        // buchbar true
                        );
                        //raumfinder.addRaum(tempRaum);     zu Testzwecken auskommentiert
                    }

                tempBesitzer = new Dozent (strBesitzer);
                tempZeitraum = new Zeitraum();  // Implementierung Zeitraum fehlt

                reservierungen.add(
                        new Reservierung(
                                tempRaum,
                                tempBesitzer,
                                tempZeitraum,
                                strName
                        )
                );
            }

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Interner Fehler: Substring out of Bounds [readEvents]");
            e.printStackTrace();
        }

        return reservierungen;
    }

    private String substringAfter (String text, String breakpoint) {
        int startIndex=text.indexOf(breakpoint);
        return text.substring((startIndex+breakpoint.length()));
    }

    private String readICS (String filepath){
        String erg="";
        int c;
        try{
            FileReader f = new FileReader(filepath);
            while ((c = f.read()) != -1){
                erg = erg + (char)c;
            }
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return erg;
    }

}
