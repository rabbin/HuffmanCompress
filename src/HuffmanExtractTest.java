
import huffman.Huffman;
import huffman.HuffmanExtract;


public class HuffmanExtractTest {
    public static void main(String[] args){

        System.out.println("**解压缩****");
        String fileToBeExtractPath = "D:\\cs\\java\\HuffmanCompress\\src\\test.txt.hc";

        HuffmanExtract huffmanExtract = Huffman.newHaffmanExtract();

        long begin = System.currentTimeMillis();

        huffmanExtract.extract(fileToBeExtractPath);

        long end = System.currentTimeMillis();
        long time = (end - begin) / 1000;
        long msec = (end-begin)%1000;
        long sec = time %60;
        long min = time/60;
        System.out.println("The program has run " + min + " mins " +sec + " seconds "+msec+" ms");

    }//main

}
