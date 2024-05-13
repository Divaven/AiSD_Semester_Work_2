public interface Tree {

    Node insert(long data);

    Node delete(long data);

    Node find(long data);

    Node findRecursively(long data);

    void traverse();

    long getMax();

    long getMin();

    boolean isEmpty();

}
