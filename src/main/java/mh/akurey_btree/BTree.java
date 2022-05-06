/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mh.akurey_btree;

/**
 *
 * @author Aozhen
 */

public class BTree {

  private int minDegree;
  private int orderM;
  private int height;
  private int count;
  private BNode root;

 
  public class BNode {
    int occupiedKeys;
    int key[] = new int[orderM];
    BNode subtrees[] = new BNode[orderM+1];
    boolean isLeaf = true;
  }

  public BTree(int pMinDegree) {
    this.minDegree = pMinDegree;
    this.orderM = this.minDegree * 2 - 1;
    this.root = new BNode();
    this.root.occupiedKeys = 0;
    this.root.isLeaf = true;
  }
  
    public void insert(final int key) {
        // Save current root
        BNode tRoot = this.root;
        // If root is full
        if (tRoot.occupiedKeys == this.orderM) {
            // Make new root
            BNode newBNode = new BNode();
            // Set current root to new root
            this.root = newBNode;
            newBNode.isLeaf = false;
            newBNode.occupiedKeys = 0;
            // Set old root as new root's first child
            newBNode.subtrees[0] = tRoot;
            // Split old root
            splitChild(newBNode, 0, tRoot);
            // Insert key into new root
            insertUnfilled(newBNode, key);
        } 
        else {
            // Insert key into current root
            insertUnfilled(tRoot, key);
        }
    }
  
    private void insertUnfilled(BNode bNode, int key) {
        // Initialize index for key traversal
        int index = 0;
        // If the node doesn't have any children
        if (bNode.isLeaf) {
            // Search for position where the insert key is greater or equal to the node key
            for (index = bNode.occupiedKeys - 1; index >= 0 && key < bNode.key[index]; index--)
                // Shift keys to the right
                bNode.key[index + 1] = bNode.key[index];
            // Insert key into position
            bNode.key[index + 1] = key;
            // Increment key count
            bNode.occupiedKeys = bNode.occupiedKeys + 1;
        }
        // If the node has children
        else {
            // Search for position where the insert key is greater or equal to the node key
            for (index = bNode.occupiedKeys - 1; index >= 0 && key < bNode.key[index]; index--){
            } 
            // Child position for key isnert
            index++;
            // Get child
            BNode child = bNode.subtrees[index];
            // If child is full
            if (child.occupiedKeys == this.orderM) {
                // Split the child
                splitChild(bNode, index, child);
                // If new key at index is lower or equal to the insert key
                if (key > bNode.key[index]) {
                    // Change the index to the correct position
                    index++;
                }
            }
            // Insert key into child at correct position
            insertUnfilled(bNode.subtrees[index], key);
        }
    }

    private void splitChild(BNode bNode, int nodeIndex, BNode mainChild) {
        // Create new node as new child for bNode
        BNode newBNode = new BNode();
        newBNode.isLeaf = mainChild.isLeaf;
        // New node will have minDegree-1 of mainChilds keys
        newBNode.occupiedKeys = this.minDegree - 1;
        // Pass left keys from mainChild to the new node (excluding minDegree-1)
        for (int keyIndex = 0; keyIndex < this.minDegree - 1; keyIndex++) {
            newBNode.key[keyIndex] = mainChild.key[keyIndex + this.minDegree];
        }
        // If mainChild has children
        if (!mainChild.isLeaf) {
            // Pass mainChild's left children to newBNode
            for (int childIndex = 0; childIndex < this.minDegree; childIndex++) {
                newBNode.subtrees[childIndex] = mainChild.subtrees[childIndex + this.minDegree];
            }
        }
        // mainChild now has minDegree-1 of their original keys
        mainChild.occupiedKeys = this.minDegree - 1;
        // Shift bNode's children right, starting right of mainChild's position
        for (int childIndex = bNode.occupiedKeys; childIndex >= nodeIndex + 1; childIndex--) {
            bNode.subtrees[childIndex + 1] = bNode.subtrees[childIndex];
        }
        // Insert the new node into bNode's subtrees
        bNode.subtrees[nodeIndex + 1] = newBNode;
        // Shift bNode's keys to the right, in order to insert mainChild's leftover key
        for (int keyIndex = bNode.occupiedKeys - 1; keyIndex >= nodeIndex; keyIndex--) {
            bNode.key[keyIndex + 1] = bNode.key[keyIndex];
        }
        // Insert mainChild's leftover key into bNode
        bNode.key[nodeIndex] = mainChild.key[minDegree - 1];
        // Increase bNode's key count
        bNode.occupiedKeys = bNode.occupiedKeys + 1;
    }
    private void printTree(BNode bNode, int height) {
        assert (bNode == null);
        System.out.print("Node Height - " + height + " | Key Count - " + bNode.occupiedKeys + "\n");
        for (int index = 0; index < bNode.occupiedKeys; index++) {
            System.out.print(bNode.key[index] + " ");
        }
        System.out.println();
        if (!bNode.isLeaf) {
            for (int index = 0; index < bNode.occupiedKeys + 1; index++) {
                this.printTree(bNode.subtrees[index],height+1);
            }
        }
    }
    public void print() {
        this.printTree(this.root, 0);
    }

    public static void main(String[] args) {
        BTree b = new BTree(3);
        for(int index = 0;index<21;index++){
            b.insert(index);
        }
        b.print();
    }
}