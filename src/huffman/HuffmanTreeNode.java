package huffman;

class HuffmanTreeNode<E> implements Comparable<HuffmanTreeNode<E>> {
    E node;
    double weight;
    HuffmanTreeNode<E> leftChild = null;
    HuffmanTreeNode<E> rightChild = null;
    String code = "";

    HuffmanTreeNode(E node, double count) {
        this.node = node;
        this.weight = count;
    }

    HuffmanTreeNode() {
        this.node = null;
        this.node = null;
    }

    void setLeftChild(HuffmanTreeNode<E> leftChild) {
        this.leftChild = leftChild;
    }

    void setRightChild(HuffmanTreeNode<E> rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return String.valueOf(node) + ":" + String.valueOf(weight);
    }

    @Override
    public int compareTo(HuffmanTreeNode o) {
        double res = this.weight - o.weight;
        if (res > 0) {
            return 1;
        } else if (res == 0) {
            return 0;
        } else if (res < 0) {
            return -1;
        }
        return 0;
    }
}
