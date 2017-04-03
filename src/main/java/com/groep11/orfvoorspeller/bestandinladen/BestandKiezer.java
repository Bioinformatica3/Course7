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
 * 
 * @author Koen
 */
public class BestandKiezer {

    private String inputPad;
    private File inputFile;

    /**
     *
     */
    public BestandKiezer() {

    }

    /**
     *
     * @param fileLocatie
     */
    public BestandKiezer(String fileLocatie) {
        this.inputPad = fileLocatie;
    }

    /**
     *
     * @return
     */
    public String getInputPad() {
        return inputPad;
    }

    /**
     *
     * @param filePad
     */
    public void setInputPad(String filePad) {
        this.inputPad = filePad;
    }

    /**
     *
     * @return
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     *
     * @param newFile
     */
    public void setInputFile(File newFile) {
        this.inputFile = newFile;
    }

    

    /**
     *          gebruikt om de globale file variabele netjes de gekozen file te geven
     * @return
     * @throws OngeldigBestandException
     */
    public File setInputBrowseBestand() throws OngeldigBestandException {
        this.inputFile = fileBrowser();
        return this.inputFile;
    }

    /**
     *
     * @return
     * @throws OngeldigBestandException
     */
    public String setInputBrowseBestandPad() throws OngeldigBestandException {

        this.inputFile = setInputBrowseBestand();
        this.inputPad = inputFile.getAbsolutePath();

        return this.inputPad;
    }

    /**
     *
     * @return
     * @throws OngeldigBestandException
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
