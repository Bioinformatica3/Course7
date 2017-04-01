/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.swing.JTextField;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author Koen
 */
public class BestandOpener {

    public  LinkedHashMap<String, DNASequence> openDNABestand(String filePad) throws IOException, NullPointerException {
        return FastaReaderHelper.readFastaDNASequence(new File(filePad));
    }

    public LinkedHashMap<String, DNASequence> openDNABestand(File fastaFile) throws IOException, NullPointerException {
        return FastaReaderHelper.readFastaDNASequence(fastaFile);

    }
//nodig?
    public  LinkedHashMap<String, DNASequence> laadFASTABestand(File fastaFile) { //error handling
        LinkedHashMap<String, DNASequence> fastaData = new LinkedHashMap<>();

        try {

            fastaData = openDNABestand(fastaFile);

        } catch (IOException e) {
            e.printStackTrace(); //fix
        }
        return fastaData;

    }
}
