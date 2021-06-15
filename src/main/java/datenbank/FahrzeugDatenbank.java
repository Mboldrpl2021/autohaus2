package datenbank;

import model.Fahrzeug;
import datenImportierenundLesen.KonstanteWert;

import java.sql.*;
import java.util.ArrayList;

public class FahrzeugDatenbank {

    private static Connection verbindung;

    public static void startDataBase(){
        try {
            Class.forName("org.h2.Driver");
            verbindung = DriverManager.getConnection(KonstanteWert.DATABASEPATH);

            PreparedStatement command = verbindung.prepareStatement("CREATE TABLE IF NOT EXISTS FahrzeugeTabelle ( FahrzeugType TEXT, Fahrzeugbezeichnung TEXT , Hersteller Text , Leistung TEXT , Verkaufpreise DOUBLE)");
            command.executeUpdate();

        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void fahrzeugListSchreiben(ArrayList<Fahrzeug> fahrzeugen){
        for (Fahrzeug fahrzeug: fahrzeugen) {

            try {

                PreparedStatement command = verbindung.prepareStatement("INSERT INTO FahrzeugeTabelle ( FahrzeugType , Fahrzeugbezeichnung ,  Hersteller , Leistung ,  Verkaufpreise)  VALUES(?,?,?,?,?);");
                command.setString(1, fahrzeug.getFahrzeugtyp());
                command.setString(2, fahrzeug.getFahrzeugbezeichnung());
                command.setString(3, fahrzeug.getHersteller());
                command.setString(4, fahrzeug.getLeistung());
                command.setDouble(5, fahrzeug.getVerkaufpreis());
                command.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try{
            verbindung.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static ResultSet fahrzeugListLesen() throws SQLException {

        PreparedStatement command = verbindung.prepareStatement("SELECT * FROM FahrzeugeTabelle;");
        return command.executeQuery();

    }

    public static Connection getVerbindung() {
        return verbindung;
    }
}
