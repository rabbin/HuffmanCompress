package huffman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanCodeGenerator{


    static <E> Map<E, String> createHuffmanCode(List<HuffmanTreeNode<E>> wordCountList) {

        Map<E, String> huffmanCompressCodeMap = new HashMap<>();

        while (wordCountList.size() > 1) {

            sort(wordCountList);

            HuffmanTreeNode<E> leftChild = wordCountList.get(wordCountList.size() - 1);
            HuffmanTreeNode<E> rightChild = wordCountList.get(wordCountList.size() - 2);

            HuffmanTreeNode<E> parent = new HuffmanTreeNode<>(null, leftChild.weight + rightChild.weight);
            parent.setLeftChild(leftChild);
            parent.setRightChild(rightChild);

            updateChildHuffmanCode("1", leftChild,huffmanCompressCodeMap);
            updateChildHuffmanCode("0", rightChild,huffmanCompressCodeMap);

            wordCountList.remove(leftChild);
            wordCountList.remove(rightChild);
            wordCountList.add(parent);



        }
        return huffmanCompressCodeMap;

    }//createHuffmanCode

    static private  <E> void updateChildHuffmanCode(String parrentCode, HuffmanTreeNode<E> node,Map<E,String> huffmanCompressCodeMap) {


        node.code = parrentCode + node.code;
        if (node.node != null) {
            huffmanCompressCodeMap.put(node.node, node.code);
        }

        if (node.leftChild != null) {
            updateChildHuffmanCode(String.valueOf(node.code.charAt(0)), node.leftChild,huffmanCompressCodeMap);
        }
        if (node.rightChild != null) {
            updateChildHuffmanCode(String.valueOf(node.code.charAt(0)), node.rightChild,huffmanCompressCodeMap);
        }
    }

    static private <E> void sort(List<HuffmanTreeNode<E>> wordCountList) {

        //建小顶堆
        for (int i = (wordCountList.size() - 1) / 2; i >= 0; i--) {
            heapAdjust(wordCountList, i, wordCountList.size());
        }
        for (int i = wordCountList.size() - 1; i >= 0; i--) {
            swap(wordCountList.get(0), wordCountList.get(i));
            heapAdjust(wordCountList, 0, i);
        }

    }//sort

    static private <E>  void heapAdjust(List<HuffmanTreeNode<E>> wordCountList, int parentIndex, int end) {


        for (int i = 2 * parentIndex + 1; i < end; i = i * 2 + 1) {

            HuffmanTreeNode<E> parent = wordCountList.get(parentIndex);

            if (i + 1 < end && wordCountList.get(i).compareTo(wordCountList.get(i + 1)) > 0) {
                i++;
            }
            HuffmanTreeNode<E> maxChild = wordCountList.get(i);
            if (parent.compareTo(maxChild) <= 0) {
                break;
            } else {
                swap(parent, maxChild);
                parentIndex = i;
            }
        }


    }//heapAdjust

    static private <E> void swap(HuffmanTreeNode<E> first, HuffmanTreeNode<E> second) {
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
