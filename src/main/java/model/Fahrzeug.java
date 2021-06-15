package model;

public class Fahrzeug {

    private String fahrzeugtyp;
    private String hersteller;
    private String fahrzeugbezeichnung;
    private String leistung;
    private double verkaufpreis;

    public Fahrzeug() {
    }

    public Fahrzeug(String fahrzeugtyp, String hersteller, String fahrzeugbezeichnung, String leistung, double verkaufpreis) {
        this.fahrzeugtyp = fahrzeugtyp;
        this.hersteller = hersteller;
        this.fahrzeugbezeichnung = fahrzeugbezeichnung;
        this.leistung = leistung;
        this.verkaufpreis = verkaufpreis;
    }

    public String getFahrzeugtyp() {
        return fahrzeugtyp;
    }

    public void setFahrzeugtyp(String fahrzeugtyp) {
        this.fahrzeugtyp = fahrzeugtyp;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public String getFahrzeugbezeichnung() {
        return fahrzeugbezeichnung;
    }

    public void setFahrzeugbezeichnung(String fahrzeugbezeichnung) {
        this.fahrzeugbezeichnung = fahrzeugbezeichnung;
    }

    public String getLeistung() {
        return leistung;
    }

    public void setLeistung(String leistung) {
        this.leistung = leistung;
    }

    public double getVerkaufpreis() {
        return verkaufpreis;
    }

    public void setVerkaufpreis(double verkaufpreis) {
        this.verkaufpreis = verkaufpreis;
    }
}
