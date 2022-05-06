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
    private BMatrixObject[][] bMatrix;
    private int columns;
    private int rows;
    private int colCount;
    
    
    public class BMatrixObject {
        public int value;
        public boolean exists;
    
        public BMatrixObject(){
            this.exists = false;
        }
    
        public BMatrixObject(int pVal){
            this.value = pVal;
            this.exists = true;
        }
    
    }
    
    public BMatrix(int pColumns, int pOrderM){
        this.columns = pColumns;
        this.rows = pOrderM;
        this.colCount = 0;
        bMatrix = new BMatrixObject[rows][columns];
    }
    
    public void generate(BNode bNode){
        assert (bNode == null);
        for (int index = 0; index < this.rows; index++) {
            if(index < bNode.occupiedKeys){
                this.bMatrix[index][this.colCount] = new BMatrixObject(bNode.keys[index]);
            }
            else{
                this.bMatrix[index][this.colCount] = new BMatrixObject();
            }
        }
        if (!bNode.isLeaf) {
            for (int index = 0; index < bNode.occupiedKeys + 1; index++) {
                this.colCount++;
                this.generate(bNode.subtrees[index]);
            }
        }
    }
    
    public void print(){
        for(int i = 0; i < this.rows;i++){
            for(int j = 0; j < this.columns; j++){
                if(this.bMatrix[i][j].exists){
                    System.out.print(this.bMatrix[i][j].value + " ");
                }
                else{
                    System.out.print("N ");
                }
            }
            System.out.println();
        }
    }
    
}
