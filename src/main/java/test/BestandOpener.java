/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author Koen
 */
public class BestandOpener {

//exceptions fixen, mogelijk statisch?
    public LinkedHashMap<String, DNASequence> openDNABestand(String filePad) {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        try {
            fastaSequentie = FastaReaderHelper.readFastaDNASequence(new File(filePad));
        } catch (IOException ex) {

        } catch (NullPointerException ex) {

        }
        return fastaSequentie;
    }

    public LinkedHashMap<String, DNASequence> openDNABestand(File fastaFile) {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        try {
            fastaSequentie = FastaReaderHelper.readFastaDNASequence(fastaFile);
        } catch (IOException ex) {

        } catch (NullPointerException ex) {

        }
        return fastaSequentie;
    }

}
