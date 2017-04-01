/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 *
 * @author Koen
 */
public class FASTASequentie {

    private DNASequence dnaSequentie;
    private String dnaTitel;
    
   public FASTASequentie(String inputTitel, DNASequence inputSequentie) {
        this.dnaTitel = inputTitel;
        this.dnaSequentie = inputSequentie;

    }
    
    public FASTASequentie(Entry<String, DNASequence> inputSequentie) {
        this.dnaTitel = inputSequentie.getKey();
        this.dnaSequentie = inputSequentie.getValue();

    }
    public void setTitel(String newTitel){
        this.dnaTitel = newTitel;
    }
    public String getTitel(){
        return this.dnaTitel;
    }
    public void setSequentie(DNASequence newSequentie){
        this.dnaSequentie = newSequentie;
    }
    public DNASequence getSequentie(){
        return this.dnaSequentie;
    }
    public String getSequentieString(){
        return this.dnaSequentie.getSequenceAsString();
    }
    
  
}
