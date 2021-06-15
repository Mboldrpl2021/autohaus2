package datenImportierenundLesen;

import datenbank.FahrzeugDatenbank;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.Fahrzeug;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FahrzeugLesenUndSchreiben {

    public void schreibenDatenbank(ArrayList<Fahrzeug> fahrzeugen){

        FahrzeugDatenbank.startDataBase();
        FahrzeugDatenbank.fahrzeugListSchreiben(fahrzeugen);
    }

    public ArrayList<Fahrzeug> lesenDatenbank(){

        FahrzeugDatenbank.startDataBase();
        ArrayList<Fahrzeug> fahrzeugen = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = FahrzeugDatenbank.fahrzeugListLesen();
            while (resultSet.next()){
                Fahrzeug fahrzeug = new Fahrzeug(resultSet.getString("FahrzeugType"),  resultSet.getString("Fahrzeugbezeichnung"), resultSet.getString("Hersteller"), resultSet.getString("Leistung"), resultSet.getDouble("Verkaufpreise"));
                fahrzeugen.add(fahrzeug);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            FahrzeugDatenbank.getVerbindung().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*for (Fahrzeug fahrzeug: fahrzeugen) {

            System.out.println(fahrzeug.getFahrzeugtyp() +" "+ fahrzeug.getFahrzeugbezeichnung()+ " "+ fahrzeug.getHersteller()+ " " +" "+ fahrzeug.getLeistung()+ " "+ fahrzeug.getVerkaufpreis());

        }*/

        return fahrzeugen;
    }

    public ArrayList<Fahrzeug> auszugFahrzeug(File file) {

        ArrayList<Fahrzeug> fahrzeugen = new ArrayList<>();

        ObjectMapper mapper = new XmlMapper();

        try {
            InputStream inputStream = new FileInputStream(file);
            TypeReference<List<Fahrzeug>> typeReference = new TypeReference<List<Fahrzeug>>() {};
            fahrzeugen.addAll(mapper.readValue(inputStream, typeReference));


        }catch (Exception e){
                e.printStackTrace();
        }



        for (Fahrzeug fahrzeug: fahrzeugen) {
            System.out.println(fahrzeug.getFahrzeugtyp()+" "+ fahrzeug.getFahrzeugbezeichnung()+" "+fahrzeug.getHersteller()+
                    " "+ fahrzeug.getLeistung()+" "+fahrzeug.getVerkaufpreis());
        }

        return fahrzeugen;
    }
}
