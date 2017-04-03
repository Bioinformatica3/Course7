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
     *
     * @param gebruiker
     * @param wachtwoord
     * @param database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public DataOpslag(String gebruiker, String wachtwoord, String database) throws SQLException, ClassNotFoundException {
        this.userName = gebruiker;
        this.password = wachtwoord;
        this.databaseUrl = database;
        this.connectie = openVerbinding(userName, password, databaseUrl);
        this.sqlStatement = connectie.createStatement();

    }

    /**
     *
     * @param gebruiker
     * @param wachtwoord
     * @param database
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection openVerbinding(String gebruiker, String wachtwoord, String database) throws ClassNotFoundException, SQLException {
        Connection conn = null;

        Class.forName(DRIVER);
        conn = DriverManager.getConnection(database, gebruiker, wachtwoord);

        return conn;
    }

    /**
     *
     * @param tabel
     * @param attributen
     * @param waardes
     * @return
     * @throws SQLException
     * @throws OngelijkAantalKolommenException
     */
    public String makeInsertStringQuery(String tabel, String[] attributen, Object[] waardes) throws SQLException, OngelijkAantalKolommenException {
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

            System.out.println(query);
            return query;

        } else {
            throw new OngelijkAantalKolommenException();

        }
    }

    /**
     *
     * @param query
     * @throws SQLException
     */
    public void execute(String query) throws SQLException {
        this.sqlStatement.execute(query, Statement.RETURN_GENERATED_KEYS);

    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet getPrimaryIds() throws SQLException {
        return this.sqlStatement.getGeneratedKeys();
    }

    /**
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.connectie.close();
    }

}
