import datenImportierenundLesen.FahrzeugLesenUndSchreiben;

import java.io.File;

public class FahrzeugImport {

    public static void main(String[] args) {

        File fdaten = new File("C:\\Users\\User\\Documents\\HaegerAufgabe\\Fahrzeugdaten.xml");


        System.out.println(fdaten.getName());
        FahrzeugLesenUndSchreiben fahrzeugLesenUndSchreiben = new FahrzeugLesenUndSchreiben();
        //fahrzeugLesenUndSchreiben.auszugFahrzeug(fdaten);
        //fahrzeugLesenUndSchreiben.schreibenDatenbank(fahrzeugLesenUndSchreiben.auszugFahrzeug(fdaten));
        fahrzeugLesenUndSchreiben.lesenDatenbank();
    }
}
