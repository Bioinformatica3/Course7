/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.transcription.Frame;

/**
 *
 * @author Koen
 */
public class AminoVoorspeller {

    private LinkedHashMap<Frame, ProteinSequence> aminoSequenties;
    private LinkedHashMap<Frame, String> aminoStrings;

    public AminoVoorspeller(DNASequence dnaSequentie) {
        this.aminoSequenties = bepaalAminoSequenties(dnaSequentie);
        this.aminoStrings = bepaalAminoSequentiesString(dnaSequentie);

    }
    
//samenvoegen?
    public static LinkedHashMap<Frame, ProteinSequence> bepaalAminoSequenties(DNASequence dnaSequentie) {
        LinkedHashMap<Frame, ProteinSequence> aminos = new LinkedHashMap<Frame, ProteinSequence>(7);
        RNASequence rnaSequentie;
        ProteinSequence aminoSequentie;
        
        for (Frame frameShift : Frame.getAllFrames()) {
            rnaSequentie = dnaSequentie.getRNASequence(frameShift);
            aminoSequentie = rnaSequentie.getProteinSequence();
            aminos.put(frameShift, aminoSequentie);
        }
        return aminos;
    }

    public static LinkedHashMap<Frame, String> bepaalAminoSequentiesString(DNASequence dnaSequentie) {
        LinkedHashMap<Frame, String> aminos = new LinkedHashMap<Frame, String>(7);
        RNASequence rnaSequentie;
        ProteinSequence aminoSequentie;
        String aminoSequentieString;
        
        for (Frame frameShift : Frame.getAllFrames()) {
            rnaSequentie = dnaSequentie.getRNASequence(frameShift);
            aminoSequentie = rnaSequentie.getProteinSequence();
            aminoSequentieString = aminoSequentie.getSequenceAsString();
            aminos.put(frameShift, aminoSequentieString);
        }
        return aminos;
    }

    public HashMap<Frame, ProteinSequence> getAminoSequenties() {
        return this.aminoSequenties;
    }

}
