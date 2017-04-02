/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groep11.orfvoorspeller.bestandinladen;

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
//nog mogelijkheid om bestand lokaal op te slaan?

//mogelijk statisch?
    public LinkedHashMap<String, DNASequence> openDNABestand(String filePad) throws IOException, NullPointerException {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        fastaSequentie = FastaReaderHelper.readFastaDNASequence(new File(filePad));

        return fastaSequentie;
    }

    public LinkedHashMap<String, DNASequence> openDNABestand(File fastaFile) throws IOException, NullPointerException {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        fastaSequentie = FastaReaderHelper.readFastaDNASequence(fastaFile);

        return fastaSequentie;
    }

}
