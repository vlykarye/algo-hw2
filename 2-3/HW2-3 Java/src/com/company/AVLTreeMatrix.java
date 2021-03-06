package com.company;

/*
 *  Java Program to Implement AVL Tree
 *
 *  Written by: Manish Bhojasia
 *  Found at: http://www.sanfoundry.com/java-program-implement-avl-tree/
 *
 */

import java.util.Random;

/* Class AVLNodeMatrix */
class AVLNodeMatrix {
    AVLNodeMatrix left, right;
    int data;
    int height;
    byte[] matrix;

    /* Constructor */
    public AVLNodeMatrix() {
        left = null;
        right = null;
        data = 0;
        height = 0;
    }

    /* Constructor */
    public AVLNodeMatrix(int n) {
        left = null;
        right = null;
        data = n;
        height = 0;
    }
}

/* Class AVLTree */
class AVLTreeMatrix {
    private AVLNodeMatrix root;
    private Random rand = new Random();
    public int count = 0;

    /* Constructor */
    public AVLTreeMatrix() {
        root = null;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return root == null;
    }

    /* Make the tree logically empty */
    public void makeEmpty() {
        root = null;
    }

    /* Function to insert data */
    public void insert(int data) {
        root = insert(data, root);
    }

    public void delete(int data) {
        root = delete(data, root);
    }

    /* Function to get height of node */
    private int height(AVLNodeMatrix t) {
        return t == null ? -1 : t.height;
    }

    /* Function to max of left/right node */
    private int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    /* Function to insert data recursively */
    private AVLNodeMatrix insert(int x, AVLNodeMatrix t) {
        if (t == null) {
            ++count;
            t = new AVLNodeMatrix(x);
            if (x % 3 == 0) {
                t.matrix = new byte[(int) Math.pow(2, 20)];
            }
            if (x % 3 == 1) {
                t.matrix = new byte[(int) Math.pow(2, 19) + (int) Math.pow(2, 18)];
            }
            if (x % 3 == 2) {
                t.matrix = new byte[(int) Math.pow(2, 18) + (int) Math.pow(2, 17)];
            }
            for (int i = 0; i < t.matrix.length; ++i) {
                t.matrix[i] = 1;
            }
        } else if (x < t.data) {
            t.left = insert(x, t.left);
            if (height(t.left) - height(t.right) == 2) {
                if (x < t.left.data)
                    t = rotateWithLeftChild(t);
                if (x > t.left.data)
                    t = doubleWithLeftChild(t);
            }
        } else if (x > t.data) {
            t.right = insert(x, t.right);
            if (height(t.right) - height(t.left) == 2) {
                if (x > t.right.data)
                    t = rotateWithRightChild(t);
                if (x < t.right.data)
                    t = doubleWithRightChild(t);
            }
        } else
            ;  // Duplicate; do nothing
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /* Function to delete data recursively */
    private AVLNodeMatrix delete(int x, AVLNodeMatrix t) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (t == null) {
            return t;
        }

        // If the key to be deleted is smaller than
        // the t's key, then it lies in left subtree
        if (x < t.data) {
            t.left = delete(x, t.left);
        }

        // If the key to be deleted is greater than the
        // t's key, then it lies in right subtree
        else if (x > t.data) {
            t.right = delete(x, t.right);
        }

        // if key is same as t's key, then this is the node
        // to be deleted
        else {
            // node with only one child or no child
            if ((t.left == null) || (t.right == null)) {
                AVLNodeMatrix temp = t.left;
                if (temp == null) {
                    temp = t.right;
                }
                // No child case
                if (temp == null) {
                    temp = t;
                    t = null;
                } else   // One child case
                    t = temp; // Copy the contents of
                // the non-empty child
                --count;
            } else {
                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                // must descend all left subtrees of the
                // right subtree until there are no more
                AVLNodeMatrix temp = t.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                // swap with t
                t.data = temp.data;
                // delete temp
                t.right = delete(temp.data, t.right);
            }
        }

        // If the tree had only one node then return
        if (t == null) {
            return t;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        t.height = max(height(t.left), height(t.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        int balance = height(t.right) - height(t.left);

        // balance > 1 means right heavy
        // balance < -1 means left heavy
        //

        if (balance > 1) {
            if (t.right.left != null && t.right.right != null) {
                if (height(t.right.left) - height(t.right.right) > 1) {
                    t = doubleWithRightChild(t);
                } else {
                    t = rotateWithRightChild(t);
                }
            }
            if (t.right.left != null && t.right.right == null) {
                t = doubleWithRightChild(t);
            }
            if (t.right.left == null && t.right.right != null) {
                t = rotateWithRightChild(t);
            }
        }

        if (balance < -1) {
            if (t.left.right != null && t.left.left != null) {
                if (height(t.left.right) - height(t.left.left) > 1) {
                    t = doubleWithLeftChild(t);
                } else {
                    t = rotateWithLeftChild(t);
                }
            }
            if (t.left.left == null && t.left.right != null) {
                t = doubleWithLeftChild(t);
            }
            if (t.left.left != null && t.left.right == null) {
                t = rotateWithLeftChild(t);
            }
        }

        return t;
    }

    /* Rotate binary tree node with left child */

    private AVLNodeMatrix rotateWithLeftChild(AVLNodeMatrix k2) {
        AVLNodeMatrix k1 = k2.left;
        if (k2.left == null) {
            int k = 0;
        }
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /* Rotate binary tree node with right child */
    private AVLNodeMatrix rotateWithRightChild(AVLNodeMatrix k1) {
        AVLNodeMatrix k2 = k1.right;
        if (k1.right == null) {
            int k = 0;
        }
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child
     */
    private AVLNodeMatrix doubleWithLeftChild(AVLNodeMatrix k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child
     */
    private AVLNodeMatrix doubleWithRightChild(AVLNodeMatrix k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    /* Functions to count number of nodes */
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(AVLNodeMatrix r) {
        if (r == null)
            return 0;
        else {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }

    /* Functions to search for an element */
    public boolean search(int val) {
        return search(root, val);
    }

    private boolean search(AVLNodeMatrix r, int val) {
        boolean found = false;
        while ((r != null) && !found) {
            int rval = r.data;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    /* Function for inorder traversal */
    public void inorder() {
        inorder(root);
    }

    private void inorder(AVLNodeMatrix r) {
        if (r != null) {
            inorder(r.left);
            System.out.print(r.data + " ");
            inorder(r.right);
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(root);
    }

    private void preorder(AVLNodeMatrix r) {
        if (r != null) {
            System.out.print(r.data + " ");
            preorder(r.left);
            preorder(r.right);
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(root);
    }

    private void postorder(AVLNodeMatrix r) {
        if (r != null) {
            postorder(r.left);
            postorder(r.right);
            System.out.print(r.data + " ");
        }
    }
}
