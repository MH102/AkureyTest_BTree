/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mh.akurey_btree;

import java.util.ArrayList; 
import mh.akurey_btree.BTree.BNode;

/**
 *
 * @author Aozhen
 */
public class BMatrixCell {
    public ArrayList cellArr;
    public int key;
    public int type;
    public BMatrixCell(int pType){
        this.type = pType;
        switch (pType) {
            // If we are storing subtrees
            case 1 -> cellArr = new ArrayList<BNode>();
            // If we are storing key brothers
            case 2 -> cellArr = new ArrayList<Integer>();
            // If we are storing a node key
            default -> this.type = 0;
        }    
    }

    public void insertCellObject(BNode bNode){
        this.cellArr.add(bNode);
    }
    
    public void insertCellObject(int pVal){
        this.cellArr.add(pVal);
    }
    
    public void setKeyValue(int pVal){
        this.key = pVal;
    }
    
    public void print(){
        switch(this.type){
            case 1 -> {
                System.out.print("Left Tree Keys: ");
                if(cellArr.get(0)!= null){
                    ((BNode)cellArr.get(0)).print();
                }
                System.out.println();
                System.out.print("Right Tree Keys: ");
                if(cellArr.get(1)!= null){
                    ((BNode)cellArr.get(1)).print();
                }
                System.out.println();
            }
            case 2 -> {
                System.out.print("Key Brothers: ");
                for(int index=0; index < cellArr.size();index++){
                    System.out.print(cellArr.get(index) + " ");
                }
                System.out.println();
            }
            default -> System.out.println("Key: " + this.key);
        }
    }
    
    
}
