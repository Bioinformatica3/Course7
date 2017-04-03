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
package com.groep11.orfvoorspeller.bestandinladen;

import java.util.Map.Entry;
import java.util.Objects;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 *
 * @author Koen
 */
public class FASTASequentie {

    private DNASequence dnaSequentie;
    private String dnaTitel;

    /**
     *
     * @param inputTitel
     * @param inputSequentie
     */
    public FASTASequentie(String inputTitel, DNASequence inputSequentie) {
        this.dnaTitel = inputTitel;
        this.dnaSequentie = inputSequentie;

    }

    /**
     *
     * @param inputSequentie
     */
    public FASTASequentie(Entry<String, DNASequence> inputSequentie) {
        this.dnaTitel = inputSequentie.getKey();
        this.dnaSequentie = inputSequentie.getValue();

    }

    /**
     *
     * @param newTitel
     */
    public void setTitel(String newTitel) {
        this.dnaTitel = newTitel;
    }

    /**
     *
     * @return
     */
    public String getTitel() {
        return this.dnaTitel;
    }

    /**
     *
     * @param newSequentie
     */
    public void setSequentie(DNASequence newSequentie) {
        this.dnaSequentie = newSequentie;
    }

    /**
     *
     * @return
     */
    public DNASequence getSequentie() {
        return this.dnaSequentie;
    }

    /**
     *
     * @return
     */
    public String getSequentieString() {
        return this.dnaSequentie.getSequenceAsString();
    }

    /**
     *
     * @return
     */
    public String getSequentieMetComplementair() {
        return this.dnaSequentie.getSequenceAsString() + "\n" + this.dnaSequentie.getComplement().getSequenceAsString();
    }

    @Override
    public String toString() {
        return "FASTASequentie{" + "DNA sequentie= " + dnaSequentie + ", DNA header= " + dnaTitel + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dnaSequentie);
        hash = 83 * hash + Objects.hashCode(this.dnaTitel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FASTASequentie other = (FASTASequentie) obj;
        if (!Objects.equals(this.dnaTitel, other.dnaTitel)) {
            return false;
        }
        if (!Objects.equals(this.dnaSequentie, other.dnaSequentie)) {
            return false;
        }
        return true;
    }

}
