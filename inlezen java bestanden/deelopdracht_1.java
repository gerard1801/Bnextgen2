import java.io.*;
import java.util.*;

/**
 * Created by gerardvanderwel on 06-09-18.
 */

public class deelopdracht_1 {
    private int read_counter;
    private long gem_lengte;
    private int min_read;
    private int max_read;
    private float totaal_basen;
    private float totaal_gc;
    private float gc_content;
    private ArrayList<Float> gc_content_pp_gc;
    private ArrayList<Float> gc_content_pp_bp;
    private ArrayList<Float> GC_pp;

    public deelopdracht_1(){
        this.read_counter = read_counter;
        this.gem_lengte = gem_lengte;
        this.min_read = min_read;
        this.max_read = max_read;
        this.totaal_basen = totaal_basen;
        this.totaal_gc = totaal_gc;
        this.gc_content = gc_content;
        this.gc_content_pp_gc = new ArrayList<Float>(Collections.nCopies(126, 0.0f));
        this.gc_content_pp_bp = new ArrayList<Float>(Collections.nCopies(126, 0.0f));
        this.GC_pp = new ArrayList<Float>();
    }

    public int getRead_counter() {
        return this.read_counter;
    }

    public long getGem_lengte() {
        return this.gem_lengte;
    }

    public int getMin_read() {
        return this.min_read;
    }

    public int getMax_read() {
        return this.max_read;
    }

    public float getTotaal_basen(){
        return this.totaal_basen;
    }

    public float getTotaal_gc(){
        return this.totaal_gc;
    }

    public float getGc_content(){
        return (this.totaal_gc/this.totaal_basen) * 100;
    }

    public ArrayList<Float> getGc_content_pp_gc(){
        return this.gc_content_pp_gc;
    }

    public ArrayList<Float> getGc_content_pp_bp(){
        return this.gc_content_pp_bp;
    }

    public ArrayList<Float> getGC_pp(){
        for(int i=0; i < gc_content_pp_gc.size(); i++){
            this.GC_pp.add(this.gc_content_pp_gc.get(i)/this.gc_content_pp_bp.get(i)*100);
        }
        return this.GC_pp;
    }

    public static void main(String[] args) throws IOException{
        try {
            if (args.length == 1 && args[0].matches("-h")) {
                System.out.println("\nDit is de gebruikers informatie van Bnextgen_1.jar.\n\n" +
                        "---FUNCTIE---\nIn dit script wordt er een Quality Control uitgevoerd op de fastq bestanden." +
                        "\n\n---GEBRUIK---\n" +
                        "Om het script te kunnen runnen zijn er een aantal variabelen nodig.\n" +
                        "De layout van het aanroepen is als volgt: \n\njava -jar {file} {input1} {input2} {output1} {output2}" +
                        "\n{file} : Het pad naar het script.\n{input1} : Het pad naar het eerste fastq bestand." +
                        "\n{input2}: Het pad naar het tweede fastq bestand." + "\n{output1}: De naam van het output-bestand met de analyse van {input1}" +
                        "\n{output2}: De naam van het output-bestand met de analyse van {input2}" +
                        "\n\n---AANROEPEN---\n" +
                        "Een voorbeeld van het aanroepen van het script is hier weergeven:\n\n" +
                        "java -jar /home/s1095337/Bnextgen_1.jar /home/bngp/reads/bngsa_nietinfected_1.fastq" +
                        " /home/bngp/reads/bngsa_nietinfected_2.fastq /home/s1095337/out_deelopdr2_trim1.txt " +
                        "/home/s1095337/out_deelopdr2_trim2.txt\n");
            } else {
                String fileName1 = args[0];
                String fileName2 = args[1];
                String outName1 = args[2];
                String outName2 = args[3];
                deelopdracht_1 opd1 = new deelopdracht_1();
                deelopdracht_1 opd1v2 = new deelopdracht_1();
                opd1.read_write(fileName1);
                opd1.Write_to_file(outName1);
                opd1v2.read_write(fileName2);
                opd1v2.Write_to_file(outName2);
            }
        } catch (Exception e) {
            System.out.println("\nDe command is niet geldig!\nVoor meer informatie: java -jar /home/s1095337/Bnextgen_1.jar -h\n");
        }
    }

    public void Write_to_file(String out_file) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(out_file), "utf-8"))) {
            writer.write("Het aantal reads: " + getRead_counter() + "\n");
            writer.write("Gemiddelde lengte van de reads: " + getGem_lengte() / getRead_counter() + "\n");
            writer.write("Minimum lengte van de reads: " + getMin_read() + "\n");
            writer.write("Maximum lengte van de reads: " + getMax_read() + "\n");
            writer.write("GC content: " + getGc_content() + "\n");
            writer.write("GC per positie: " + getGC_pp() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read_write(String filename) throws IOException {
        String readLine;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            bufferedReader.readLine();
            while ((readLine = bufferedReader.readLine()) != null) {
                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                read_counter(readLine);
                gem_counter(readLine);
                min_read(readLine);
                max_read(readLine);
                gc_content(readLine);
                gc_content_pp(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int read_counter(String read) {
        return this.read_counter+=1;
    }

    public long gem_counter(String read) {
        return this.gem_lengte+=read.length();
    }

    public int min_read(String read) {
        if(this.min_read == 0) {
            this.min_read = read.length();
        }

        if(read.length() < this.min_read){
            this.min_read = read.length();
        }
        return this.min_read;
    }

    public int max_read(String read) {
        if(read.length() > this.max_read){
            this.max_read = read.length();
        }
        return this.max_read;
    }

    public void gc_content(String read) {
        this.totaal_gc += read.chars().filter(num -> num == 'G').count();
        this.totaal_gc += read.chars().filter(num -> num == 'C').count();
        this.totaal_basen += read.length();
    }

    public void gc_content_pp(String read) {
        for (int i = 0; i < read.length(); i++) {
            this.gc_content_pp_bp.set(i, this.gc_content_pp_bp.get(i) + 1.0f);
            char a_char = read.charAt(i);
            if (a_char == 'G' || a_char == 'C') {
                this.gc_content_pp_gc.set(i, this.gc_content_pp_gc.get(i) + 1.0f);

            } else {
                this.gc_content_pp_gc.set(i, this.gc_content_pp_gc.get(i));
            }
        }
    }
}