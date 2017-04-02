/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedHashMap;
import java.util.Map;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 *
 * @author Koen
 */
public class FASTASequentieHelper {

    public static FASTASequentie saveFASTASequentie(LinkedHashMap<String, DNASequence> fastaSequentie) throws OngeldigBestandException {
        if (fastaSequentie.isEmpty()) {
            throw new OngeldigBestandException();
        } else {
            Map.Entry<String, DNASequence> fastaEntry;
            fastaEntry = fastaSequentie.entrySet().iterator().next();
            return new FASTASequentie(fastaEntry); //voor nu wordt alleen de eerste fasta sequentie opgeslagen

        }
    }
}
