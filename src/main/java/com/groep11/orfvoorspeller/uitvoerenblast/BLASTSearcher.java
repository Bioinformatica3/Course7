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
package com.groep11.orfvoorspeller.uitvoerenblast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.biojava.nbio.core.search.io.Hit;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;

/**
 * NIET FUNCTIONEEL (afkomstig uit BioJava Cookbook)
 *
 * @author Koen
 */
public class BLASTSearcher {

    private ArrayList<BLASTHit> blastLijst = new ArrayList<>();

    private static final String BLAST_OUTPUT_FILE = "blastOutput.xml";    // file to save blast results to
    private static final String SEQUENCE = "MKWVTFISLLFLFSSAYSRGVFRRDAHKSEVAHRFKDLGEENFKALVLIAFAQYLQQCP";     // Blast query sequence

    public static void BLAST() {
        NCBIQBlastService service = new NCBIQBlastService();

        // set alignment options
        NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
        props.setBlastProgram(BlastProgramEnum.blastp);
        props.setBlastDatabase("swissprot");
        props.setAlignmentOption(ENTREZ_QUERY, "\"serum albumin\"[Protein name] AND mammals[Organism]");

        // set output options
        NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();
        // in this example we use default values set by constructor (XML format, pairwise alignment, 100 descriptions and alignments) 

        // Example of two possible ways of setting output options
// outputProps.setAlignmentNumber(200); // outputProps.setOutputOption(BlastOutputParameterEnum.ALIGNMENTS, “200”);
        String rid = null;          // blast request ID
        FileWriter writer = null;
        BufferedReader reader = null;
        try {
            // send blast request and save request id
            rid = service.sendAlignmentRequest(SEQUENCE, props);

            // wait until results become available. Alternatively, one can do other computations/send other alignment requests
            while (!service.isReady(rid)) {
                System.out.println("Waiting for results. Sleeping for 5 seconds");
                Thread.sleep(5000);
            }

            // read results when they are ready
            InputStream in = service.getAlignmentResults(rid, outputProps);
            reader = new BufferedReader(new InputStreamReader(in));

            // write blast output to specified file
            File f = new File(BLAST_OUTPUT_FILE);
            System.out.println("Saving query results in file " + f.getAbsolutePath());
            writer = new FileWriter(f);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            // clean up
            IOUtils.close(writer);
            IOUtils.close(reader);

            // delete given alignment results from blast server (optional operation)
            service.sendDeleteRequest(rid);
        }
    }

}
