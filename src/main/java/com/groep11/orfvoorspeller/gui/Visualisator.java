/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groep11.orfvoorspeller.gui;

import com.groep11.orfvoorspeller.orfstonen.OngeldigeORFException;
import com.groep11.orfvoorspeller.orfstonen.ORF;
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

    private ArrayList<ORF> gevondenORFs;

    public Visualisator(ArrayList<ORF> orfs) {
        this.gevondenORFs = orfs;

    }

    public static void highlight(int beginPositie, int eindPositie, JTextPane highlightPane) throws BadLocationException {
        Highlighter.HighlightPainter painter;
        Highlighter highlight;

        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        highlight = highlightPane.getHighlighter();
        highlight.addHighlight(beginPositie, eindPositie, painter);

    }

    public void visualizeOnPanes(JTextPane forwardORFPane, JTextPane reverseORFPane) throws BadLocationException, OngeldigeORFException {
        String[] geheleForwardAminoSeqs;
        String[] geheleReverseAminoSeqs;

        geheleForwardAminoSeqs = forwardORFPane.getText().split("\n");
        geheleReverseAminoSeqs = reverseORFPane.getText().split("\n");

        for (ORF orf : gevondenORFs) {
            switch (orf.getStrand()) {
                case ('+'):
                    visualiseerORFs(orf, forwardORFPane, geheleForwardAminoSeqs);
                    break;
                    
                case ('-'):
                    visualiseerORFs(orf, reverseORFPane, geheleReverseAminoSeqs);
                    break;
                    
                default:
                    throw new OngeldigeORFException();

            }
        }
    }

    public void visualiseerORFs(ORF orf, JTextPane textPane, String[] textPaneTekst) throws BadLocationException, OngeldigeORFException {
        int orfStart;
        int orfEind;
        int orfFrame;
        
        orfStart = orf.getStartPos();
        orfEind = orf.getEindPos();
        
        orfFrame = orfStart % 3;
//        if (orfFrame == 0) {
//            orfFrame = 3; //reading frame 3 geeft bij modulo 3 als uitkomst 0, dit fixt dat
//        }

        for (int i = 0; i < orfFrame; i++) {
            orfStart += textPaneTekst[i].length();
            orfEind += textPaneTekst[i].length();

            
        }
        switch (orf.getStrand()) {
            case '+':
                highlight(orfStart, orfEind, textPane);
                break;
                
            case '-':
                highlight(orfEind, orfStart, textPane);
                break;
                
            default:
                throw new OngeldigeORFException();
        }

    }

//    public void visualiseerORFs(JTextPane forwardORFPane, JTextPane reverseORFPane) {
//        String phuck;
//        String phuck2;
//
//        String[] bird;
//        String[] istheword;
//
//        int orfStart;
//        int orfEind;
//        int orfFrame;
//
//        phuck = forwardORFPane.getText();
//        phuck2 = reverseORFPane.getText();
//
//        bird = phuck.split("\n");
//        istheword = phuck2.split("\n");
//
//        for (ORF orf : gevondenORFs) {
//            orfStart = orf.getStartPos();
//            orfEind = orf.getEindPos();
//            //System.out.println(orfStart + " " + orfEind);
//            orfFrame = orfStart % 3;
//            //  System.out.println(orfFrame + " Strand: " + orf.getStrand());
//            switch (orf.getStrand()) {
//
//                case ('+'):
//                    for (int i = 0; i < orfFrame; i++) {
//                        orfStart += bird[i].length();
//                        orfEind += bird[i].length();
//                    }
//
//                    try {
//                        Visualisator.highlight(orfStart, orfEind, aaSequentieTextPaneUpper);
//                    } catch (BadLocationException ex) {
//                        errorPopup("Ongeldige ORF posities gevonden");
//                    }
//
//                    break;
//
//                case ('-'):
//                    //waarom is de visualisatie verkeerd om?
//                    for (int i = 0; i < orfFrame; i++) {
//                        orfStart += istheword[i].length();
//                        orfEind += istheword[i].length();
//                    }
//                    try {
//                        //SEQUENTIE IS IN REVERSE DUS GOTTA TURN AROUND POSITIES
//                        Visualisator.highlight(orfEind, orfStart, aaSequentieTextPaneLower);
//                    } catch (BadLocationException ex) {
//                        errorPopup("Ongeldige ORF posities gevonden");
//                    }
//
//                    break;
//            }
//        }
//    }
}
