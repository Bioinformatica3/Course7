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
     * Constructor noodzakelijk omdat de ORFs al bekend moeten zijn voordat het
     * zinvol is om deze class te instantieren.
     *
     * @param orfs Lijst met alle gevonden ORF objecten.
     */
    public Visualisator(ArrayList<ORF> orfs) {
        this.gevondenORFs = orfs;

    }

    /**
     * Methode om de highlights werkelijk toe te voegen op een gegeven
     * textPane,deze methode is statisch omdat het onafhankelijk van de gevonden
     * ORFs kan functioneren.
     *
     * @param beginPositie van de ORF op de gehele sequentie.
     * @param eindPositie van de ORF op de gehele sequentie.
     * @param highlightPane TextPane waarop de highlight plaats moet vinden.
     * @throws BadLocationException Wanneer de locaties van de posities niet te
     * matchen zijn op de textpane (posities zijn bijvoorbeeld groter dan de
     * lengte van de String in de textpane)
     */
    public static void highlight(int beginPositie, int eindPositie, JTextPane highlightPane) throws BadLocationException {
        Highlighter.HighlightPainter painter;
        Highlighter highlight;

        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        highlight = highlightPane.getHighlighter();
        highlight.addHighlight(beginPositie, eindPositie, painter);

    }

    /**
     * Deze methode haalt de aminozuursequenties op uit de textPanes als
     * String[] arrays (waarbij elke String 1 aminozuur frame is) en laat de ORF
     * posities matchen op deze sequenties.
     *
     * @param forwardORFPane TextPane dat de forward aminosequenties bevat en
     * waarop dus de forward ORFs moeten worden gevisualiseerd.
     * @param reverseORFPane TextPane dat de reverse aminosequenties bevat en
     * waarop dus de reverse ORFs moeten worden gevisualiseerd.
     *
     * @throws BadLocationException Wanneer de locaties van de posities niet te
     * matchen zijn op de textpane (posities zijn bijvoorbeeld groter dan de
     * lengte van de String in de textpane)
     * @throws OngeldigeORFException Mocht een ORF geen strand hebben
     * meegekregen.
     */
    public void visualizeOnPanes(JTextPane forwardORFPane, JTextPane reverseORFPane) throws BadLocationException, OngeldigeORFException {
        String[] geheleForwardAminoSeqs;
        String[] geheleReverseAminoSeqs;

        geheleForwardAminoSeqs = forwardORFPane.getText().split("\n");
        geheleReverseAminoSeqs = reverseORFPane.getText().split("\n");

        for (ORF orf : gevondenORFs) {
            switch (orf.getStrand()) {
                case ('+'): //forward strands
                    visualiseerORFs(orf, forwardORFPane, geheleForwardAminoSeqs);
                    break;

                case ('-'): //reverse strands
                    visualiseerORFs(orf, reverseORFPane, geheleReverseAminoSeqs);
                    break;

                default:
                    throw new OngeldigeORFException();

            }
        }
    }

    /**
     * Deze methode matcht de ORF posities met de posities in de textPane en
     * laat deze highlighten door de highlight methode.
     *
     * @param orf de ORF die gehighlight moet worden.
     * @param textPane TextPane waarop het highlighten plaatsvindt.
     * @param textPaneTekst de tekst (iedere regel gesplitst tot een String[])
     * van de textPane
     *
     * @throws BadLocationException Wanneer de locaties van de posities niet te
     * matchen zijn op de textpane (posities zijn bijvoorbeeld groter dan de
     * lengte van de String in de textpane)
     * @throws OngeldigeORFException Mocht een ORF geen strand hebben
     * meegekregen.
     */
    public void visualiseerORFs(ORF orf, JTextPane textPane, String[] textPaneTekst) throws BadLocationException, OngeldigeORFException {
        int orfStart;
        int orfEind;
        int orfFrame;

        orfStart = orf.getStartPos();
        orfEind = orf.getEindPos();

        orfFrame = orfStart % 3;

        //Omdat alle 3 de sequenties in feite 1 lange String zijn in de textpane moeten de posities van ORFs
        //op deze string aangepast worden afhankelijk van hun reading frame.
        for (int i = 0; i < orfFrame; i++) {
            orfStart += textPaneTekst[i].length();
            orfEind += textPaneTekst[i].length();

        }
        switch (orf.getStrand()) {// + is een positieve/forward ORF en - een negatieve/reverse
            case '+':
                highlight(orfStart, orfEind, textPane);
                break;

            case '-':
                highlight(orfEind, orfStart, textPane); //negatieve ORFs tellen vanaf hun eindpositie tot hun start (zijn inverted)
                break;

            default:
                throw new OngeldigeORFException();
        }

    }
}
