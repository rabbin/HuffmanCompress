package haffman;

class HaffmanTreeNode<E> implements Comparable<HaffmanTreeNode<E>> {
    E node;
    double weight;
    HaffmanTreeNode<E> leftChild = null;
    HaffmanTreeNode<E> rightChild = null;
    String code = "";

    HaffmanTreeNode(E node, double count) {
        this.node = node;
        this.weight = count;
    }

    HaffmanTreeNode() {
        this.node = null;
        this.node = null;
    }

    void setLeftChild(HaffmanTreeNode<E> leftChild) {
        this.leftChild = leftChild;
    }

    void setRightChild(HaffmanTreeNode<E> rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return String.valueOf(node) + ":" + String.valueOf(weight);
    }

    @Override
    public int compareTo(HaffmanTreeNode o) {
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
