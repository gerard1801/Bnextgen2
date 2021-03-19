import java.io.*;

/**
 * Created by gerardvanderwel on 05/11/2019.
 */
public class deelopdracht_6 {
    private int deletie;
    private int insertie;
    private int AT;
    private int AG;
    private int AC;
    private int TA;
    private int TG;
    private int TC;
    private int GA;
    private int GT;
    private int GC;
    private int CG;
    private int CT;
    private int CA;

    public deelopdracht_6() {
        this.deletie = deletie;
        this.insertie = insertie;
        this.AT = AT;
        this.AG = AG;
        this.AC = AC;
        this.TA = TA;
        this.TG = TG;
        this.TC = TC;
        this.GA = GA;
        this.GT = GT;
        this.GC = GC;
        this.CG = CG;
        this.CT = CT;
        this.CA = CA;
    }
    public static void main(String[] args) {
        deelopdracht_6 opd6 = new deelopdracht_6();
        try {
            if (args.length == 1 && args[0].matches("-h")) {
                System.out.println("\nDit is de gebruikers informatie van Bnextgen_6.jar.\n\n" +
                        "---FUNCTIE---\nIn dit script wordt er een berekening gemaakt van het aantal deleties, inserties en mutaties." +
                        "\n\n---GEBRUIK---\n" +
                        "Om het script te kunnen runnen zijn er een aantal variabelen nodig.\n" +
                        "De layout van het aanroepen is als volgt: \n\njava -jar {file} {input1} {output}" +
                        "\n{file} : Het pad naar het script.\n{input} : Het pad naar het vcf bestand." +
                        "\n{output}: Het pad van het output bestand." +
                        "\n\n---AANROEPEN---\n" +
                        "Een voorbeeld van het aanroepen van het script is hier weergeven:\n\n" +
                        "java -jar /home/s1095337/Bnextgen_6.jar /home/s1095337/mapping/variantcall.vcf" +
                        " /home/s1095337/deelopdr6.txt\n");
            } else {
                String file1 = args[0];
                String file2 = args[1];
                opd6.read(file1);
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "utf-8"))) {
                    writer.write("Aantal deleties: " + opd6.deletie);
                    writer.write("\nAantal inserties: " + opd6.insertie);
                    writer.write("\nVerhouding deleties/inserties: " + (float) opd6.deletie / opd6.insertie);
                    writer.write("\nAantall A -> T: " + opd6.AT);
                    writer.write("\nAantall A -> G: " + opd6.AG);
                    writer.write("\nAantall A -> C: " + opd6.AC);
                    writer.write("\nAantall T -> A: " + opd6.TA);
                    writer.write("\nAantall T -> G: " + opd6.TG);
                    writer.write("\nAantall T -> C: " + opd6.TC);
                    writer.write("\nAantall G -> A: " + opd6.GA);
                    writer.write("\nAantall G -> T: " + opd6.GT);
                    writer.write("\nAantall G -> C: " + opd6.AC);
                    writer.write("\nAantall C -> G: " + opd6.CG);
                    writer.write("\nAantall C -> T: " + opd6.CT);
                    writer.write("\nAantall C -> A: " + opd6.CA);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void read(String file) {
        String readLine;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.readLine();
            while ((readLine = bufferedReader.readLine()) != null) {
                if (readLine.contains("#")) {
                    continue;
                } else {
                    String split[] = readLine.split("\t");
                    checker(split[3], split[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checker(String ref, String alt){
        if (ref.length() != alt.length()) {
            if (ref.length() > alt.length()) {
                this.deletie += ref.length() - alt.length();
            } else {
                this.insertie += alt.length() - ref.length();
            }
        } else if (ref.matches("A") && alt.matches("T")) {
            this.AT ++;
        } else if (ref.matches("A") && alt.matches("G")) {
            this.AG ++;
        } else if (ref.matches("A") && alt.matches("C")) {
            this.AC ++;
        } else if (ref.matches("T") && alt.matches("A")) {
            this.TA ++;
        } else if (ref.matches("T") && alt.matches("G")) {
            this.TG ++;
        } else if (ref.matches("T") && alt.matches("C")) {
            this.TC ++;
        } else if (ref.matches("G") && alt.matches("A")) {
            this.GA ++;
        } else if (ref.matches("G") && alt.matches("T")) {
            this.GT ++;
        } else if (ref.matches("G") && alt.matches("C")) {
            this.GC ++;
        } else if (ref.matches("C") && alt.matches("G")) {
            this.CG ++;
        } else if (ref.matches("C") && alt.matches("T")) {
            this.CT ++;
        } else if (ref.matches("C") && alt.matches("A")) {
            this.CA ++;
        }
    }
}
