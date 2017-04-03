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
package com.groep11.orfvoorspeller.orfstonen;

import com.groep11.orfvoorspeller.bestandinladen.FASTASequentie;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 * De data van een ORF kan worden opgeslagen als instantie van deze class.
 * Overerft de FASTASequentie class aangezien ORFs in deze context feitelijk
 * kleinere FASTA DNA sequenties zijn.
 *
 * @author Koen
 */
public class ORF extends FASTASequentie {

    private static int aantalORFs;
    private int startPos;
    private int eindPos;
    private char strand;

    /**
     * Constructor van een ORF, het is dus verplicht dat een ORF aangemaakt
     * wordt met titel,sequentie, posities en strands.
     *
     * @param orfTitel titel van de ORF.
     * @param inputSequentie DNA sequentie van de ORF.
     * @param startPositie start positie van de ORF op de gehele DNA sequentie.
     * @param eindPositie eind positie van de ORF op de gehele DNA sequentie.
     * @param dnaStrand strand waarop de ORF zich bevindt (+ of -).
     */
    public ORF(String orfTitel, DNASequence inputSequentie, int startPositie, int eindPositie, char dnaStrand) {
        super(orfTitel, inputSequentie);
        this.startPos = startPositie;
        this.eindPos = eindPositie;
        this.strand = dnaStrand;
        aantalORFs += 1;

    }

    /**
     *
     * Methode die het totaal aantal ORFs die zijn aangemaakt retourneert.
     */
    public static int getAantalORFs() {
        return aantalORFs;
    }

    /**
     *
     * Methode die de startpositie van het ORF op de gehele DNA sequentie retourneert.
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     *
     * Methode om de startpositie van de ORF aan te passen.
     */
    public void setStartPos(int startPositie) {
        this.startPos = startPositie;
    }

    /**
     *
     * Methode die de eindpositie van de ORF op de gehele DNA sequentie retourneert.
     */
    public int getEindPos() {
        return eindPos;
    }

    /**
     *
     * Methode om de eindpositie van de ORF aan te passen.
     */
    public void setEindPos(int eindPositie) {
        this.eindPos = eindPositie;
    }

    /**
     *
     * Methode die de bijbehorende strand van de ORF opvraagt.
     */
    public char getStrand() {
        return strand;
    }

    /**
     *
     * Methode om de strand van een ORF te veranderen.
     */
    public void setStrand(char newStrand) {
        this.strand = newStrand;
    }

    /**
     *
     * Override van de toString() methode om het object retourneerbaar te maken
     * als een leesbare String (met de variabelen duidelijk aangegeven).
     */
    @Override
    public String toString() {
        return "ORF{" + "titel= " + super.getTitel() + "start positie= " + startPos + ", eind positie= " + eindPos + ", strand= " + strand + '}';
    }

}
