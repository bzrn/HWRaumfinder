package Oberflaeche;

import Verarbeitung.Raumfinder;
import Verarbeitung.Admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <strong>Zweck:</strong> Leitet den Start des Raumfinders mittels Konsolenabfragen ein und initialisiert das System
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 2.1
 * @author Alexander Reichenbach
 *
 */
public class _Starter {
    static private BufferedReader din = new BufferedReader(new InputStreamReader(System.in));
    static private Raumfinder rf;
    static private GUIFrame gui;
    static private Admin admin;

    static private boolean firstStart=false;
    static private boolean einlesen=false;
    static private String[] eingabe = {"","","",""};

    public static void main(String[] Args) throws IOException, InterruptedException {

        System.out.println("Ist dies der erste Programmstart? (Y|N)");
        while (!(eingabe[0].equalsIgnoreCase("y")||eingabe[0].equalsIgnoreCase("n"))) eingabe[0] = din.readLine();
        if (eingabe[0].equalsIgnoreCase("y")){
            firstStart = true;
            System.out.println("Willkommen beim HWRaumfinder!");
            Thread.sleep(500);
            System.out.println("Bevor der HWRaumfinder gestartet werden kann, muss ein Admin-Konto erstellt werden.");
            Thread.sleep(500);
            System.out.println("Achtung: Dieses Nutzerkonto ist nicht löschbar.");
            Thread.sleep(500);
            System.out.println("Bitte den gewünschten Nutzernamen eingeben:");
            eingabe[0] = din.readLine();
            while (eingabe[1].length()<8){
                System.out.println("Bitte ein mindestens 8-stelliges Passwort eingeben:");
                eingabe[1] = din.readLine();
            }
            System.out.println("Bitte eine Sicherheitsfrage eingeben:");
            eingabe[2] = din.readLine();
            System.out.println("Bitte eine Antwort auf die Sicherheitsfrage eingeben:");
            eingabe[3] = din.readLine();

            admin = new Admin(eingabe[0],eingabe[1],eingabe[2], eingabe[3], false);

            System.out.println("\nVielen Dank!");
            Thread.sleep(500);
            System.out.println("Soll der Raumfinder vor dem Start die bestehende Raumbelegung seitens der HWR-Verwaltung\naus dem Internet herunterladen? (Y|N)");
            while (!(eingabe[0].equalsIgnoreCase("y")||eingabe[0].equalsIgnoreCase("n"))) eingabe[0] = din.readLine();
            if (eingabe[0].equalsIgnoreCase("y")) einlesen = true;
        }

        starteHWRaumfinder();
    }

    private static void starteHWRaumfinder(){
        System.out.println("\nHWRaumfinder wird gestartet.");
        
        rf = Raumfinder.getInstance();
        
        if (firstStart) rf.getNutzer().add(admin);
        else rf.load();

        if (einlesen) {
            System.out.println("Daten werden eingelesen...");
            rf.onlineEinlesen();
        }

        gui = GUIFrame.getInstance();
        
        System.out.println("Programm gestartet.");
    }
}
