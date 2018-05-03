package huffman;

import java.io.*;
import java.util.*;

public class HuffmanCompress{

    HuffmanCompress() {

    }

    public void compress(String filePath) {

        List<HuffmanTreeNode<Character>> wordCountList = readFileAndWordCount(filePath);
        Map<Character, String> huffmanCompressCodeMap = HuffmanCodeGenerator.createHuffmanCode(wordCountList);

        Reader reader = null;
        OutputStream out = null;
        BufferedWriter outDic = null;
        File file;

        try {

            file = new File(filePath);
            reader =  new InputStreamReader(new FileInputStream(file));
            String codeBuffer = "";
            char[] chars = new char[1000];
            int count = 0;
            int numCharsPerRead;
            while ((numCharsPerRead= reader.read(chars)) > 0) {

                for(int i = 0;i<numCharsPerRead;i++){
                    if (chars[i] != '\r') {
                        codeBuffer += (huffmanCompressCodeMap.get(chars[i]));
                        count++;
                    }//if
                }

            }//(numCharsPerRead= reader.read(chars)) > 0


            out = new FileOutputStream(new File(file.getParent() + "\\" + file.getName() + ".hc"));

            int size = codeBuffer.length();
            byte[] tmp = new byte[1000];
            int j =0;
            for (int i = 0; i < size; i++) {
                if (codeBuffer.charAt(i) == '1') {
                    tmp[j] |= 1;
                }
                if (i % 8 == 7) {
                    j++;
                    if(j >= 1000){
                        out.write(tmp);
                        j = 0;
                    }
                }
                tmp[j] <<= 1;
            }//for

            for (int m = 8 - (size % 8); m > 1; m--) {
                tmp[j] <<= 1;
            }

            for(int i = 0 ;i<=j;i++){
                out.write(tmp[i]);
            }
            out.flush();

            outDic = new BufferedWriter(new FileWriter(file.getParent() + "\\" + file.getName() + ".dic"));
            outDic.write(String.valueOf(count));
            outDic.newLine();
            outDic.flush();

            for (Map.Entry<Character, String> entry : huffmanCompressCodeMap.entrySet()) {
                if ( entry.getKey() == '\n') {
                    outDic.write("newLine" + ":" + entry.getValue());
                } else if(entry.getKey() == ':'){
                    outDic.write("colon" + ":" + entry.getValue());
                } else {
                    outDic.write(entry.getKey() + ":" + entry.getValue());
                }

                outDic.newLine();
            }
            outDic.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//if

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//if

            if (outDic != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//if
        }//finally
    }//compress


    private List<HuffmanTreeNode<Character>> readFileAndWordCount(String fileName) {

        Map<Character, Integer> wordCountMap = new HashMap<>(1000);

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(fileName));

            char[] chars = new char[1000];
            int numCharsPerRead;
            while ((numCharsPerRead= reader.read(chars)) > 0) {

                for(int i = 0;i<numCharsPerRead;i++){
                    if (chars[i] != '\r') {
                        if ((wordCountMap.get(chars[i]) == null)) {
                            wordCountMap.put(chars[i], 1);
                        } else {
                            wordCountMap.put(chars[i], wordCountMap.get(chars[i]) + 1);
                        }
                    }//if
                }

            }//(numCharsPerRead= reader.read(chars)) > 0

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//if
        }

        List<HuffmanTreeNode<Character>> wordCountList = new ArrayList<>(1000);
        for (Map.Entry<Character, Integer> entry : wordCountMap.entrySet()) {
            wordCountList.add(new HuffmanTreeNode<>(entry.getKey(), entry.getValue()));
        }

        return wordCountList;
    }


}
