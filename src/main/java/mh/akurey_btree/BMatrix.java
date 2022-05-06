/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mh.akurey_btree;

import mh.akurey_btree.BTree.BNode;

/**
 *
 * @author Aozhen
 */
public class BMatrix {
    private BMatrixCell[][] bMatrix;
    private int columns;
    private int rows;
    private int colCount;
   
    
    
    
    public BMatrix(int pNodeCount){
        // Columns are the amount of keys in the B-Tree
        this.columns = pNodeCount;
        /* Always 3 rows: 
         * first row are the keys, 
         * second row are the left and right subtree for the key, 
         * third row are the key brothers
         */
        this.rows = 3;
        bMatrix = new BMatrixCell[rows][columns];
    }
    
    public void generate(BNode bNode){
        if(bNode==null){
            return;
        }
        // For each key in the current node
        for (int index = 0; index < bNode.occupiedKeys; index++) {
            // Initialize matrix cells
            // cell object containing corresponding node key
            BMatrixCell bMCellKey = new BMatrixCell(0);
            // cell object containing left and right subtrees 
            BMatrixCell bMCellTrees = new BMatrixCell(1);
            // cell object containing key brothers
            BMatrixCell bMCellKeyBrothers = new BMatrixCell(2);
            // End of init
            
            // Set the key cell value to the current node's key
            bMCellKey.setKeyValue(bNode.keys[index]);
           
            // Insert left sub-tree into tree cell
            bMCellTrees.insertCellObject(bNode.subtrees[index]);
            // Insert right sub-tree into tree cell
            bMCellTrees.insertCellObject(bNode.subtrees[index+1]);
            
            // For each key in the current node different from the current key
            for(int sIndex = 0; sIndex < bNode.occupiedKeys; sIndex++){
                if(sIndex!= index){
                    // Insert key brother into key brothers cell
                    bMCellKeyBrothers.insertCellObject(bNode.keys[sIndex]);
                }
            }
            
            // Assign each cell object to corresponding pos in matrix
            this.bMatrix[0][this.colCount] = bMCellKey;
            this.bMatrix[1][this.colCount] = bMCellTrees;
            this.bMatrix[2][this.colCount] = bMCellKeyBrothers;
            // Set to next column for next key
            this.colCount++;
        }
        if (!bNode.isLeaf) {
            // For each sub-tree, generate the corresponding cells
            for (int index = 0; index < bNode.occupiedKeys + 1; index++) {
                // Recursive call towards all sub-trees
                this.generate(bNode.subtrees[index]);
            }
        }
    }
    
    public void queryKey(int pKey){
        
        System.out.println("Query result for key " + pKey + ": ");
        // Searches for pKey on first row
        for(int index = 0; index < this.columns; index++){
            // If the key matches our query
            if(this.bMatrix[0][index].key== pKey){
                // Print results
                this.bMatrix[1][index].print();
                return;
            }
        }
        // If the key doesn't exist
        System.out.println("No such key was found");
    }
    
    public void print(){
        for(int index = 0; index < this.columns;index++){
            System.out.println("COLUMN " + index + "---------");
            this.bMatrix[0][index].print();
            this.bMatrix[1][index].print();
            this.bMatrix[2][index].print();
            System.out.println("------------------");
            
        }
    }
    
}
