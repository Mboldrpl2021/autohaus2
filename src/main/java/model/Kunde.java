package model;

public class Kunde {

    private String Name;
    private String Vorname;
    private String Anschrift;
    private int PLZ;
    private String Ort;

    public Kunde( String name, String vorname, String anschrift, int PLZ, String ort) {
        Name = name;
        Vorname = vorname;
        Anschrift = anschrift;
        this.PLZ = PLZ;
        Ort = ort;
    }
    public String getName() { return Name; }

    public void setName(String name) { Name = name;}

    public String getVorname() { return Vorname; }

    public void setVorname(String vorname) { Vorname = vorname; }

    public String getAnschrift() { return Anschrift; }

    public void setAnschrift(String anschrift) { Anschrift = anschrift; }

    public int getPLZ() { return PLZ; }

    public void setPLZ(int PLZ) { this.PLZ = PLZ; }

    public String getOrt() { return Ort; }

    public void setOrt(String ort) { Ort = ort; }

}
