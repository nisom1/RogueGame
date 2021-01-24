package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// import java.util.Random.nextInt;



public class Teleport extends CreatureAction{
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;
    public ObjectDisplayGrid displayGrid = getGrid();

    public Teleport(String name, String type, Creature owner){
        super(name, type, owner);
    }

    public void teleportFrom(int x , int y){
        int legal = 0;
        int legalX;
        int legalY;
        int[] position = new int[2]; // legal (x, y) for the monster to teleport to
        int minX = 0; // depends on display grid
        int maxX = displayGrid.getWidth()-2;
        int minY = 5 ;
        int maxY = displayGrid.getHeight() - 5 ; // don't want the monster relocating over the Pack / Info lines

        Random random = new Random(); // int value = random.nextInt(max - min) + min;
        
        while(legal == 0){
            // generate a new (x,y) position
            position[0] = random.nextInt(maxX - minX) + minX;  // random x position
            position[1] = random.nextInt(maxY - minY) + minY;  // random y position
            char charAtSpot = displayGrid.getObjectFromDisplay( position[0], position[1] );
            if ( (charAtSpot == '.') | (charAtSpot == '#') | (charAtSpot == '?') | (charAtSpot == ']') | (charAtSpot == ')') ) {
                legal = 1;
            }
            else {
                legal = 0;
            }
        }

        // Now that we have a legal position (x,y) from the monster to move to:
        legalX = position[0]; 
        legalY = position[1];

        // remove the monster from it's current position
        displayGrid.removeObjectFromDisplay(x, y);

        // update/set the monster's NEW postion
        owner.setPosX(legalX);
        owner.setPosY(legalY);

        // move the monster on the grid
        displayGrid.addObjectToDisplay( owner , owner.getPosX() , owner.getPosY() );
    }

    public void run(){
        // this code runs when a player is hit
        // what should happen: causes the creature to go to a random place in the dungeon that is legal for a creature to be,
        // i.e., within a room, '.' or a passageway between rooms, '#' (confirm) ??
        // A creature must end up in a part of the dungeon that it is legal for a player to move to.
        teleportFrom(owner.getPosX(), owner.getPosY());
        displayGrid.displayInfo(actionMessage);
    }
}