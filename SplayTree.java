public class SplayTree implements Tree {

    private Node root;
    private long insert_cnt;
    private long search_cnt;
    private long delete_cnt;

    public Node getRoot() {
        return root;
    }

    public long getInsertCnt() {
        return insert_cnt;
    }

    public long getSearchCnt() {
        return search_cnt;
    }

    public long getDeleteCnt() {
        return delete_cnt;
    }


    @Override
    public Node insert(long data) {
        root = insert(root, new Node(data));
        return root;
    }

    private Node insert(Node node, Node nodeToInsert) {
        if (node == null) {
            return nodeToInsert;
        }
        if (nodeToInsert.getData() < node.getData()) {
            node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
            node.getLeftChild().setParent(node);
        } else if (nodeToInsert.getData() > node.getData()) {
            node.setRightChild(insert(node.getRightChild(), nodeToInsert));
            node.getRightChild().setParent(node);
        }
        insert_cnt++;
        return node;
    }

    @Override
    public Node delete(long data) {
        root = delete(data, root);
        return root;
    }

    public Node delete(long data, Node node) {
        if (node == null) return null;

        if (data < node.getData()) {
            node.setLeftChild(delete(data, node.getLeftChild()));
            if (node.getLeftChild() != null) node.getLeftChild().setParent(node);
        } else if (data > node.getData()) {
            node.setRightChild(delete(data, node.getRightChild()));
            if (node.getRightChild() != null) node.getRightChild().setParent(node);
        } else {
            // One Child or Leaf Node (no children)
            if (node.getLeftChild() == null) return node.getRightChild();
            else if (node.getRightChild() == null) return node.getLeftChild();
            // Two Children
            node.setData(getMax(node.getLeftChild()));
            node.setLeftChild(delete(node.getData(), node.getLeftChild()));
            if (node.getLeftChild() != null) node.getLeftChild().setParent(node);
        }
        delete_cnt++;
        return node;
    }

    @Override
    public Node find(long data) {
        Node node = root;
        while (node != null) {
            search_cnt++;
            if (node.getData() == data) {
                splay(node);
                return node;
            }
            node = data < node.getData() ? node.getLeftChild() : node.getRightChild();
        }
        return null;
    }

    @Override
    public Node findRecursively(long data) {
        return find(root, data);
    }

    public Node find(Node node, long data) {
        if (node != null) {
            if (node.getData() == data) {
                splay(node);
                return node;
            }
            Node nextNode = data > node.getData() ? node.getRightChild() : node.getLeftChild();
            find(nextNode, data);
        }
        return null;
    }

    private void splay(Node node) {
        while (node != root) {
            Node parent = node.getParent();
            if (node.getGrandParent() == null) {
                if (node.isLeftChild()) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else if (node.isLeftChild() && parent.isLeftChild()) {
                rotateRight(node.getGrandParent());
                rotateRight(parent);
            } else if (node.isRightChild() && parent.isRightChild()) {
                rotateLeft(node.getGrandParent());
                rotateLeft(parent);
            } else if (node.isLeftChild() && parent.isRightChild()) {
                rotateRight(parent);
                rotateLeft(parent);
            } else {
                rotateLeft(parent);
                rotateRight(parent);
            }
        }
    }

    private long getMax(Node node) {
        if (node == null) return Integer.MIN_VALUE;
        long max = node.getData();
        long leftMax = getMax(node.getLeftChild());
        long rightMax = getMax(node.getRightChild());
        if (leftMax > max) {
            max = leftMax;
        }
        if (rightMax > max) {
            max = rightMax;
        }
        return max;
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.getRightChild();

        if (rightChild == null) return;

        node.setRightChild(rightChild.getLeftChild());
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node);
        }
        rightChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = rightChild;
        } else {
            if (node.isLeftChild()) {
                node.getParent().setLeftChild(rightChild);
            } else {
                node.getParent().setRightChild(rightChild);
            }
        }
        rightChild.setLeftChild(node);
        node.setParent(rightChild);
    }

    private void rotateRight(Node node) {
        Node leftChild = node.getLeftChild();
        if (leftChild == null) return;

        node.setLeftChild(leftChild.getRightChild());
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node);
        }
        leftChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = leftChild;
        } else {
            if (node.isLeftChild()) {
                node.getParent().setLeftChild(leftChild);
            } else {
                node.getParent().setRightChild(leftChild);
            }
        }
        leftChild.setRightChild(node);
        node.setParent(leftChild);
    }

    @Override
    public void traverse() {
        traverse(root);
    }

    private void traverse(Node node) {
        if (node != null) {
            traverse(node.getLeftChild());
            System.out.print(node.getData() + " ");
            traverse(node.getRightChild());
        }
    }

    @Override
    public long getMax() {
        return getMax(root);
    }

    @Override
    public long getMin() {
        return getMin(root);
    }

    private long getMin(Node node) {
        if (node == null) return Integer.MAX_VALUE;
        long min = node.getData();
        long leftMin = getMin(node.getLeftChild());
        long rightMin = getMin(node.getRightChild());
        if (leftMin < min) {
            min = leftMin;
        }
        if (rightMin < min) {
            min = rightMin;
        }
        return min;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
}
