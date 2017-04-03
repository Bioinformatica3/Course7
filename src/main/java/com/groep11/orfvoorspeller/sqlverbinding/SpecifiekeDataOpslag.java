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
package com.groep11.orfvoorspeller.sqlverbinding;

import com.groep11.orfvoorspeller.orfstonen.ORF;
import com.groep11.orfvoorspeller.bestandinladen.FASTASequentie;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * De (niet generieke) class om DNA en ORFs op te slaan in de SQL database, het
 * aanmaken en uitvoeren van de werkelijke queries wordt verzorgd door
 * DataOpslag maar deze class geeft de specifieke tabellen, attributen en
 * waardes door die nodig zijn om de data op te slaan.
 *
 * @author Koen
 */
public class SpecifiekeDataOpslag {

    private static final String GEBRUIKER = "root";
    private static final String WACHTWOORD = "blaat1234";
    private static final String DATABASE = "jdbc:mysql://localhost:3306/mydb";
    private static DataOpslag saver;

    /**
     * Constructor dat een nieuwe instantie van DataOpslag aanmaakt met de voor
     * dit project specifieke gebruikersnaam, wachtwoord en database.
     *
     * @throws SQLException als er een fout optreedt tijdens het verbinden met
     * de SQL DB.
     * @throws ClassNotFoundException als de SQL driver niet gevonden kan
     * worden.
     */
    public SpecifiekeDataOpslag() throws SQLException, ClassNotFoundException {
        saver = new DataOpslag(GEBRUIKER, WACHTWOORD, DATABASE);
    }

    /**
     * Methode om de volledige ingeladen FASTA sequentie op te slaan in een SQL
     * DB. Van de FASTA data wordt de titel (header) en sequentie opgeslagen.
     *
     * @param fastaSequentie de FASTA sequentie om op te slaan.
     * @throws SQLException wanneer er een fout optreedt met het inserten van de
     * FASTA data.
     * @throws ClassNotFoundException wanner de SQL niet gevonden kan worden.
     * @throws NullPointerException als de fastaSequentie leeg is.
     * @throws ArrayIndexOutOfBoundsException
     * @throws OngelijkAantalKolommenException als er niet evenveel attributen
     * als waardes zijn.
     */
    public void saveDNA(FASTASequentie fastaSequentie) throws SQLException, ClassNotFoundException, NullPointerException, ArrayIndexOutOfBoundsException, OngelijkAantalKolommenException {
        String header;
        String sequentie;
        String saveQuery;

        String[] fastaData = new String[2];
        String[] fastaAttributen = new String[2];

        header = fastaSequentie.getTitel();
        sequentie = fastaSequentie.getSequentieString();

        //vervang eventuele kommas met punten om problemen met SQL tegen te gaan
        fastaData[0] = header.replace(",", ".");
        fastaData[1] = sequentie.replace(",", ".");

        fastaAttributen[0] = "titel";
        fastaAttributen[1] = "sequentie";

        saveQuery = saver.makeInsertStringQuery("dna", fastaAttributen, fastaData);

        saver.execute(saveQuery);

    }

    /**
     * Methode om individuele ORFs op te slaan in een SQL DB. Van een ORF wordt
     * de startpositie, eindpositie en strand opgeslagen.
     *
     * @param orfs Lijst met alle ORFs om op te slaan.
     * @throws ArrayIndexOutOfBoundsException wanneer de ORF lijst leeg is.
     * @throws SQLException als er een fout optreedt tijdens het inserten van de
     * ORF data.
     * @throws OngelijkAantalKolommenException als er niet evenveel attributen
     * als waardes zijn.
     */
    public void saveORFs(ArrayList<ORF> orfs) throws ArrayIndexOutOfBoundsException, SQLException, OngelijkAantalKolommenException {
        int startPositie;
        int eindPositie;
        char strand;
        String saveQuery;

        String[] orfAttributen = new String[3];
        Object[] orfWaardes = new Object[3];

        orfAttributen[0] = "start";
        orfAttributen[1] = "eind";
        orfAttributen[2] = "strand";

        for (ORF orf : orfs) {
            startPositie = orf.getStartPos();
            eindPositie = orf.getEindPos();
            strand = orf.getStrand();

            orfWaardes[0] = startPositie;
            orfWaardes[1] = eindPositie;
            orfWaardes[2] = strand;

            saveQuery = saver.makeInsertStringQuery("orfs", orfAttributen, orfWaardes);

            saver.execute(saveQuery);

        }

    }

}
