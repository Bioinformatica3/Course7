/**
 * Deze applicatie biedt gebruikers de mogelijkheid om een DNA sequentie (in FASTA formaat) in te laden
 * en hierin aanwezige ORFs (gedefineerd als een DNA sequentie dat in frame begint met ATG en eindigt met een stop codon)
 * te vinden,visualiseren en eventueel op te slaan.
 * Deze applicatie volgt in grote lijnen het ontwerp, om de code overzichtelijker te houden
 * zijn er per functionaliteit (package) wel meer classes en methodes toegevoegd.
 *
 * Ontwikkelaars: Glenn Hulscher, Tijs van Lieshout, Koen van der Heide en Milo van de Griend
 * Datum laatste versie: 03-04-2017
 *
 * Bekende bugs:
 * - ORFs worden in de database nog niet verbonden aan de DNA sequentie.
 * - Als de FASTA file meerdere sequenties bevat wordt alleen de eerste sequentie hier verwerkt.
 *
 */
package com.groep11.orfvoorspeller.bestandinladen;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * Deze class bevat de logica om naar een bestand te browsen en en het gekozen
 * bestand te retouneren.
 *
 * @author Koen
 */
public class BestandKiezer {

    private String inputPad;
    private File inputFile;

    /**
     * Lege constructor om lege instantiering mogelijk te maken.
     */
    public BestandKiezer() {

    }

    /**
     * Constructor voor wanneer de locatie van het bestand al bekend is (maar
     * het bestand zelf nog opgehaald moet worden).
     *
     * @param fileLocatie locatie van het bestand als String.
     */
    public BestandKiezer(String fileLocatie) {
        this.inputPad = fileLocatie;
    }

    /**
     * Retouneert de locatie van het bestand.
     *
     * @return bestand locatie op de computer als String.
     */
    public String getInputPad() {
        return inputPad;
    }

    /**
     * Zet de locatie van het bestand.
     *
     * @param filePad de bestands locatie op de computer.
     */
    public void setInputPad(String filePad) {
        this.inputPad = filePad;
    }

    /**
     * Retouneert het gekozen bestand.
     *
     * @return het gekozen bestand als File.
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * Verander het gekozen bestand.
     *
     * @param newFile het nieuwe bestand.
     */
    public void setInputFile(File newFile) {
        this.inputFile = newFile;
    }

    /**
     * Deze methode wordt enkel gebruikt om de globale file variabele netjes de
     * gekozen file te geven door de file browser aan te roepen.
     *
     * @return het gekozen bestand als File.
     * @throws OngeldigBestandException wanneer de gebruiker het kiezen van een
     * bestand annuleert.
     */
    public File setInputBrowseBestand() throws OngeldigBestandException {
        this.inputFile = fileBrowser();
        return this.inputFile;
    }

    /**
     * Deze methode roept de setInputBrowseBestand methode aan maar bepaald ook
     * meteen de locatie van het bestand en retouneert dit.
     *
     * @return de file locatie van het gekozen bestand als String.
     * @throws OngeldigBestandException wanneer de gebruiker het kiezen van een
     * bestand annuleert.
     */
    public String setInputBrowseBestandPad() throws OngeldigBestandException {

        this.inputFile = setInputBrowseBestand();
        this.inputPad = inputFile.getAbsolutePath();

        return this.inputPad;
    }

    /**
     * Methode dat de werkelijke bestand browser aanmaakt en het door de
     * gebruiker gekozen bestand retouneert. Is statisch omdat deze browser ook
     * buiten deze class van nut kan zijn en onafhankelijk van deze class kan
     * functioneren.
     *
     * @return @throws OngeldigBestandException wanneer de gebruiker geen
     * bestand kiest.
     */
    public static File fileBrowser() throws OngeldigBestandException {
        int bestandFound;

        File input = null;

        JFileChooser keuze = new JFileChooser();
        keuze.setDialogTitle("LAAD DAT BESTAND BRO");
        bestandFound = keuze.showOpenDialog(null);

        if (bestandFound == JFileChooser.APPROVE_OPTION) {
            input = keuze.getSelectedFile();

        } else {
            throw new OngeldigBestandException();
        }
        return input;
    }

}
