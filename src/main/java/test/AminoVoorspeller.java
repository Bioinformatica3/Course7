/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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

//    private LinkedHashMap<Frame, ProteinSequence> aminoSequenties;
//    private LinkedHashMap<Frame, String> aminoStrings;
//
//    public AminoVoorspeller(DNASequence dnaSequentie) {
//        this.aminoSequenties = bepaalPerFrameAminos(dnaSequentie);
//        this.aminoStrings = bepaaPerFramelAminosString(dnaSequentie);
//
//    }
    public static LinkedHashMap<Frame, ProteinSequence> bepaalPerFrameAminos(DNASequence dnaSequentie) {
        LinkedHashMap<Frame, ProteinSequence> aminos = new LinkedHashMap<Frame, ProteinSequence>(6);
        ProteinSequence aminoSequentie;

        for (Frame readingFrame : Frame.getAllFrames()) {
            aminoSequentie = bepaalAminos(readingFrame, dnaSequentie);
            aminos.put(readingFrame, aminoSequentie);
        }
        return aminos;
    }

    public static ProteinSequence bepaalAminos(Frame readingFrame, DNASequence dnaSequentie) {
        RNASequence rnaSequentie;
        ProteinSequence aminoSequentie;

        rnaSequentie = dnaSequentie.getRNASequence(readingFrame);
        aminoSequentie = rnaSequentie.getProteinSequence();
        return aminoSequentie;
    }

    public static LinkedHashMap<Frame, String> bepaaPerFramelAminosString(DNASequence dnaSequentie) {
        LinkedHashMap<Frame, String> aminos = new LinkedHashMap<Frame, String>(6);
        ProteinSequence aminoSequentie;
        String aminoSequentieString;

        for (Frame readingFrame : Frame.getAllFrames()) {
            aminoSequentie = bepaalAminos(readingFrame, dnaSequentie);
            aminoSequentieString = aminoSequentie.getSequenceAsString();
            aminos.put(readingFrame, aminoSequentieString);
        }
        return aminos;
    }

//    public HashMap<Frame, ProteinSequence> getAminoSequenties() {
//        return this.aminoSequenties;
//    }
}
