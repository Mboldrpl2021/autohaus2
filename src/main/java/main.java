import datenImportierenundLesen.KundenLesenUndSchreiben;
import datenbank.KundeDatenbank;
import model.Kunde;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        //FahrzeugImport.importFahrzeug();

        printOptions();

        String benutzerauswahl = null;
        do {
            if (benutzerauswahl != null) {
                System.out.println("Ungültige Option ausgewählt.");
            }
            Scanner eingabeScanner = new Scanner(System.in);
            benutzerauswahl = eingabeScanner.nextLine();
        }
        while(benutzerauswahl != null && !validateChoosenOption(benutzerauswahl));

        System.out.print("Dein Auswahl ist:");
        System.out.println(benutzerauswahl);
        switch(benutzerauswahl) {
            case "1":
                System.out.println("Liste von allen Kunden");
                KundeDatenbank.startDatabase();

                ArrayList<Kunde> kundenist = new KundenLesenUndSchreiben().lesenDatenbank();

                if (kundenist.size() == 0){
                        System.out.println("Keiner Kunde wurde bis jetzt gespeichert");

                }else {
                    for (int i = 0; i < kundenist.size(); i++) {
                            Kunde kunde = kundenist.get(i);
                            System.out.println(i + ". "+ " "+ kunde.getName()+ " "+ kunde.getVorname()+ ", "+ kunde.getAnschrift()+ " "+ kunde.getPLZ()+ " "+ kunde.getOrt());
                    }
                }

                System.out.println("");
                main(new String[2]);
                break;
            case "2":
                Kunde kunde = eingabeAttribut();
                auswahl(kunde);

                main(new String[2]);
                break;
            default:
                System.out.println("Existing program");
                System.exit(1);
        }

    }

    public static void printOptions() {
        System.out.println("Bitte wählen sie eine Option");
        System.out.println("1: Alle Kunden auflisten");
        System.out.println("2: Ein neuer Kunde eintragen");
        System.out.println("x: Programm terminieren");
    }

    public static boolean validateChoosenOption(String userChoice) {
        List<String> possibleOptions = new ArrayList<>();
        possibleOptions.add("1");
        possibleOptions.add("2");
        possibleOptions.add("x");

        return possibleOptions.contains(userChoice);
    }

    public static Kunde eingabeAttribut(){
        String name = "";
        String vorname = "";
        String anschrift = "";
        int plz = 0;
        String ort = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte geben Sie den Name des Kundes:");
        name = scanner.nextLine();

        System.out.println("Bitte geben Sie den Vorname des Kundes:");
        vorname = scanner.nextLine();

        System.out.println("Bitte geben Sie den Anschrift des Kundes:");
        anschrift = scanner.nextLine();

        System.out.println("Bitte geben Sie den PLZ des Kundes:");
        plz = scanner.nextInt();

        System.out.println("Bitte geben Sie den Ort des Kundes:");
        Scanner scanner1 = new Scanner(System.in);
        ort = scanner1.nextLine();

        Kunde kunde = new Kunde(name, vorname, anschrift, plz, ort);

        return kunde;
    }

    public static void auswahl(Kunde kunde){

        String antwort = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wollen Sie unmitellbar den Kundendaten speichern?");
        System.out.println("Geben Sie: ");
        System.out.println("1 für ja");
        System.out.println("2 für nein");
        antwort = scanner.nextLine();

        if (antwort.equals("1")){
            KundeDatenbank.startDatabase();
            KundeDatenbank.kundSchreiben(kunde);
            System.out.println("Kunden Attributen gespeichert");
            System.out.println("");
        }

        main(new String[2]);
    }

}
