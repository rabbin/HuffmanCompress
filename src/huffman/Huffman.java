package huffman;

public class Huffman<E> {
    // TODO: 2018/5/2 todo 设计模式 & 泛型 & 注释 & README.md
    public static <E> HuffmanCompress<E> newHaffmanCompress() {
        return new HuffmanCompress<>();
    }

    public static <E> HuffmanExtract<E> newHaffmanExtract() {
        return new HuffmanExtract<>();
    }

}
