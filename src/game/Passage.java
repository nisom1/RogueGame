package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Passage extends Structure{
    // public String name;
    public int room1;
    public int room2;
    private char charOnScreen = '#';

    private ArrayList<Integer> posX = new ArrayList<Integer>( );
    private ArrayList<Integer> posY = new ArrayList<Integer>( );

    
    public Passage(String _name, int _room1, int _room2){
        name = _name;
        room1 = _room1;
        room2 = _room2;
    } 
    public void setName(String _name){
        name = _name;
    } 
    public int getRoom1() {
        return room1;
    }
    public int getRoom2() {
        return room2;
    }
    public char getCharacter(){ 
        return charOnScreen;
    } 

    @Override
    public void setPosX(int _posX){ // more like "addPosX"
        posX.add(_posX); // add coordinate to the list
    } 

    @Override
    public void setPosY(int _posY){ // more like "addPosY"
        posY.add(_posY);
    }

    @Override // passages and room dont' ahve characteristics, so it's fine to push them onto the stack as a constant Char class, and not a displayable
    public void draw(ObjectDisplayGrid objectGrid) { // draw passage  
        Char ch = new Char('#'); // X's on the border
        int x1;
        int x2;
        int y1;
        int y2;
        int i = 0;

        for (i = 0; i < posX.size()-1; i++){
            // you can't index a non-existent element in java
            // you have to use a for loop, and to the exact, right/correct, number of iterations
            // recalculate / update the indexes
            x1 = posX.get(i);
            x2 = posX.get(i+1);
            y1 = posY.get(i);
            y2 = posY.get(i+1);

            // Build in X-Direction   
            // condition 1: y1 == y2
            if(y1 == y2){ 

                // build left to right
                // condition 2: x1 < x2
                if(x1 <= x2) {
                    for(int x = x1; x <= x2; x++) {
                        objectGrid.addObjectToDisplay(ch, x, y1); // constant Y height
                    }
                }
                
                // build right to left
                // condition 2: x1 > x2
                else if(x1 > x2) {
                    for(int x = x1; x >= x2; x--) {
                        objectGrid.addObjectToDisplay(ch, x, y1); // constant Y height
                    }
                }
            }

            // Build in Y-Direction
            // condition 1: x1 == x2
            else if(x1 == x2) {
                
                // build high to low
                // condition 2: y1 < y2
                if(y1 <= y2) {
                    for(int y = y1; y <= y2; y++) {
                        objectGrid.addObjectToDisplay(ch, x1, y); // constant Y height
                    }
                }

                // build low to high
                // condition 2: y1 > y2
                else if(y1 > y2) {
                    for(int y = y1; y >= y2; y--) {
                        objectGrid.addObjectToDisplay(ch, x1, y); // constant Y height
                    }
                }
            }
        } // end of for loop (all the indicies in the pos-arrays have been passed)
    

        // add doorways
        Char door = new Char('+'); // doorway
        int firstX = posX.get(0);
        int firstY = posY.get(0);
        // get the last element in the arrayList
        int lastIndex = posX.size() - 1;
        int lastX = posX.get(lastIndex);
        int lastY = posY.get(lastIndex);

        objectGrid.addObjectToDisplay(door, firstX, firstY);
        objectGrid.addObjectToDisplay(door, lastX, lastY); 
    
    } // end of draw
    
}