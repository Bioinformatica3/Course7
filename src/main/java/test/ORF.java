/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Map;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.transcription.Frame;

/**
 *
 * @author Koen
 */
public class ORF extends FASTASequentie {
 
    //NOG TOSTRING EN COMPARETO?
    
    private static int aantalORFs;
    private int startPos;
    private int eindPos;
    private char strand;
            
    public ORF(String orfTitel, DNASequence inputSequentie , int startPositie, int eindPositie, char dnaStrand) {
        super(orfTitel,inputSequentie);
        this.startPos = startPositie;
        this.eindPos = eindPositie;
        this.strand = dnaStrand;
        aantalORFs += 1;
        
    }
    public static int getAantalORFs(){
        return aantalORFs;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPositie) {
        this.startPos = startPositie;
    }

    public int getEindPos() {
        return eindPos;
    }

    public void setEindPos(int eindPositie) {
        this.eindPos = eindPositie;
    }

    public char getStrand() {
        return strand;
    }

    public void setStrand(char newStrand) {
        this.strand = newStrand;
    }

 
    
    
}
