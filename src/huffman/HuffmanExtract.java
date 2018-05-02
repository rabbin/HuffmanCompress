package huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanExtract<E> {
    Map<String, E> haffmanExtractCodeMap = new HashMap<String, E>();
    int size = 0;

    HuffmanExtract() {

    }

    void readHaffanExtractCodeMap(String filePath) {
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line = reader.readLine();
            size = Integer.valueOf(line);
            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(":");
                if (entry[0].equals("newLine")) {
                    Character newLine = '\n';
                    haffmanExtractCodeMap.put(entry[1], (E) newLine);
                } else if (entry[0].equals("colon")) {
                    Character colon = ':';
                    haffmanExtractCodeMap.put(entry[1], (E) colon);
                } else {
                    haffmanExtractCodeMap.put(entry[1], (E) entry[0]);
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
        readHaffanExtractCodeMap(fileToBeExtract.getParent() + "\\" + name.substring(0, name.length() - 3) + ".dic");

        InputStream in = null;
        OutputStreamWriter out = null;
        try {
            in = new FileInputStream(new File(fileNameToBeExtract));
            File file = new File(fileToBeExtract.getParent() + "\\" + name.substring(0, name.length() - 3));
            if (file.exists()) {
                file = new File(fileToBeExtract.getParent() + "\\-"+ name.substring(0, name.length() - 3));
            }
            out = new OutputStreamWriter(new FileOutputStream(file));
            byte[] temp = new byte[1];
            StringBuilder haffmanCode = new StringBuilder();

            // TODO: 2018/5/2 todo 用byte[] 做缓冲区
            while (in.read(temp) > 0) {
                haffmanCode.append(byteToBit(temp[0]));
            }

            String code = "";
            int i = 0;
            while (size > 0) {
                if (haffmanExtractCodeMap.get(code) != null) {
                    if(String.valueOf(haffmanExtractCodeMap.get(code)).equals("\n")){
                        out.write('\r');
                    }
                    out.write(String.valueOf(haffmanExtractCodeMap.get(code)));
                    size--;
                    code = "";
                } else {
                    code += haffmanCode.charAt(i++);
                }
            }
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
