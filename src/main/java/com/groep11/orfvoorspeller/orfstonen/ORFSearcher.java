/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groep11.orfvoorspeller.orfstonen;

import com.groep11.orfvoorspeller.orfstonen.ORF;
import com.groep11.orfvoorspeller.bestandinladen.FASTASequentie;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 *
 * @author Koen
 */
public class ORFSearcher {

    private static final String STARTSTOPCODON = "ATG([ATGC]{3})+?(TAA|TGA|TAG)";
    private static final Pattern ORFPATROON = Pattern.compile(STARTSTOPCODON);
    private ArrayList<ORF> orfLijst = new ArrayList<>();

    public void vindORFs(FASTASequentie dnaSequentie) throws CompoundNotFoundException {
        Matcher orfMatches;
        String[] dnaStrings = new String[2];
        String orfTitel = "";
        String orfString = "ORF_";
        char strand = ' ';

        int orfId = 0;
        int orfBegin;
        int orfEind;

        int strandCounter = 0;

        dnaStrings[0] = dnaSequentie.getSequentie().getSequenceAsString();
        dnaStrings[1] = dnaSequentie.getSequentie().getReverseComplement().getSequenceAsString();

        for (String dnaString : dnaStrings) {
            orfMatches = ORFPATROON.matcher(dnaString);
            while (orfMatches.find()) {

                orfId++;
                orfTitel = orfString + Integer.toString(orfId);
                
            
                orfBegin = orfMatches.start();
                orfEind = orfMatches.end();

                switch (strandCounter) {
                    case (0):

                        strand = '+';
                        break;
                    case (1):
                        strand = '-';
                        //de zoekopdracht vindt plaats in de omgedraaide reverse sequentie, 
                        //voor correcte orf posities moeten deze weer terug gedraaid worden
                        orfBegin = dnaString.length() - orfMatches.start();
                        orfEind = dnaString.length() - orfMatches.end();

                        break;
                }

                DNASequence orfSequentie = new DNASequence(orfMatches.group());
                ORF foundORF = new ORF(orfTitel, orfSequentie, orfBegin, orfEind, strand);

                orfLijst.add(foundORF);
            }
            strandCounter++;

        }

    }

    public ArrayList<ORF> getORFLijst() {
        return this.orfLijst;
    }

    public void setORFLijst(ArrayList<ORF> newORFs) {
        this.orfLijst = newORFs;
    }

    @Override
    public String toString() {
        return "ORFSearcher{" + "orfLijst= " + orfLijst + '}';
    }

}
