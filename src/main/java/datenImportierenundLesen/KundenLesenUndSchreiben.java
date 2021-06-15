package datenImportierenundLesen;

import datenbank.KundeDatenbank;
import model.Kunde;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KundenLesenUndSchreiben {

    public void schreibenDatenbank(Kunde kunde){

        KundeDatenbank.startDatabase();
        KundeDatenbank.kundSchreiben(kunde);
    }

    public ArrayList<Kunde> lesenDatenbank(){

        KundeDatenbank.startDatabase();
        ArrayList<Kunde> kunden = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = KundeDatenbank.kundenListLesen();
            while (resultSet.next()){
                Kunde kunde = new Kunde( resultSet.getString("Name"),  resultSet.getString("Vorname"), resultSet.getString("Anschrift"), resultSet.getInt("PLZ"), resultSet.getString("ORT"));
                kunden.add(kunde);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            KundeDatenbank.getVerbindung().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*for (Kunde kunde: kunden) {

            System.out.println(resultSet.getString("Anrede"),kunde.getName() +" "+ kunde.getVorname()+ " "+ kunde.getAnschrift()+ " " +" "+ kunde.getPLZ()+ " "+ kunde.getOrt());

        }*/

        return kunden;
    }

}
