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
 *
 * @author Koen
 */
public class ORF extends FASTASequentie {

    private static int aantalORFs;
    private int startPos;
    private int eindPos;
    private char strand;

    /**
     *
     * @param orfTitel
     * @param inputSequentie
     * @param startPositie
     * @param eindPositie
     * @param dnaStrand
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
     * Methode die het aantal ORFs in de input sequentie retourneert.
     */
    public static int getAantalORFs() {
        return aantalORFs;
    }

    /**
     *
     * Methode die de startpositie van het ORF retourneert.
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     *
     * Methode die de startpositie in een instance variable opslaat.
     */
    public void setStartPos(int startPositie) {
        this.startPos = startPositie;
    }

    /**
     *
     * Methode die de eindpositie retourneert.
     */
    public int getEindPos() {
        return eindPos;
    }

    /**
     *
     * Methode die de eindpositie in een instance variable opslaat.
     */
    public void setEindPos(int eindPositie) {
        this.eindPos = eindPositie;
    }

    /**
     *
     * Methode die de bijbehorende strand opvraagt.
     */
    public char getStrand() {
        return strand;
    }

    /**
     *
     * Methode die de strand opslaat in een instance variable.
     */
    public void setStrand(char newStrand) {
        this.strand = newStrand;
    }
    
    /**
     *
     * Override van de toString() methode om het object retourneerbaar te maken als 
     * string (met duidelijke eigenschappen, ten opzichte van een object reference).
     */ 

    @Override
    public String toString() {
        return "ORF{" + "titel= " + super.getTitel() + "start positie= " + startPos + ", eind positie= " + eindPos + ", strand= " + strand + '}';
    }

}
