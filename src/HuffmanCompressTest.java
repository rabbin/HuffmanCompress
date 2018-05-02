import huffman.Huffman;
import huffman.HuffmanCompress;

import java.io.File;

public class HuffmanCompressTest {
    public static void main(String[] args) {

        System.out.println("**压缩*****：");
        String fileToBeCompressPath = "D:\\cs\\java\\HuffmanCompress\\src\\test.txt";

        HuffmanCompress<Character> huffmanCompress = Huffman.newHaffmanCompress();

        long begin = System.currentTimeMillis();

        huffmanCompress.compress(fileToBeCompressPath);

        long end = System.currentTimeMillis();
        long time = (end - begin) / 1000;
        long msec = (end-begin)%1000;
        long sec = time %60;
        long min = time/60;
        System.out.println("The program has run " + min + " mins " +sec + " seconds "+msec+" ms");

        File originalFile = new File(fileToBeCompressPath);
        long originalFileSize = originalFile.length();


        File compressedFile = new File(originalFile.getParent()+"\\"+originalFile.getName()+".hc");
        long compressedFileSize = compressedFile.length();
        System.out.printf("压缩比为：%.2f%%\n",(double)compressedFileSize*100/originalFileSize);


    }//main


}