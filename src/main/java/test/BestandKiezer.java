/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.swing.JFileChooser;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author Koen
 */
public class BestandKiezer {

    private String inputPad;
    private File inputFile;

    public BestandKiezer() {

    }

    public BestandKiezer(String fileLocatie) {
        this.inputPad = fileLocatie;
    }

    public String getInputPad() {
        return inputPad;
    }

    public void setInputPad(String filePad) {
        this.inputPad = filePad;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File newFile) {
        this.inputFile = newFile;
    }

    //gebruikt om de globale file variabele netjes de gekozen file te geven
    public File browseBestand() {
        try {

            this.inputFile = fileBrowser();

        } catch (NullPointerException e) { //fixen

            e.printStackTrace();
        }

        return this.inputFile;
    }

    public String browseBestandPad() {
        try {

            this.inputFile = fileBrowser();
            this.inputPad = inputFile.getAbsolutePath();
        } catch (NullPointerException e) { //fixen

            e.printStackTrace();
        }

        return this.inputPad;
    }

    private File fileBrowser() {
        int bestandFound;

        File input = null;

        JFileChooser keuze = new JFileChooser();
        keuze.setDialogTitle("LAAD DAT BESTAND BRO");
        bestandFound = keuze.showOpenDialog(null);

        if (bestandFound == JFileChooser.APPROVE_OPTION) {
            input = keuze.getSelectedFile();

        } else {
            // throw new FaalException();
        }
        return input;
    }

 

}
