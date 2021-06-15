import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.color.Color;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.element.Image;
import java.text.DateFormat;
import datenImportierenundLesen.FahrzeugLesenUndSchreiben;
import datenImportierenundLesen.KundenLesenUndSchreiben;
import model.Fahrzeug;
import model.Kunde;

import java.util.Date;
import java.util.ArrayList;

public class PdfErstellen {

    public static void main(String args[]) throws Exception {
        KundenLesenUndSchreiben kundenLesen = new KundenLesenUndSchreiben();
        ArrayList<Kunde> kunden = kundenLesen.lesenDatenbank();

        FahrzeugLesenUndSchreiben fahrzeugLesen = new FahrzeugLesenUndSchreiben();
        ArrayList<Fahrzeug> fahrzeugen = fahrzeugLesen.lesenDatenbank();

        for (int i = 0; i < kunden.size() ; i++) {

            Kunde kunde = kunden.get(i);

            // Erstellen PdfWriter
            String dest = "C:\\Users\\User\\Documents\\HaegerAufgabe\\Document" + i + ".pdf";
            PdfWriter schreiber = new PdfWriter(dest);

            // Neue PdfDocument  erstellen
            PdfDocument pdf = new PdfDocument(schreiber);

            // Neue doc erstellen
            Document document = new Document(pdf);

            //Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            String para1 = "\n\n"+ kunde.getName() + " "+ kunde.getVorname();
            String para2 = kunde.getAnschrift();
            String para3 = kunde.getPLZ() + " " + kunde.getOrt();

            // Erstellen ein Bilder
            String bildDatei = "C:\\Users\\User\\Documents\\HaegerAufgabe\\Mercedes-Benz-logo.jpg";
            ImageData logoDatei = ImageDataFactory.create(bildDatei);

            // Creating an Image object
            Image logo = new Image(logoDatei);
            logo.scaleToFit(200f, 80f);
            logo.setFixedPosition(400, 760);
            Date datum = new Date();
            String dateToStr = DateFormat.getDateInstance().format(datum);
            String para5 = "\n\n Information zür neuen Automodellen \n\n Sehr geehrte Damen und Herren,";
            String para6 = "Hiermit informieren wir Sie über die aktuelle Modellliste informieren.\n\n";
            String para7 = """
                    \n\nWir möchten uns nochmals bei Ihnen für Ihre Vertraung bedanken und wünsche Ihnen eine gesunde und genossen Sommerzeit.

                     mit freundlichen Grüßen""";
            String para8 = "Mercedes Benz AG";

            // Paragraph erstellen
            Paragraph paragraph1 = new Paragraph(para1);
            Paragraph paragraph2 = new Paragraph(para2);
            Paragraph paragraph3 = new Paragraph(para3);
            Paragraph paragraph4 = new Paragraph(dateToStr).setFixedPosition(450,700,200);
            Paragraph paragraph5 = new Paragraph(para5);
            Paragraph paragraph6 = new Paragraph(para6);
            Paragraph paragraph7 = new Paragraph(para7);
            Paragraph paragraph8 = new Paragraph(para8);

            //Einfügen von Bilder
            document.add(logo);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);

            /*for (int j = 0; j < fahrzeugen.size(); j++) {
                Fahrzeug fahrzeug = fahrzeugen.get(j);
                String para6 = j + ") " + fahrzeug.getFahrzeugtyp() + " "+ fahrzeug.getFahrzeugbezeichnung()+ " "+fahrzeug.getHersteller()+ " "+fahrzeug.getLeistung()+ " "+fahrzeug.getVerkaufpreis();
                Paragraph paragraph6 = new Paragraph(para6);
                document.add(paragraph6);
            }*/

            float size[] = {120f,120f,120f,120f,120f};
            Table fahrzeugTable = new Table(size);

            Paragraph p1 = new Paragraph("FAHRZEUGTYP");
            Paragraph p2 = new Paragraph("FAHRZEUGBEZEICHNUNG");
            Paragraph p3 = new Paragraph("HERSTELLER");
            Paragraph p4 = new Paragraph("LEISTUNG");
            Paragraph p5 = new Paragraph("VERKAUFPREIS");

            Cell cell_1 = new Cell().add(p1);
            cell_1.setBackgroundColor(Color.LIGHT_GRAY);
            cell_1.setBorder(new SolidBorder(Color.DARK_GRAY,2));
            cell_1.setTextAlignment(TextAlignment.CENTER);
            Cell cell_2 = new Cell().add(p2);
            cell_2.setBackgroundColor(Color.LIGHT_GRAY);
            cell_2.setBorder(new SolidBorder(Color.DARK_GRAY,2));
            cell_2.setTextAlignment(TextAlignment.CENTER);
            Cell cell_3 = new Cell().add(p3);
            cell_3.setBackgroundColor(Color.LIGHT_GRAY);
            cell_3.setBorder(new SolidBorder(Color.DARK_GRAY,2));
            cell_3.setTextAlignment(TextAlignment.CENTER);
            Cell cell_4 = new Cell().add(p4);
            cell_4.setBackgroundColor(Color.LIGHT_GRAY);
            cell_4.setBorder(new SolidBorder(Color.DARK_GRAY,2));
            cell_4.setTextAlignment(TextAlignment.CENTER);
            Cell cell_5 = new Cell().add(p5);
            cell_5.setBackgroundColor(Color.LIGHT_GRAY);
            cell_5.setBorder(new SolidBorder(Color.DARK_GRAY,2));
            cell_5.setTextAlignment(TextAlignment.CENTER);

            fahrzeugTable.addCell(cell_1);
            fahrzeugTable.addCell(cell_2);
            fahrzeugTable.addCell(cell_3);
            fahrzeugTable.addCell(cell_4);
            fahrzeugTable.addCell(cell_5);

            for (int j = 0; j < fahrzeugen.size(); j++) {
                Fahrzeug fahrzeug = fahrzeugen.get(j);

                Paragraph p6 = new Paragraph(fahrzeug.getFahrzeugtyp());
                Paragraph p7 = new Paragraph(fahrzeug.getFahrzeugbezeichnung());
                Paragraph p8 = new Paragraph(fahrzeug.getHersteller());
                Paragraph p9 = new Paragraph(fahrzeug.getLeistung());
                Paragraph p10 = new Paragraph(fahrzeug.getVerkaufpreis()+ "€");

                Cell cell_6 = new Cell().add(p6);
                Cell cell_7 = new Cell().add(p7);
                Cell cell_8 = new Cell().add(p8);
                Cell cell_9 = new Cell().add(p9);
                Cell cell_10 = new Cell().add(p10);

                fahrzeugTable.addCell(cell_6);
                fahrzeugTable.addCell(cell_7);
                fahrzeugTable.addCell(cell_8);
                fahrzeugTable.addCell(cell_9);
                fahrzeugTable.addCell(cell_10);

            }

            document.add(fahrzeugTable);
            document.add(paragraph7);
            document.add(paragraph8);
            document.close();
            System.out.println("Inhalt hinzugefügt");

        }

    }
}
