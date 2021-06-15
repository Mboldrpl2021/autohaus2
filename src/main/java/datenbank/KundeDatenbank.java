package datenbank;

import model.Kunde;
import datenImportierenundLesen.KonstanteWert;

import java.sql.*;

public class KundeDatenbank {

    private static Connection verbindung;

    public static void startDatabase(){
        try {
            Class.forName("org.h2.Driver");
            verbindung = DriverManager.getConnection(KonstanteWert.DATABASEPATH);

            PreparedStatement command1 = verbindung.prepareStatement("CREATE TABLE IF NOT EXISTS KundenTabelle (  Name TEXT , Vorname TEXT, Anschrift TEXT, PLZ INTEGER , ORT TEXT);");
            command1.executeUpdate();

        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void kundSchreiben(Kunde kund){

            try {

                PreparedStatement command = verbindung.prepareStatement("INSERT INTO KundenTabelle ( Name , Vorname ,  Anschrift , PLZ ,  ORT)  VALUES(?,?,?,?,?);");
                command.setString(1, kund.getName());
                command.setString(2, kund.getVorname());
                command.setString(3, kund.getAnschrift());
                command.setInt(4, kund.getPLZ());
                command.setString(5, kund.getOrt());
                command.executeUpdate();

                verbindung.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public static ResultSet kundenListLesen() throws SQLException {

        PreparedStatement command = verbindung.prepareStatement("SELECT * FROM KundenTabelle ;");
        ResultSet resultSet = command.executeQuery();
        return resultSet;

    }

    public static Connection getVerbindung() {
        return verbindung;
    }
}
