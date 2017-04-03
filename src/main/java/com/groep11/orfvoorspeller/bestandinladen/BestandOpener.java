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

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

/**
 * Kleine class dat de statische methodes bevat om een gekozen FASTA bestand in
 * te laden.
 *
 * @author Koen
 */
public class BestandOpener {

    /**
     * Methode om een FASTA bestand uit een gegeven locatie op te halen en de
     * FASTA sequentie te retouneren.
     *
     * @param filePad Locatie van het FASTA bestand.
     * @return De LinkedHashMap waarbij de key de FASTA titel/header als String
     * is en de bijbehorende DNA sequentie de value.
     * @throws IOException Wanneer er een foutief bestand wordt ingeladen (geen
     * FASTA bijvoorbeeld).
     * @throws NullPointerException Wanneer het bestand leeg is.
     */
    public static LinkedHashMap<String, DNASequence> openDNABestand(String filePad) throws IOException, NullPointerException {
        return openDNABestand(new File(filePad));
    }

    /**
     * Methode om een gegeven FASTA bestand in te lezen en hieruit de FASTA
     * sequentie te retouneren.
     *
     * @param fastaFile Het FASTA bestand.
     * @return De LinkedHashMap waarbij de key de FASTA titel/header als String
     * is en de bijbehorende DNA sequentie de value.
     * @throws IOException Wanneer er een foutief bestand wordt ingeladen (geen
     * FASTA bijvoorbeeld).
     * @throws NullPointerException Wanneer het bestand leeg is.
     */
    public static LinkedHashMap<String, DNASequence> openDNABestand(File fastaFile) throws IOException, NullPointerException {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        fastaSequentie = FastaReaderHelper.readFastaDNASequence(fastaFile);

        return fastaSequentie;
    }

}
