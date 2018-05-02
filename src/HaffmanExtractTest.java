
import haffman.Haffman;
import haffman.HaffmanExtract;

public class HaffmanExtractTest {
    public static void main(String[] args){

        System.out.println("**解压缩****");
        String fileToBeExtractPath = "D:\\cs\\java\\HuffmanCompress\\src\\test.txt.hc";

        HaffmanExtract<Character> haffmanExtract = Haffman.newHaffmanExtract();

        long begin = System.currentTimeMillis();

        haffmanExtract.extract(fileToBeExtractPath);

        long end = System.currentTimeMillis();
        long time = (end - begin) / 1000;
        long msec = (end-begin)%1000;
        long sec = time %60;
        long min = time/60;
        System.out.println("The program has run " + min + " mins " +sec + " seconds "+msec+" ms");


    }//main

}
