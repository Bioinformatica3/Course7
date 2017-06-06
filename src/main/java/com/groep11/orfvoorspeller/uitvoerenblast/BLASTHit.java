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

/**
 * NOT FUNCTIONAL
 *
 * @author Koen
 */
public class BLASTHit {

    private String accessieCode;
    private String eiwitNaam;
    private String blastSequentie;
    private int score;
    private double identity;
    private double positive;
    private double e_value;

    public BLASTHit(String eiwitAccessieCode, String eiwitNaam, String blastSequentie, int score, double identity, double positive, double e_value) {
        this.accessieCode = eiwitAccessieCode;
        this.eiwitNaam = eiwitNaam;
        this.blastSequentie = blastSequentie;
        this.score = score;
        this.identity = identity;
        this.positive = positive;
        this.e_value = e_value;
    }

    public String getAccessieCode() {
        return accessieCode;
    }

    public void setAccessieCode(String eiwitAccessieCode) {
        this.accessieCode = eiwitAccessieCode;
    }

    public String getEiwitNaam() {
        return eiwitNaam;
    }

    public void setEiwitNaam(String eiwitNaam) {
        this.eiwitNaam = eiwitNaam;
    }

    public String getBlastSequentie() {
        return blastSequentie;
    }

    public void setBlastSequentie(String blastSequentie) {
        this.blastSequentie = blastSequentie;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getIdentity() {
        return identity;
    }

    public void setIdentity(double identity) {
        this.identity = identity;
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public double getE_value() {
        return e_value;
    }

    public void setE_value(double e_value) {
        this.e_value = e_value;
    }

}
