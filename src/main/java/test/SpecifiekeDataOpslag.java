/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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
    
   
    
    public DataOpslag buildDataOpslag() throws SQLException, ClassNotFoundException{
        DataOpslag saver = new DataOpslag(GEBRUIKER,WACHTWOORD,DATABASE);
        return saver;
    }
    //nog checken of waarde al in tabel staat
    public void saveDNA(FASTASequentie dnaSequentie) throws SQLException, ClassNotFoundException{
        String header;
        String sequentie;
        String saveQuery;
        
        String[] dnaData = new String[2];
        String[] dnaAttributen = new String[2];
        
        header = dnaSequentie.getTitel();
        sequentie = dnaSequentie.getSequentieString();
        
        //vervang eventuele kommas met spaties om problemen in SQL tegen te gaan
        dnaData[0] = header.replace(",", " "); 
        dnaData[1] = sequentie.replace(",", " ");
        
        dnaAttributen[0] = "titel";
        dnaAttributen[1] = "sequentie";
        
        DataOpslag opslag = buildDataOpslag();
        saveQuery = opslag.makeInsertStringQuery("dna", dnaAttributen, dnaData);
        
        opslag.execute(saveQuery);
        
        
        
    
}
    public void saveORFs(ArrayList<ORF> orfs){
        
    }
            
            
    
}
