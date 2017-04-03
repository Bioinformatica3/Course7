/**
 * Deze applicatie biedt gebruikers de mogelijkheid om een DNA sequentie (in FASTA formaat) in te laden
 * en hierin aanwezige ORFs (gedefineerd als een DNA sequentie dat in frame begint met ATG en eindigt met een stop codon)
 * te vinden,visualiseren en eventueel op te slaan.
 * Deze applicatie volgt in grote lijnen het ontwerp, om de code overzichtelijker te houden
 * zijn er per functionaliteit (package) wel meer classes en methodes toegevoegd.
 *
 * Ontwikkelaars: Glenn Hulscher, Tijs van Lieshout, Koen van der Heide en Milo van de Griend
 * Datum laatste versie: 03-04-2017
 *
 * Bekende bugs:
 * - ORFs worden in de database nog niet verbonden aan de DNA sequentie.
 * - Als de FASTA file meerdere sequenties bevat wordt alleen de eerste sequentie hier verwerkt.
 *
 */
package com.groep11.orfvoorspeller.gui;

import com.groep11.orfvoorspeller.orfstonen.OngeldigeORFException;
import com.groep11.orfvoorspeller.orfstonen.ORF;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * Class dat de GUI ondersteunt om de posities van de gevonden ORFs te
 * highlighten op de aminosequentie textPanes.
 *
 * @author Koen
 */
public class Visualisator {

    private ArrayList<ORF> gevondenORFs;

    /**
     *
     * @param orfs
     */
    public Visualisator(ArrayList<ORF> orfs) {
        this.gevondenORFs = orfs;

    }

    /**
     *
     * @param beginPositie
     * @param eindPositie
     * @param highlightPane
     * @throws BadLocationException
     */
    public static void highlight(int beginPositie, int eindPositie, JTextPane highlightPane) throws BadLocationException {
        Highlighter.HighlightPainter painter;
        Highlighter highlight;

        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        highlight = highlightPane.getHighlighter();
        highlight.addHighlight(beginPositie, eindPositie, painter);

    }

    /**
     *
     * @param forwardORFPane
     * @param reverseORFPane
     * @throws BadLocationException
     * @throws OngeldigeORFException
     */
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

    /**
     *
     * @param orf
     * @param textPane
     * @param textPaneTekst
     * @throws BadLocationException
     * @throws OngeldigeORFException
     */
    public void visualiseerORFs(ORF orf, JTextPane textPane, String[] textPaneTekst) throws BadLocationException, OngeldigeORFException {
        int orfStart;
        int orfEind;
        int orfFrame;

        orfStart = orf.getStartPos();
        orfEind = orf.getEindPos();

        orfFrame = orfStart % 3;

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
}
