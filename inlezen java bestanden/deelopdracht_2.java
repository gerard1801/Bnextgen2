import com.sun.tools.javac.util.Pair;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by gerardvanderwel on 20-09-18.
 */
public class deelopdracht_2 {
    public int cut_off_score;
    private int phred_int;
    private int dna_int;

    public deelopdracht_2() {
        //De cut_off_score wordt tussen de 0 - 40 gebruikt.
        //Er is gekozen om een cut_off_score van 20 te houden om niet te veel/weinig false positives te krijgen.
        this.cut_off_score = 20;
    }

    public int getCut_off_score() {
        return cut_off_score;
    }

    public void setCut_off_score(int cut_off_score) {
        this.cut_off_score = cut_off_score;
    }

    public int getPhred_int() {
        return phred_int;
    }

    public void setPhred_int(int phred_int) {
        this.phred_int = phred_int;
    }

    public int getDna_int() {
        return dna_int;
    }

    public void setDna_int(int dna_int) {
        this.dna_int = dna_int;
    }


    public static void main(String[] args) {
        try {
            if (args.length == 1 && args[0].matches("-h")) {
                System.out.println("\nDit is de gebruikers informatie van Bnextgen_2.jar.\n\n" +
                        "---FUNCTIE---\nIn dit script worden de reads van fastQ bestanden getrimmed." +
                        "\n\n---GEBRUIK---\n" +
                        "Om het script te kunnen runnen zijn er een aantal variabelen nodig.\n" +
                        "De layout van het aanroepen is als volgt: \n\njava -jar {file} {input1} {input2} {output1} {output2}" +
                        "\n{file} : Het pad naar het script.\n{input1} : Het pad naar het eerste fastq bestand." +
                        "\n{input2}: Het pad naar het tweede fastq bestand." + "\n{output1}: De naam van het output-bestand van de getrimde reads uit {input1}" +
                        "\n{output2}: De naam van het output-bestand van de getrimde reads uit {input2}" +
                        "\n\n---AANROEPEN---\n" +
                        "Een voorbeeld van het aanroepen van het script is hier weergeven:\n\n" +
                        "java -jar /home/s1095337/Bnextgen_2.jar /home/bngp/reads/bngsa_nietinfected_1.fastq" +
                        " /home/bngp/reads/bngsa_nietinfected_2.fastq /home/s1095337/QCvoorTR1.txt " +
                        "/home/s1095337/QCvoorTR2.txt\n");
            } else {
                String fileName1 = args[0];
                String fileName2 = args[1];
                String fileName3 = args[2];
                String fileName4 = args[3];
                deelopdracht_2 opd2 = new deelopdracht_2();
                opd2.read_write(fileName1, fileName2, fileName3, fileName4);
            }
        } catch (Exception e) {
            System.out.println("\nDe command is niet geldig!\nVoor meer informatie: java -jar /home/s1095337/Bnextgen_2.jar -h\n");
        }
    }

    public void read_write(String filename1, String filename2, String fileName3, String fileName4) {
        String header1;
        String header2;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename1));
             Writer writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName3), "utf-8"));
             Writer writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName4), "utf-8"));
             BufferedReader bufferedReader1 = new BufferedReader(new FileReader(filename2));
        ) {
            while ((header1 = bufferedReader.readLine()) != null) {
                StringBuilder dna_streng = new StringBuilder(bufferedReader.readLine());
                String plus1 = bufferedReader.readLine();
                StringBuilder ascii = new StringBuilder(bufferedReader.readLine());
                ArrayList<StringBuilder> trim1 = trimming(dna_streng, ascii);
                ArrayList<StringBuilder> trim2 = trimming(trim1.get(0).reverse(), trim1.get(1).reverse());

                //deel 2
                header2 = bufferedReader1.readLine();
                StringBuilder dna_streng2 = new StringBuilder(bufferedReader1.readLine());
                String plus2 = bufferedReader1.readLine();
                StringBuilder ascii2 = new StringBuilder(bufferedReader1.readLine());
                ArrayList<StringBuilder> trim3 = trimming(dna_streng2, ascii2);
                ArrayList<StringBuilder> trim4 = trimming(trim3.get(0).reverse(), trim3.get(1).reverse());

                //De reads moeten niet te kort zijn, omdat ze dan op meerdere plaaten aligned kunnen worden.
                //Er is daarom gekozen om de readlengte langer te houden dan 15, om de alignment wat betrouwbaarder te maken.
                if (trim2.get(0).toString().length() > 15 && trim4.get(0).toString().length() > 15){
                    writer1.write(header1.toString() + "\n" + trim2.get(0).reverse() + "\n" + plus1 + "\n" + trim2.get(1).reverse() + "\n");
                    writer2.write(header2.toString() + "\n" + trim4.get(0).reverse() + "\n" + plus2 + "\n" + trim4.get(1).reverse() + "\n");
                } else {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StringBuilder> trimming(StringBuilder dna_streng, StringBuilder ascii) {
        int total = 0;
        ArrayList <StringBuilder> lijst = new ArrayList<>();
        for (int i = 0; i < dna_streng.length(); i++) {
            int phred = (int) ascii.charAt(i) - (33 + this.cut_off_score);
            total += phred;
            if (total < 0) {
                dna_streng.deleteCharAt(0);
                ascii.deleteCharAt(0);
            } else {
                break;
            }
        }
        lijst.add(dna_streng);
        lijst.add(ascii);
        return lijst;
    }
}