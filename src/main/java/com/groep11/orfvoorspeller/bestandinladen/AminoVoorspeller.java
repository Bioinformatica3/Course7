/**
 * Deze applicatie biedt gebruikers de mogelijkheid om een DNA sequentie (in FASTA formaat) in te laden
 * en hierin aanwezige ORFs (gedefineerd als een DNA sequentie dat in frame begint met ATG en eindigt met een stop codon)
 * te vinden,visualiseren en eventueel op te slaan in een MySQL database.
 * Vereist BioJava 4.2.7 en mysql-connector 6.0.6.
 *
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
package com.groep11.orfvoorspeller.bestandinladen;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.transcription.Frame;

/**
 * Deze class is verantwoordelijk voor het omzetten een gegeven DNA sequentie
 * naar een aminozuursequentie, hierbij kan de aminozuursequentie voor een
 * specifiek frame worden bepaald of voor alle frames tegelijk.
 *
 * @author Koen
 */
public class AminoVoorspeller {

    private DNASequence dnaSequentie;
    private String dnaTitel;

    /**
     * Constructor voor de aminovoorspeller, de meegegeven fasta sequentie wordt
     * weer opgesplitst in zijn sequentie en fasta titel.
     *
     * @param fastaSequentie FASTA sequentie (dat DNA bevat) waarvan de
     * bijbehorende aminozuursequenties bepaald moeten worden.
     */
    public AminoVoorspeller(FASTASequentie fastaSequentie) {
        this.dnaSequentie = fastaSequentie.getSequentie();
        this.dnaTitel = fastaSequentie.getTitel();

    }

    /**
     * Retouneert de DNA sequentie behorende bij de FASTA sequentie.
     *
     * @return de DNA nucleotiden sequentie
     */
    public DNASequence getDnaSequentie() {
        return dnaSequentie;
    }

    /**
     * Pas de DNA sequentie aan (let op dat de titel waarschijnlijk ook
     * aangepast moet worden).
     *
     * @param newSequentie de nieuwe DNA sequentie
     */
    public void setDnaSequentie(DNASequence newSequentie) {
        this.dnaSequentie = newSequentie;
    }

    /**
     * Retouneert de titel (header) van de FASTA sequentie.
     *
     * @return de titel (header) van de sequentie.
     */
    public String getDnaTitel() {
        return dnaTitel;
    }

    /**
     * Pas de FASTA titel (header) aan (let op dat de DNA sequentie
     * waarschijnlijk ook aangepast moet worden)
     *
     * @param newTitel de nieuwe gewenste FASTA titel.
     */
    public void setDnaTitel(String newTitel) {
        this.dnaTitel = newTitel;
    }

    /**
     * Roept de bepaalAminos methode aan voor ALLE 6 reading frames aanwezig in
     * de DNA sequentie en voegt de uitkomst voor iedere reading frame toe aan
     * een LinkedHashMap.
     *
     * @return een LinkedHashMap met iedere reading frame (in volgorde) als key
     * met de bijbehorende vertaalde aminozuursequentie opgeslagen als
     * ProteinSequence als value.
     */
    public LinkedHashMap<Frame, ProteinSequence> bepaalPerFrameAminos() {
        LinkedHashMap<Frame, ProteinSequence> aminos = new LinkedHashMap<Frame, ProteinSequence>(6);
        ProteinSequence aminoSequentie;

        for (Frame readingFrame : Frame.getAllFrames()) {
            aminoSequentie = bepaalAminos(readingFrame);
            aminos.put(readingFrame, aminoSequentie);
        }
        return aminos;
    }

    /**
     * Bepaalt voor een gegeven reading frame wat de bijbehorende aminozuur
     * sequentie zou zijn voor de DNA sequentie opgegeven bij het instantieren
     * van de AminoVoorspeller. Hiervoor wordt de DNA sequentie eerst vertaald
     * naar een frame shifted RNA sequentie en dan naar de aminozuursequentie.
     *
     * @param readingFrame reading frame waar vanaf de aminozuursequentie
     * bepaald moet worden.
     * @return de vertaalde aminozuursequentie voor het gegeven reading frame.
     */
    public ProteinSequence bepaalAminos(Frame readingFrame) {
        RNASequence rnaSequentie;
        ProteinSequence aminoSequentie;

        rnaSequentie = dnaSequentie.getRNASequence(readingFrame);
        aminoSequentie = rnaSequentie.getProteinSequence();
        return aminoSequentie;
    }

    /**
     * Identiek aan bepaalPerFrameAminos maar retouneert ditmaal een
     * LinkedHashMap waarbij de values de aminozuursequenties als String zijn in
     * plaats van ProteinSequence.
     *
     * @return LinkedHashMap met reading frame als key en aminozuursequentie als
     * String object als value.
     */
    public LinkedHashMap<Frame, String> bepaaPerFrameAminosString() {
        LinkedHashMap<Frame, String> aminos = new LinkedHashMap<Frame, String>(6);
        ProteinSequence aminoSequentie;
        String aminoSequentieString;

        for (Frame readingFrame : Frame.getAllFrames()) {
            aminoSequentie = bepaalAminos(readingFrame);
            aminoSequentieString = aminoSequentie.getSequenceAsString();
            aminos.put(readingFrame, aminoSequentieString);
        }
        return aminos;
    }

    /**
     * Roept de bepaalPerFrameAminosString methode aan en zet alle
     * aminozuursequenties achter elkaar tot 1 lange String.
     *
     * @return alle aminozuursequenties als 1 samengevoegde String.
     */
    public String getAllAminoSequentiesAlsString() {
        HashMap<Frame, String> aminoSequenties;
        Frame frame;
        String aminoSequentieString;

        StringBuilder aaBuilder = new StringBuilder();
        StringBuilder aaComplementBuilder = new StringBuilder();

        aminoSequenties = bepaaPerFrameAminosString();

        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {

            frame = frameSequentie.getKey();
            aminoSequentieString = frameSequentie.getValue();

            if (frame.toString().startsWith("REVERSED")) {
                if (aaComplementBuilder.length() != 0) {
                    aaComplementBuilder.append("\n");

                }
                aaComplementBuilder.append(aminoSequentieString);

            } else {
                if (aaBuilder.length() != 0) {
                    aaBuilder.append("\n");
                }
                aaBuilder.append(aminoSequentieString);
            }
        }
        aaBuilder.append(aaComplementBuilder.reverse());

        return aaBuilder.toString();

    }

    /**
     * Identiek aan getAllAminoSequentiesAlsString() maar ditmaal alleen voor de
     * forward (positieve) reading frames.
     *
     * @return alle forward (positieve) aminozuursequenties samengevoegd tot 1
     * String.
     */
    public String getForwardAminoSequentiesAlsString() {

        HashMap<Frame, String> aminoSequenties;
        Frame frame;
        String aminoSequentieString;
        String aminoSequentieInFrame;
        StringBuilder aaBuilder = new StringBuilder();

        aminoSequenties = bepaaPerFrameAminosString();

        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {

            frame = frameSequentie.getKey();
            aminoSequentieString = frameSequentie.getValue();

            aminoSequentieInFrame = bepaalFramesOffset(frame, aminoSequentieString);

            if (!frame.toString().startsWith("REVERSED")) {
                if (aaBuilder.length() != 0) {
                    aaBuilder.append("\n");
                }
                aaBuilder.append(aminoSequentieInFrame);

            }
        }

        return aaBuilder.toString();

    }

    /**
     * Identiek aan getAllAminoSequentiesAlsString() maar ditmaal alleen voor de
     * reverse (negatieve) reading frames.
     *
     * @return alle reverse (negatieve) aminozuursequenties samengevoegd tot 1
     * String.
     */
    public String getReverseAminoSequentiesAlsString() {

        HashMap<Frame, String> aminoSequenties;
        Frame frame;
        String aminoSequentieString;
        String aminoSequentieInFrame;

        StringBuilder aaComplementBuilder = new StringBuilder();

        aminoSequenties = bepaaPerFrameAminosString();

        for (Map.Entry<Frame, String> frameSequentie : aminoSequenties.entrySet()) {

            frame = frameSequentie.getKey();
            aminoSequentieString = frameSequentie.getValue();

            aminoSequentieInFrame = bepaalFramesOffset(frame, aminoSequentieString);

            if (frame.toString().startsWith("REVERSED")) {
                if (aaComplementBuilder.length() == 0) {
                    aaComplementBuilder.append(aminoSequentieInFrame);

                } else {
                    aaComplementBuilder.insert(0, aminoSequentieInFrame + "\n");
                }

            }
        }

        return aaComplementBuilder.toString();

    }

    /**
     * Deze methode past een gegeven aminozuursequentie (als String) voor een
     * gegeven reading frame zodanig aan dat de posities van de aminozuren
     * overeenkomen met de posities van de bijbehorende DNA codonen. Aan ieder
     * aminozuur worden dus twee whitespaces toegevoegd en afhankelijk van de
     * reading frame wordt de gehele sequentie ook nog verschoven.
     *
     * Is statisch zodat deze methode ook bruikzaam is voor aminozuursequenties
     * verkregen buiten deze class.
     *
     * @param readingFrame reading frame van de aminozuursequentie.
     * @param aaSequentie aminozuursequentie dat aangepast dient te worden.
     * @return de aminozuursequentie als String met de nodige aanpassingen om te
     * matchen met de DNA sequentie.
     */
    public static String bepaalFramesOffset(Frame readingFrame, String aaSequentie) {
        StringBuilder aaBuilder = new StringBuilder();
        aaSequentie = aaSequentie.replace("", "  ").trim();
        switch (readingFrame) {

            case ONE:
                aaBuilder.append(aaSequentie);
                break;

            case TWO:
                aaBuilder.append(" ").append(aaSequentie);
                break;

            case THREE:
                aaBuilder.append("  ").append(aaSequentie);
                break;

            case REVERSED_ONE:
                aaBuilder.append(aaSequentie);
                aaBuilder.reverse();
                break;

            case REVERSED_TWO:
                aaBuilder.append(aaSequentie).append(" ");
                aaBuilder.reverse();
                break;

            case REVERSED_THREE:
                aaBuilder.append(aaSequentie).append("  ");
                aaBuilder.reverse();
                break;

        }
        return aaBuilder.toString();
    }

}
