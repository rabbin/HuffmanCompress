package huffman;

import java.io.*;
import java.util.*;

public class HuffmanCompress<E> {

    Map<E, String> haffmanCompressCodeMap = new HashMap<E, String>();

    HuffmanCompress() {

    }

    public void compress(String filePath) {

        List<HuffmanTreeNode<E>> wordCountList = readFileByCharsAndWordCount(filePath);
        createHaffmanTree(wordCountList);

        Reader reader = null;
        OutputStream out = null;

        Writer outDic = null;
        File file;
        try {
            file = new File(filePath);
            reader = new InputStreamReader(new FileInputStream(file));

            outDic = new OutputStreamWriter(new FileOutputStream(file.getParent() + "\\" + file.getName() + ".dic"));
            out = new FileOutputStream(new File(file.getParent() + "\\" + file.getName() + ".hc"));
            String codeBuffer = "";

            int temp;
            char tempChar;
            int count = 0;
            while ((temp = reader.read()) != -1) {
// TODO: 2018/5/1 todo 用BufferedReader读。

                tempChar = (char) temp;
                if ((tempChar) != '\r') {
                    codeBuffer += (haffmanCompressCodeMap.get(tempChar));
                    count++;
                }//if

            }//while temp = reader.read()) != -1


            outDic.write(String.valueOf(count));
            outDic.write("\n");
            outDic.flush();

            int size = codeBuffer.length();
            byte[] tmp = new byte[1];
            for (int i = 0; i < size; i++) {
                if (codeBuffer.charAt(i) == '1') {
                    tmp[0] |= 1;
                }
                if (i % 8 == 7) {
                    out.write(tmp);

                    tmp[0] = 0;
                }
                tmp[0] <<= 1;
            }//for

            for (int j = 8 - (size % 8); j > 1; j--) {
                tmp[0] <<= 1;
            }

            out.write(tmp);
            out.flush();

            for (Map.Entry<E, String> entry : haffmanCompressCodeMap.entrySet()) {
                if ((Character) entry.getKey() == '\n') {
                    outDic.write("newLine" + ":" + entry.getValue());
                } else if((Character) entry.getKey() == ':'){
                    outDic.write("colon" + ":" + entry.getValue());
                } else {
                    outDic.write(entry.getKey() + ":" + entry.getValue());
                }

                outDic.write("\n");
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


    private List<HuffmanTreeNode<E>> readFileByCharsAndWordCount(String fileName) {

        Map<Character, Integer> wordCountMap = new HashMap<>(1000);

        Reader reader = null;
        try {
            // TODO: 2018/5/2 todo 用BufferedReader读
            reader = new InputStreamReader(new FileInputStream(new File(fileName)));
            int temp;
            char tempChar;
            while ((temp = reader.read()) != -1) {
                tempChar = (char) temp;
                if ((tempChar) != '\r') {
                    if ((wordCountMap.get(tempChar) == null)) {
                        wordCountMap.put(tempChar, 1);
                    } else {
                        wordCountMap.put(tempChar, wordCountMap.get(tempChar) + 1);
                    }
                }//if ((tempChar) != '\r')
            }// while ((temp = reader.read()) != -1)

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

        List<HuffmanTreeNode<E>> wordCountList = new ArrayList<HuffmanTreeNode<E>>(1000);
        for (Map.Entry<Character, Integer> entry : wordCountMap.entrySet()) {
            wordCountList.add(new HuffmanTreeNode(entry.getKey(), entry.getValue()));
        }
        return wordCountList;
    }

    private void createHaffmanTree(List<HuffmanTreeNode<E>> wordCountList) {

        while (wordCountList.size() > 1) {
            sort(wordCountList);

            HuffmanTreeNode<E> leftChild = wordCountList.get(wordCountList.size() - 1);
            HuffmanTreeNode<E> rightChild = wordCountList.get(wordCountList.size() - 2);

            HuffmanTreeNode<E> parrent = new HuffmanTreeNode<E>(null, leftChild.weight + rightChild.weight);
            parrent.setLeftChild(leftChild);
            parrent.setRightChild(rightChild);

            updateChildHaffmanCode("1", leftChild);
            updateChildHaffmanCode("0", rightChild);

            wordCountList.remove(leftChild);
            wordCountList.remove(rightChild);
            wordCountList.add(parrent);


        }


    }//createHaffmanTree

    private void updateChildHaffmanCode(String parrentCode, HuffmanTreeNode<E> node) {


        node.code = parrentCode + node.code;
        if (node.node != null) {
            haffmanCompressCodeMap.put(node.node, node.code);
        }

        if (node.leftChild != null) {
            updateChildHaffmanCode(String.valueOf(node.code.charAt(0)), node.leftChild);
        }
        if (node.rightChild != null) {
            updateChildHaffmanCode(String.valueOf(node.code.charAt(0)), node.rightChild);
        }
    }

    private void sort(List<HuffmanTreeNode<E>> wordCountList) {

        //建小顶堆
        for (int i = (wordCountList.size() - 1) / 2; i >= 0; i--) {
            heapAdjust(wordCountList, i, wordCountList.size());
        }
        for (int i = wordCountList.size() - 1; i >= 0; i--) {
            swap(wordCountList.get(0), wordCountList.get(i));
            heapAdjust(wordCountList, 0, i);
        }

    }//sort

    private void heapAdjust(List<HuffmanTreeNode<E>> wordCountList, int parrentIndex, int end) {


        for (int i = 2 * parrentIndex + 1; i < end; i = i * 2 + 1) {

            HuffmanTreeNode parrent = wordCountList.get(parrentIndex);

            if (i + 1 < end && wordCountList.get(i).compareTo(wordCountList.get(i + 1)) > 0) {
                i++;
            }
            HuffmanTreeNode maxChild = wordCountList.get(i);
            if (parrent.compareTo(maxChild) <= 0) {
                break;
            } else {
                swap(parrent, maxChild);
                parrentIndex = i;
            }
        }


    }//heapAdjust

    private void swap(HuffmanTreeNode<E> first, HuffmanTreeNode<E> second) {
        HuffmanTreeNode<E> temp = new HuffmanTreeNode<>();
        temp.node = first.node;
        temp.weight = first.weight;
        temp.leftChild = first.leftChild;
        temp.rightChild = first.rightChild;
        temp.code = first.code;

        first.node = second.node;
        first.weight = second.weight;
        first.leftChild = second.leftChild;
        first.rightChild = second.rightChild;
        first.code = second.code;

        second.node = temp.node;
        second.weight = temp.weight;
        second.leftChild = temp.leftChild;
        second.rightChild = temp.rightChild;
        second.code = temp.code;


    }

}
