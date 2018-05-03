package huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanExtract{
    private Map<String, Object> huffmanExtractCodeMap = new HashMap<>();
    private int size = 0;

    HuffmanExtract() {

    }

    private void readHuffmanExtractCodeMap(String filePath) {

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

            String line = reader.readLine();
            size = Integer.valueOf(line);

            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(":");
                if (entry[0].equals("newLine")) {
                    huffmanExtractCodeMap.put(entry[1], '\n');
                } else if (entry[0].equals("colon")) {
                    huffmanExtractCodeMap.put(entry[1], ':');
                } else {
                    huffmanExtractCodeMap.put(entry[1], entry[0].charAt(0));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void extract(String fileNameToBeExtract) {

        File fileToBeExtract = new File(fileNameToBeExtract);
        String name = fileToBeExtract.getName();
        readHuffmanExtractCodeMap(fileToBeExtract.getParent() + "\\" + name.substring(0, name.length() - 3) + ".dic");

        InputStream in = null;
        OutputStreamWriter out = null;
        try {

            in = new FileInputStream(new File(fileNameToBeExtract));
            byte[] bytes = new byte[1000];
            StringBuilder huffmanCode = new StringBuilder();

            int numBytesPerRead;
            while ((numBytesPerRead = in.read(bytes)) > 0) {
                for(int i = 0; i<numBytesPerRead;i++){
                    huffmanCode.append(byteToBit(bytes[i]));
                }//for
            }//while


            File file = new File(fileToBeExtract.getParent() + "\\" + name.substring(0, name.length() - 3));
            if (file.exists()) {
                file = new File(fileToBeExtract.getParent() + "\\-"+ name.substring(0, name.length() - 3));
            }
            out = new OutputStreamWriter(new FileOutputStream(file));
            String code = "";
            int i = 0;
            while (size > 0) {
                if (huffmanExtractCodeMap.get(code) != null) {
                    if(String.valueOf(huffmanExtractCodeMap.get(code)).equals("\n")){
                        out.write('\r');
                    }
                    out.write(String.valueOf(huffmanExtractCodeMap.get(code)));
                    size--;
                    code = "";
                } else {
                    code += huffmanCode.charAt(i++);
                }
            }//while
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
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
        }

    }

    static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }


}
