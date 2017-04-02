/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groep11.orfvoorspeller.orfstonen;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.transcription.Frame;
import com.groep11.orfvoorspeller.bestandinladen.FASTASequentie;

/**
 *
 * @author Koen
 */
public class AminoVoorspeller {

    private FASTASequentie fastaSequentie;

    public AminoVoorspeller(FASTASequentie fastaSequentie) {
        this.fastaSequentie = fastaSequentie;

    }

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
//naar amino voorspeller class?

    public String getAllAminoSequenties() {
        DNASequence dnaSequentie;
        HashMap<Frame, String> aminoSequenties;
        Frame frame;
        String aminoSequentieString;

        StringBuilder aaBuilder = new StringBuilder();
        StringBuilder aaComplementBuilder = new StringBuilder();

        dnaSequentie = fastaSequentie.getSequentie();
        aminoSequenties = AminoVoorspeller.bepaaPerFramelAminosString(dnaSequentie);

        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {

            frame = frameSequentie.getKey();
            aminoSequentieString = frameSequentie.getValue();

            //System.out.println(frame.wrap(dnaSequentie).getSequenceAsString());
            if (frame.toString().startsWith("REVERSED")) {
                if (aaComplementBuilder.length() != 0) {
                    aaComplementBuilder.append("\n");

                }
                aaComplementBuilder.append(aminoSequentieString);

            } else {
                if (aaBuilder.length() != 0) {
                    aaBuilder.append("\n");
                }
                aaBuilder.append(aminoSequentieString);
            }
        }
        aaBuilder.append(aaComplementBuilder.reverse());

        return aaBuilder.toString();

    }

    //naar amino voorspeller class?
    public String getForwardAminoSequenties() {
        DNASequence dnaSequentie;
        HashMap<Frame, String> aminoSequenties;
        Frame frame;
        String aminoSequentieString;
        String aminoSequentieInFrame;
        StringBuilder aaBuilder = new StringBuilder();

        dnaSequentie = fastaSequentie.getSequentie();
        aminoSequenties = AminoVoorspeller.bepaaPerFramelAminosString(dnaSequentie);

        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {

            frame = frameSequentie.getKey();
            aminoSequentieString = frameSequentie.getValue();

            aminoSequentieInFrame = bepaalFrames(frame, aminoSequentieString);
            //nog fixen dat de frames in goede volgorde appended worden (cases)?
            if (!frame.toString().startsWith("REVERSED")) {
                if (aaBuilder.length() != 0) {
                    aaBuilder.append("\n");
                }
                aaBuilder.append(aminoSequentieInFrame);

            }
        }

        return aaBuilder.toString();

    }

    public String getReverseAminoSequenties() {
        DNASequence dnaSequentie;
        HashMap<Frame, String> aminoSequenties;
        Frame frame;
        String aminoSequentieString;
        String aminoSequentieInFrame;

        StringBuilder aaComplementBuilder = new StringBuilder();

        dnaSequentie = fastaSequentie.getSequentie();
        aminoSequenties = AminoVoorspeller.bepaaPerFramelAminosString(dnaSequentie);

        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {

            frame = frameSequentie.getKey();
            aminoSequentieString = frameSequentie.getValue();

            aminoSequentieInFrame = bepaalFrames(frame, aminoSequentieString);
            //nog fixen dat de frames in goede volgorde appended worden (cases)?
            if (frame.toString().startsWith("REVERSED")) {
                if (aaComplementBuilder.length() == 0) {
                    aaComplementBuilder.append(aminoSequentieInFrame);

                } else {
                    aaComplementBuilder.insert(0,aminoSequentieInFrame + "\n");
                }

            }
        }

        return aaComplementBuilder.toString();

    }

    public String bepaalFrames(Frame readingFrame, String aaSequentie) {
        StringBuilder aaBuilder = new StringBuilder();
        aaSequentie = aaSequentie.replace("", "  ").trim();
        switch (readingFrame) {

            case ONE:
                aaBuilder.append(aaSequentie);
                break;

            case TWO:
                aaBuilder.append(" ").append(aaSequentie);
                break;

            case THREE:
                aaBuilder.append("  ").append(aaSequentie);
                break;

            case REVERSED_ONE:
                aaBuilder.append(aaSequentie);
                aaBuilder.reverse();
                break;

            case REVERSED_TWO:
                aaBuilder.append(aaSequentie).append(" ");
                aaBuilder.reverse();
                break;

            case REVERSED_THREE:
                aaBuilder.append(aaSequentie).append("  ");
                aaBuilder.reverse();
                break;

        }
        return aaBuilder.toString();
    }


}
