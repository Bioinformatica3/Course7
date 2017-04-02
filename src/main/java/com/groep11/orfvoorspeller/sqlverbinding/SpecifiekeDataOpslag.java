/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groep11.orfvoorspeller.sqlverbinding;

import com.groep11.orfvoorspeller.sqlverbinding.OngelijkAantalKolommenException;
import com.groep11.orfvoorspeller.sqlverbinding.DataOpslag;
import com.groep11.orfvoorspeller.orfstonen.ORF;
import com.groep11.orfvoorspeller.bestandinladen.FASTASequentie;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Koen
 */
public class SpecifiekeDataOpslag {

    private static final String GEBRUIKER = "root";
    private static final String WACHTWOORD = "blaat1234";
    private static final String DATABASE = "jdbc:mysql://localhost:3306/mydb";
    private static DataOpslag saver;

    public SpecifiekeDataOpslag() throws SQLException, ClassNotFoundException {
        saver = new DataOpslag(GEBRUIKER, WACHTWOORD, DATABASE);
    }

    //nog checken of waarde al in tabel staat
    public void saveDNA(FASTASequentie dnaSequentie) throws SQLException, ClassNotFoundException, NullPointerException, ArrayIndexOutOfBoundsException, OngelijkAantalKolommenException {
        String header;
        String sequentie;
        String saveQuery;

        String[] dnaData = new String[2];
        String[] dnaAttributen = new String[2];

        header = dnaSequentie.getTitel();
        sequentie = dnaSequentie.getSequentieString();

        //vervang eventuele kommas met punten om problemen met SQL tegen te gaan
        dnaData[0] = header.replace(",", ".");
        dnaData[1] = sequentie.replace(",", ".");

        dnaAttributen[0] = "titel";
        dnaAttributen[1] = "sequentie";

        saveQuery = saver.makeInsertStringQuery("dna", dnaAttributen, dnaData);

        saver.execute(saveQuery);

        //haal de id van de net geinserte sequentie op en zet deze in de tussentabel
        
        

    }

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
           
            saveQuery = saver.makeInsertStringQuery("orfs",orfAttributen,orfWaardes);
            
            saver.execute(saveQuery);

        }
        

    }

}