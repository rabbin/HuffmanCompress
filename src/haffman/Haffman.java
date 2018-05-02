package haffman;

public class Haffman<E> {
    // TODO: 2018/5/2 todo 设计模式 & 泛型 & 注释 & README.md
    public static <E> HaffmanCompress<E> newHaffmanCompress() {
        return new HaffmanCompress<>();
    }

    public static <E> HaffmanExtract<E> newHaffmanExtract() {
        return new HaffmanExtract<>();
    }

}
