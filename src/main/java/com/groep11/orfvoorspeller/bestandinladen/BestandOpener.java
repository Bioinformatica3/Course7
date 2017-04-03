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
 *
 * @author Koen
 */
public class BestandOpener {
//nog mogelijkheid om bestand lokaal op te slaan?

//mogelijk statisch?

    /**
     *
     * @param filePad
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    public LinkedHashMap<String, DNASequence> openDNABestand(String filePad) throws IOException, NullPointerException {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        fastaSequentie = FastaReaderHelper.readFastaDNASequence(new File(filePad));

        return fastaSequentie;
    }

    /**
     *
     * @param fastaFile
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    public LinkedHashMap<String, DNASequence> openDNABestand(File fastaFile) throws IOException, NullPointerException {
        LinkedHashMap<String, DNASequence> fastaSequentie = new LinkedHashMap<String, DNASequence>();

        fastaSequentie = FastaReaderHelper.readFastaDNASequence(fastaFile);

        return fastaSequentie;
    }

}
