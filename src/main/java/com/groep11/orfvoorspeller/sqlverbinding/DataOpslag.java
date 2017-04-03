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

import java.sql.*;
import java.util.Arrays;

/**
 * Generieke class dat de SQL queries maakt om verkregen data op te slaan in een
 * MySQL DB. Ook verzorgt deze class het werkelijk uitvoeren van deze queries en
 * het sluiten van de SQL verbinding wanneer gewenst
 *
 * @author Koen
 */
public class DataOpslag {

    private static final String LINKERHAAK = "(";
    private static final String RECHTERHAAK = ")";
    private static final String QUOTE = "'";
    private static final String KOMMA = ",";
    private static final String EQUALS = "=";
    private static final String SPACE = " ";

    private static final String INSERT = "INSERT INTO ";
    private static final String WAARDES = " VALUES ";
    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String userName;
    private String password;
    private String databaseUrl;
    private Connection connectie;

    private Statement sqlStatement;

    /**
     * Constructor van de DataOpslag, er moet dus standaard een
     * gebruiker,wachtwoord en database worden meegegeven. De constructor laat
     * zelf de verbinding openen en bereid alvast de SQL statement voor.
     *
     * @param gebruiker gebruikersnaam voor de SQL DB.
     * @param wachtwoord wachtwoord van de gebruikersnaam voor de SQL DB.
     * @param database de DB waar geconnecteerd mee moet worden.
     * @throws SQLException als er een fout optreedt tijdens het verbinden met
     * de SQL DB.
     * @throws ClassNotFoundException als de SQL Driver niet gevonden kan
     * worden.
     */
    public DataOpslag(String gebruiker, String wachtwoord, String database) throws SQLException, ClassNotFoundException {
        this.userName = gebruiker;
        this.password = wachtwoord;
        this.databaseUrl = database;
        this.connectie = openVerbinding(userName, password, databaseUrl);
        this.sqlStatement = connectie.createStatement();

    }

    /**
     * Methode om verbinding te openen, is statisch zodat er eventueel
     * onafhankelijk van instanties van deze class verbindingen geopend kunnen
     * worden (voor het werkelijke inserten zal nog steeds een instantie van
     * deze class nodig zijn).
     *
     * @param gebruiker gebruikersnaam voor de SQL DB.
     * @param wachtwoord wachtwoord van de gebruikersnaam voor de SQL DB.
     * @param database de DB waar geconnecteerd mee moet worden.
     * @return de Connection object dat de connectie met de DB representeert.
     * @throws SQLException als er een fout optreedt tijdens het verbinden met
     * de SQL DB.
     * @throws ClassNotFoundException als de SQL Driver niet gevonden kan
     * worden.
     */
    public static Connection openVerbinding(String gebruiker, String wachtwoord, String database) throws ClassNotFoundException, SQLException {
        Connection conn = null;

        Class.forName(DRIVER);
        conn = DriverManager.getConnection(database, gebruiker, wachtwoord);

        return conn;
    }

    /**
     * Generieke methode voor het aanmaken (maar nog niet uitvoeren) van MySQL
     * insert queries.
     *
     * @param tabel de tabel waarin de insert moet plaatsvinden.
     * @param attributen de attributen (op volgorde) waarin de waardes geinsert
     * dienen te worden.
     * @param waardes de waardes (op volgorde van attributen) om te inserten.
     * @return de SQL query als String.
     * @throws OngelijkAantalKolommenException wanneer er niet evenveel
     * attributen als waardes zijn opgegeven.
     */
    public String makeInsertStringQuery(String tabel, String[] attributen, Object[] waardes) throws OngelijkAantalKolommenException {
        if (attributen.length == waardes.length) {
            String waardesAlsQuery;
            String attributenAlsQuery;

            attributenAlsQuery = Arrays.toString(attributen);
            //vervang de [ en ] als uiteindes van de array met ( en ) voor sql query
            attributenAlsQuery = attributenAlsQuery.replace("[", "(").replace("]", ")");

            //vervang de [ en ] als uiteindes van de array met ( en ) en voeg single quotes ' toe rond elke waarde voor sql query
            waardesAlsQuery = Arrays.toString(waardes);
            waardesAlsQuery = waardesAlsQuery.replace("[", "('").replace("]", "')");
            waardesAlsQuery = waardesAlsQuery.replace(",", "','").replace(" ", "");

            String query = INSERT + tabel + SPACE + attributenAlsQuery + WAARDES + waardesAlsQuery;

            return query;

        } else {
            throw new OngelijkAantalKolommenException();

        }
    }

    /**
     * Methode om een gegeven SQL query werkelijk uit te voeren.
     *
     * @param query SQL query om te inserten.
     * @throws SQLException wanneer er een fout optreedt tijdens het inserten.
     */
    public void execute(String query) throws SQLException {
        this.sqlStatement.execute(query, Statement.RETURN_GENERATED_KEYS);

    }

    /**
     * Methode om de (automatisch gegenereerde) primary keys van een tabel te
     * retouneren nadat er een insert heeft plaatsgevonden.
     *
     * @return de primary keys.
     * @throws SQLException wanneer er een fout optreedt met het ophalen van de
     * keys.
     */
    public ResultSet getPrimaryIds() throws SQLException {
        return this.sqlStatement.getGeneratedKeys();
    }

    /**
     * Sluit de verbinding met de DB.
     *
     * @throws SQLException wanneer er een fout optreedt met het sluiten van de
     * verbinding.
     */
    public void close() throws SQLException {
        this.connectie.close();
    }

}
