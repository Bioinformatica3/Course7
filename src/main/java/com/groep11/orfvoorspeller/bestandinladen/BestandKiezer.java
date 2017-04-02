/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public File setInputBrowseBestand() throws OngeldigBestandException {
        this.inputFile = fileBrowser();
        return this.inputFile;
    }

    public String setInputBrowseBestandPad() throws OngeldigBestandException {

        this.inputFile = setInputBrowseBestand();
        this.inputPad = inputFile.getAbsolutePath();

        return this.inputPad;
    }

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
