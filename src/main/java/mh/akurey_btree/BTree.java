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
    int height;
    int keys[] = new int[orderM];
    BNode subtrees[] = new BNode[orderM+1];
    boolean isLeaf = true;
    
    public void print(){
        for(int index = 0; index < this.occupiedKeys;index++)
            System.out.print(this.keys[index]+" ");
    }
    
  }

  public BTree(int pMinDegree) {
    this.minDegree = pMinDegree;
    this.orderM = this.minDegree * 2 - 1;
    this.root = new BNode();
    this.root.occupiedKeys = 0;
    this.root.height = 0;
    this.height = 0;
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
            tRoot.height = 1;
            newBNode.height = 0;
            // Split old root
            this.splitChild(newBNode, 0, tRoot);
            // Insert key into new root
            this.insertUnfilled(newBNode, key);
        } 
        else {
            // Insert key into current root
            this.insertUnfilled(tRoot, key);
        }
        this.count++;
    }
  
    private void insertUnfilled(BNode bNode, int key) {
        // Initialize index for key traversal
        int index = 0;
        // If the node doesn't have any children
        if (bNode.isLeaf) {
            // Search for position where the insert key is greater or equal to the node key
            for (index = bNode.occupiedKeys - 1; index >= 0 && key < bNode.keys[index]; index--)
                // Shift keys to the right
                bNode.keys[index + 1] = bNode.keys[index];
            // Insert key into position
            bNode.keys[index + 1] = key;
            // Increment key count
            bNode.occupiedKeys = bNode.occupiedKeys + 1;
        }
        // If the node has children
        else {
            // Search for position where the insert key is greater or equal to the node key
            for (index = bNode.occupiedKeys - 1; index >= 0 && key < bNode.keys[index]; index--){
            } 
            // Child position for key isnert
            index++;
            // Get child
            BNode child = bNode.subtrees[index];
            // If child is full
            if (child.occupiedKeys == this.orderM) {
                // Update height
                // Split the child
                this.splitChild(bNode, index, child);
                // If new key at index is lower or equal to the insert key
                if (key > bNode.keys[index]) {
                    // Change the index to the correct position
                    index++;
                }
            }
            // Insert key into child at correct position
            this.insertUnfilled(bNode.subtrees[index], key);
        }
    }

    private void splitChild(BNode bNode, int nodeIndex, BNode mainChild) {
        // Create new node as new child for bNode
        BNode newBNode = new BNode();
        newBNode.height = bNode.height+1;
        if(newBNode.height>this.height){
            this.height = newBNode.height;
        }
        newBNode.isLeaf = mainChild.isLeaf;
        // New node will have minDegree-1 of mainChilds keys
        newBNode.occupiedKeys = this.minDegree - 1;
        // Pass left keys from mainChild to the new node (excluding minDegree-1)
        for (int keyIndex = 0; keyIndex < this.minDegree - 1; keyIndex++) {
            newBNode.keys[keyIndex] = mainChild.keys[keyIndex + this.minDegree];
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
            bNode.keys[keyIndex + 1] = bNode.keys[keyIndex];
        }
        // Insert mainChild's leftover key into bNode
        bNode.keys[nodeIndex] = mainChild.keys[minDegree - 1];
        // Increase bNode's key count
        bNode.occupiedKeys = bNode.occupiedKeys + 1;
    }
    
    private void printTree(BNode bNode, int height) {
        if(bNode==null){
            return;
        }
        System.out.print("Node Height - " + height + " | Key Count - " + bNode.occupiedKeys + "\n");
        for (int index = 0; index < bNode.occupiedKeys; index++) {
            System.out.print(bNode.keys[index] + " ");
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
    
    public BMatrix toMatrix(){
        BMatrix matrix = new BMatrix(this.count);
        matrix.generate(this.root);
        return matrix;
    }

    public static void main(String[] args) {
        BTree bTree = new BTree(2);
        for(int index = 0;index<11;index++){
            bTree.insert(index);
        }
        // bTree.print();
        BMatrix bMatrix = bTree.toMatrix();
        //bMatrix.print();
        bMatrix.queryKey(5);
        bMatrix.queryKey(7);
    }
}