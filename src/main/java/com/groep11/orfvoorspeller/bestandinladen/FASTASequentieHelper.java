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

import java.util.LinkedHashMap;
import java.util.Map;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 * Class enkel om FASTASequentie instanties aan te maken.
 *
 * @author Koen
 */
public class FASTASequentieHelper {

    /**
     * Methode dat de LinkedHashMap afkomstig uit een ingelezen FASTA bestand
     * omzet tot FASTASequentie instanties.
     *
     * @param fastaSequentie De map met fasta sequenties waarbij de key de
     * titel/header is en de value de werkelijke DNA sequentie.
     * @return een nieuwe instantie van FASTASequentie gevuld met de data uit de
     * fasta sequentie.
     * @throws OngeldigBestandException wanneer het opgegeven LinkedHashMap leeg
     * is.
     */
    public static FASTASequentie saveFASTASequentie(LinkedHashMap<String, DNASequence> fastaSequentie) throws OngeldigBestandException {
        if (fastaSequentie.isEmpty()) {
            throw new OngeldigBestandException();
        } else {
            Map.Entry<String, DNASequence> fastaEntry;
            fastaEntry = fastaSequentie.entrySet().iterator().next();
            return new FASTASequentie(fastaEntry); //voor nu wordt alleen de eerste fasta sequentie opgeslagen

        }
    }
}
