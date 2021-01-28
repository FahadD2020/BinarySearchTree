/**
 * Implements the methods for a Binary Search Tree. The tree Node has
 * country name, and the value is per capita GDP.
 *
 * @author Fahad Dawood n01425457
 * @version July 19, 2020
 *  
 */
public class BinarySearchTree { // open

    private Node root;
    private int size;

    // private class that represents a node
    private class Node { // open Node
        String name;
        double gdpPerCapita;
        Node leftChild;
        Node rightChild;

        public Node(String name, double gdpPerCapita) { // open Node
            this.name = name;
            this.gdpPerCapita = gdpPerCapita;
        } // close Node
        public void printNode() { // open PrintNode
            System.out.printf("%-25s%-20.2f\n", name, gdpPerCapita);
        } // close PrintNode
    } // close Node

    // a class that represents a double number.
    // needed in printBottomTen and printTopTen
    private class MyDouble { // openMy
        double value;
        public MyDouble(Double d) {value = d; }
    } // close My

    /**
     * Create an empty Binary Search Tree
     */
    public BinarySearchTree() { // Open Cons
        root = null;
        size = 0;
    } // close Cons

    /**
     * Insert country name, gdpPerCapita into this tree. If the
     * country name already exists, its gdp gets overwritten. Insertion
     * happens based on the name.
     *
     * @param name country name to insert
     * @param gdpPerCapita gdp per capita of the country.
     */
    public void insert(String name, double gdpPerCapita) { // open insert
        root = insert(root, name, gdpPerCapita);
    } // close insert

    // insert the node in the tree rooted at 'root' and return the
    // new root. Insertion is based on name.
    private Node insert(Node root, String name, double gdpPerCapita) { // open node insert
        if (root == null) { // open if
            // inserting the 1st node or a leaf node
            root = new Node(name, gdpPerCapita);
            size++;
        } else { // close/open if/else
            // recursively insert in left or right sub tree
            if (name.compareTo(root.name) < 0) { // if
                root.leftChild = insert(root.leftChild, name, gdpPerCapita);
            } else if (name.compareTo(root.name) > 0) { // if
                root.rightChild = insert(root.rightChild, name, gdpPerCapita);
            } else { // if else
                // update the gdp
                root.gdpPerCapita = gdpPerCapita;
            } // close else
        } // close else
        return root;
    } // close node insert

    /**
     * Search this tree for the given country name. If found, it's GDP per
     * capita is returned. If not found, it returns -1.
     *
     * @param name country name to lookup
     * @return gdp per capita of the country, or -1 if not found.
     */
    public double find(String name) { // open find
        double gdpPerCapita = -1;
        int visits = 0;   // no. of nodes visited

        Node node = root;
        while (node != null) { // open while
            visits++;
            if (name.compareTo(node.name) == 0) { // if open
                gdpPerCapita = node.gdpPerCapita;  // found it
                break;
            } else if (name.compareTo(node.name) < 0) { // loop 
                node = node.leftChild;   // go left
            } else { // loop
                node = node.rightChild;  // go right
            } // loop
        } // close while

        // print found/not found and no. of nodes visited
        if (gdpPerCapita < 0) { // if
            System.out.printf("%s is not found\n", name);
        } else { // loop
            System.out.printf("%s is found with GDP per Capita %.2f\n", name, gdpPerCapita);
        } // close else
        System.out.printf("%d nodes visited\n\n", visits);

        return gdpPerCapita;
    } // close Find

    /**
     * find and delete given country name from the tree
     *
     * @param name country name to delete
     */
    public void delete(String name) { root = delete(root, name); } // delete

    // recursively delete the name in the tree starting at root,
    // and return the updated root
    private Node delete(Node root, String name) { // delete node
        // if the tree is empty, nothing to do
        if (root == null) { return null; } 

        // find the node to delete
        if (name.compareTo(root.name) < 0) { // if
            root.leftChild = delete(root.leftChild, name);
        } else if (name.compareTo(root.name) > 0) { // loops
            root.rightChild = delete(root.rightChild, name);
        } else { // loop
            // we found the node to delete

            // case when root has 0 or 1 child
            if (root.leftChild == null) { // if
                return root.rightChild;
            } else if (root.rightChild == null) { // loop
                return root.leftChild;
            } // close loop

            // case when root has 2 children
            // we need the in-order successor of the root.
            // This is the left-most node in the right subtree
            Node successor = getLeftMost(root.rightChild);
            root.name = successor.name;
            root.gdpPerCapita = successor.gdpPerCapita;

            // and delete the successor node
            root.rightChild = delete(root.rightChild, successor.name);
        } // close loop
        return root;
    } // delete

    // return the left most node in the tree starting at root.
    // this is the "min" value of tree at root
    private Node getLeftMost(Node root) { //open left
        if (root == null) { return null; }

        Node min = root;
        while (min.leftChild != null) { // open while
            min = min.leftChild;
        } // close while
        return min;
    } // close left

    /**
     * print an in order traversal of this tree
     */
    public void printInorder() { inOrder(root); }

    // inorder traversal (LNR) starting at node
    private void inOrder(Node node) { // opne IO
        if (node != null) { // if
            inOrder(node.leftChild);
            node.printNode();
            inOrder(node.rightChild);
        } // if
    } // close IO

    /**
     * print a pre order traversal of this tree
     */
    public void printPreorder() { preOrder(root); }

    // preorder traversal (NLR) starting at node
    private void preOrder(Node node) { // open Pre
        if (node != null) { // if
            node.printNode();
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        } // if
    } // close Pre

    /**
     * print a post order traversal of this tree
     */
    public void printPostOrder() { postOrder(root);}

    // postOrder traversal (LRN) starting at node
    private void postOrder(Node node) { // open post
        if (node != null) { // if
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            node.printNode();
        } // if
    } // close post

    /**
     * Print 10 countries with the lowest GDP per capita.
     */
    public void printBottomTen() { // open Bottom
        // since the BST has ordering based on name, we need to
        // find the node in the entire tree that is just greater
        // that the current least. Even finding the least requires traversing
        // the entire tree.
        int valNeeded = 10;
        if (size < valNeeded) return;   // we dont even have 10 items in the tree

        Node[] bottom = new Node[valNeeded];
        bottom[0] = getLeast(root, root);
        for (int i = 1; i < bottom.length; i++) { // for
            bottom[i] = getNextHigher(root, bottom[i-1], bottom[i-1], new MyDouble(Double.MAX_VALUE));
        } // for

        for (int i = 0; i < bottom.length; i++) { // for 2
            bottom[i].printNode();
        } // for 2
    } // close Bottom

    // recursively find and return the Node with the least GDP per capita
    private Node getLeast(Node root, Node least) { // open least
        if (root != null) { // if
            if (root.gdpPerCapita < least.gdpPerCapita) { // if
                least = root;
            } // if
            least = getLeast(root.leftChild, least);
            least = getLeast(root.rightChild, least);
        } // if
        return least;
    } // close least

    // find and return the node that has the value just higher than that
    // of node curr, and is < max.
    private Node getNextHigher(Node root, Node curr, Node next, MyDouble max) { // open nextH
        if (root != null) { // if
            if (root.name.compareTo(curr.name) != 0 &&
            root.gdpPerCapita >= curr.gdpPerCapita &&
            root.gdpPerCapita < max.value) { // if
                next = root;
                max.value = root.gdpPerCapita;
            } // if
            next = getNextHigher(root.leftChild, curr, next, max);
            next = getNextHigher(root.rightChild, curr, next, max);
        } // if
        return next;
    } // close nextH

    /**
     * Print 10 countries with the highest GDP per capita.
     */
    public void printTopTen() { // open top
        // similar strategy as in printBottomTen()
        int valNeeded = 10;
        if (size < valNeeded) return;   // we dont even have 10 items in the tree

        Node[] top = new Node[valNeeded];
        top[0] = getMax(root, root);
        for (int i = 1; i < top.length; i++) {// for
            top[i] = getNextLower(root, top[i-1], top[i-1], new MyDouble(Double.MIN_VALUE));
        } // for

        for (int i = 0; i < top.length; i++) { // for
            top[i].printNode();
        } // for
    } // close top

    // recursively find and return the Node with the max GDP per capita
    private Node getMax(Node root, Node max) { // open max
        if (root != null) { // if
            if (root.gdpPerCapita > max.gdpPerCapita) { // if
                max = root;
            } // if
            max = getMax(root.leftChild, max);
            max = getMax(root.rightChild, max);
        } // if
        return max;
    } // close max

    // find and return the node that has the value just lower than that
    // of node curr, and is > min.
    private Node getNextLower(Node root, Node curr, Node next, MyDouble min) { // openlow
        if (root != null) { // if
            if (root.name.compareTo(curr.name) != 0 &&
                    root.gdpPerCapita <= curr.gdpPerCapita &&
                    root.gdpPerCapita > min.value) { // if
                next = root;
                min.value = root.gdpPerCapita;
            } // if
            next = getNextLower(root.leftChild, curr, next, min);
            next = getNextLower(root.rightChild, curr, next, min);
        } // if
        return next;
    } // close low
} // end
