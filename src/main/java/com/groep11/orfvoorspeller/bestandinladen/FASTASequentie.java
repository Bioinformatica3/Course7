/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groep11.orfvoorspeller.bestandinladen;

import java.util.Map.Entry;
import java.util.Objects;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 *
 * @author Koen
 */
public class FASTASequentie {

    private DNASequence dnaSequentie;
    private String dnaTitel;

    public FASTASequentie(String inputTitel, DNASequence inputSequentie) {
        this.dnaTitel = inputTitel;
        this.dnaSequentie = inputSequentie;

    }

    public FASTASequentie(Entry<String, DNASequence> inputSequentie) {
        this.dnaTitel = inputSequentie.getKey();
        this.dnaSequentie = inputSequentie.getValue();

    }

    public void setTitel(String newTitel) {
        this.dnaTitel = newTitel;
    }

    public String getTitel() {
        return this.dnaTitel;
    }

    public void setSequentie(DNASequence newSequentie) {
        this.dnaSequentie = newSequentie;
    }

    public DNASequence getSequentie() {
        return this.dnaSequentie;
    }

    public String getSequentieString() {
        return this.dnaSequentie.getSequenceAsString();
    }

    public String getSequentieMetComplementair() {
        return this.dnaSequentie.getSequenceAsString() + "\n" + this.dnaSequentie.getComplement().getSequenceAsString();
    }

    @Override
    public String toString() {
        return "FASTASequentie{" + "DNA sequentie= " + dnaSequentie + ", DNA header= " + dnaTitel + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dnaSequentie);
        hash = 83 * hash + Objects.hashCode(this.dnaTitel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FASTASequentie other = (FASTASequentie) obj;
        if (!Objects.equals(this.dnaTitel, other.dnaTitel)) {
            return false;
        }
        if (!Objects.equals(this.dnaSequentie, other.dnaSequentie)) {
            return false;
        }
        return true;
    }

}
