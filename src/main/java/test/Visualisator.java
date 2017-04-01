/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.transcription.Frame;

/**
 *
 * @author Koen
 */
public class Visualisator {

    private FASTASequentie fastaSequentie;

    public Visualisator(FASTASequentie sequentieTeTonen) {
        this.fastaSequentie = sequentieTeTonen;
    }

    //naar fasta sequentie class?
    public String getSequentieMetComplementair() {

        DNASequence dnaSequentie = fastaSequentie.getSequentie();
        return (dnaSequentie.getSequenceAsString() + "\n" + dnaSequentie.getComplement().getSequenceAsString());
    }

//    public void visualiseerAminoSequentie(FASTASequentie fastaSequentie) {
//        DNASequence dnaSequentie;
//        HashMap<Frame, String> aminoSequenties;
//        Frame frame;
//        String aminoSequentieString;
//
//        StringBuilder aaComplementBuilder = new StringBuilder();
//        StringBuilder aaBuilder = new StringBuilder();
//
//        dnaSequentie = fastaSequentie.getSequentie();
//        aminoSequenties = AminoVoorspeller.bepaaPerFramelAminosString(dnaSequentie);
//
//        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {
//
//            frame = frameSequentie.getKey();
//            aminoSequentieString = frameSequentie.getValue();
//
//            //System.out.println(frame.wrap(dnaSequentie).getSequenceAsString());
//            if (frame.toString().startsWith("REVERSED")) {
//                if (aaComplementBuilder.length() != 0) {
//                    aaComplementBuilder.append("\n");
//
//                }
//                aaComplementBuilder.append(aminoSequentieString.replace("", "  "));
//
//            } else {
//                if (aaBuilder.length() != 0) {
//                    aaBuilder.append("\n");
//                }
//                aaBuilder.append(aminoSequentieString.replace("", "  "));
//
////                    System.out.println(aminoSequentieString);
////                    aaPaneDoc.insertString(aaPaneDoc.getLength(), aminoSequentieString + "\n", null);
//            }
//        }
//        aaOrigineleSequentieTextPane.setText(aaBuilder.toString());
//        aaComplementSequentieTextPane.setText(aaComplementBuilder.toString());
//
//    }
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

             aminoSequentieInFrame = bepaalFrames(frame,aminoSequentieString);
            //nog fixen dat de frames in goede volgorde appended worden (cases)?
            if (frame.toString().startsWith("REVERSED")) {
                System.out.println("Reverse frame skipped");

            } else {
                if (aaBuilder.length() != 0) {
                    aaBuilder.append("\n");
                }
                aaBuilder.append(aminoSequentieInFrame);
            }
        }

        return aaBuilder.toString();

    }
    //naar amino voorspeller class?

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
            
            aminoSequentieInFrame = bepaalFrames(frame,aminoSequentieString);
            //nog fixen dat de frames in goede volgorde appended worden (cases)?
            if (frame.toString().startsWith("REVERSED")) {
                if (aaComplementBuilder.length() != 0) {
                    aaComplementBuilder.append("\n");

                }
                aaComplementBuilder.append(aminoSequentieInFrame);

            } else {
                System.out.println("Forward frame skipped");
            }
        }

        return aaComplementBuilder.toString();

    }

    //gebruik voor aa sequenties
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

//    public void toonORFs(FASTASequentie ingeladenSequentie) {
//        int orfStart;
//        int orfEind;
//
//        ArrayList<ORF> gevondenORFs = new ArrayList<>();
//        ORFSearcher orfFinder = new ORFSearcher(ingeladenSequentie);
//
//        gevondenORFs = orfFinder.getORFLijst();
//        for (ORF orfMatch : gevondenORFs) {
//
//            try {
//
//                orfStart = orfMatch.getStartPos();
//                orfEind = orfMatch.getEindPos();
//                highlight(orfStart, orfEind);
//            }  (BadLocationException ex) {
//             ex.printStackTrace();
//            }
//        }
//
//    }
    public static void highlight(int beginPositie, int eindPositie, JTextPane highlightPane) throws BadLocationException {
        Highlighter.HighlightPainter painter;
        Highlighter highlight;

        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        highlight = highlightPane.getHighlighter();
        highlight.addHighlight(beginPositie, eindPositie, painter);

    }
    
    public void visualiseerORFs(){
        
    }

}
