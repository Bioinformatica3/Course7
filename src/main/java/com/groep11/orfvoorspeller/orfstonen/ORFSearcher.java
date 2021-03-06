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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 * Bevat de logica om naar ORFs te zoeken in een DNA sequentie en deze op te
 * slaan in een lijst als ORF class objecten.
 *
 * @author Koen
 */
public class ORFSearcher {

    private static final String STARTSTOPCODON = "ATG([ATGC]{3})+?(TAA|TGA|TAG)";
    private static final Pattern ORFPATROON = Pattern.compile(STARTSTOPCODON);
    private ArrayList<ORF> orfLijst = new ArrayList<>();

    /**
     * Zoekt ORFs in een gegeven FASTA sequentie (de bijbehorende DNA sequentie
     * wordt uit de FASTA sequentie gehaald).
     *
     * @param fastaSequentie FASTA sequentie waarvan de DNA sequentie als basis
     * gebruikt wordt om ORFs in te zoeken.
     * @throws CompoundNotFoundException wanneer de DNA sequentie ongeldige
     * karakters bevat.
     */
    public void vindORFs(FASTASequentie fastaSequentie) throws CompoundNotFoundException {
        Matcher orfMatches;
        String[] dnaStrings = new String[2];
        String orfTitel = "";
        String orfString = "ORF_";
        char strand = ' ';

        int orfId = 0;
        int orfBegin;
        int orfEind;

        int strandCounter = 0;

        dnaStrings[0] = fastaSequentie.getSequentie().getSequenceAsString();
        dnaStrings[1] = fastaSequentie.getSequentie().getReverseComplement().getSequenceAsString();

        for (String dnaString : dnaStrings) {
            orfMatches = ORFPATROON.matcher(dnaString);
            while (orfMatches.find()) {

                orfId++;
                orfTitel = orfString + Integer.toString(orfId);

                orfBegin = orfMatches.start();
                orfEind = orfMatches.end();

                //forward strand is eerste string (0), reverse tweede (1).
                switch (strandCounter) {
                    case (0):

                        strand = '+';
                        break;
                    case (1):
                        strand = '-';
                        //de zoekopdracht vindt plaats in de omgedraaide reverse sequentie, 
                        //voor correcte orf posities moeten deze weer terug gedraaid worden
                        orfBegin = dnaString.length() - orfMatches.start();
                        orfEind = dnaString.length() - orfMatches.end();

                        break;
                }

                DNASequence orfSequentie = new DNASequence(orfMatches.group());
                ORF foundORF = new ORF(orfTitel, orfSequentie, orfBegin, orfEind, strand);

                orfLijst.add(foundORF);
            }
            strandCounter++;

        }

    }

    /**
     * Retouneert de lijst met alle ORF objecten.
     *
     * @return de lijst gevuld met alle gevonden ORF objecten.
     */
    public ArrayList<ORF> getORFLijst() {
        return this.orfLijst;
    }

    /**
     * Set een nieuwe lijst met ORF objecten.
     *
     * @param newORFs de nieuwe lijst met ORF objecten.
     */
    public void setORFLijst(ArrayList<ORF> newORFs) {
        this.orfLijst = newORFs;
    }

    @Override
    public String toString() {
        return "ORFSearcher{" + "orfLijst= " + orfLijst + '}';
    }

}
