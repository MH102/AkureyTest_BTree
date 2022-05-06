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
        this.columns = pNodeCount;
        this.rows = 3;
        bMatrix = new BMatrixCell[rows][columns];
    }
    
    public void generate(BNode bNode){
        if(bNode==null){
            return;
        }
        for (int index = 0; index < bNode.occupiedKeys; index++) {
            BMatrixCell bMCellKey = new BMatrixCell(0);
            BMatrixCell bMCellTrees = new BMatrixCell(1);
            BMatrixCell bMCellKeyBrothers = new BMatrixCell(2);
            bMCellKey.setKeyValue(bNode.keys[index]);
           
            bMCellTrees.insertCellObject(bNode.subtrees[index]);
            bMCellTrees.insertCellObject(bNode.subtrees[index+1]);
            
           
            for(int sIndex = 0; sIndex < bNode.occupiedKeys; sIndex++){
                if(sIndex!= index){
                    bMCellKeyBrothers.insertCellObject(bNode.keys[sIndex]);
                }
            }
            this.bMatrix[0][this.colCount] = bMCellKey;
            this.bMatrix[1][this.colCount] = bMCellTrees;
            this.bMatrix[2][this.colCount] = bMCellKeyBrothers;
            this.colCount++;
        }
        if (!bNode.isLeaf) {
            for (int index = 0; index < bNode.occupiedKeys + 1; index++) {
                this.generate(bNode.subtrees[index]);
            }
        }
    }
    
    public void queryKey(int pKey){
        System.out.println("Query result for key " + pKey + ": ");
        for(int index = 0; index < this.columns; index++){
            if(this.bMatrix[0][index].key== pKey){
                this.bMatrix[1][index].print();
                return;
            }
        }
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
