/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public DataOpslag(String gebruiker, String wachtwoord, String database) throws SQLException, ClassNotFoundException {
        this.userName = gebruiker;
        this.password = wachtwoord;
        this.databaseUrl = database;
        this.connectie = openVerbinding(userName, password, databaseUrl);
        this.sqlStatement = connectie.createStatement();

    }

    public static Connection openVerbinding(String gebruiker, String wachtwoord, String database) throws ClassNotFoundException, SQLException {
        Connection conn = null;

        Class.forName(DRIVER);
        conn = DriverManager.getConnection(database, gebruiker, wachtwoord);

        return conn;
    }

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

    public void execute(String query) throws SQLException {
        this.sqlStatement.execute(query, Statement.RETURN_GENERATED_KEYS);

    }

    public ResultSet getPrimaryIds() throws SQLException {
        return this.sqlStatement.getGeneratedKeys();
    }

    public void close() throws SQLException {
        this.connectie.close();
    }

}
