package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class ChangeDisplayedType extends CreatureAction{
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;
    ObjectDisplayGrid displayGrid = getGrid();
    
    public ChangeDisplayedType(String name, String type, Creature owner){
        super(name, type, owner);
        // i think the actionCharValue is already set by the Action super class
    }

    public void changeChar(int x, int y) {
        Displayable newChar = new Char(actionCharValue);
        displayGrid.addObjectToDisplay(newChar, x, y); // just push the newChar on the top of the player ... the game is over
        // just push the actionCharInt on top of it
    }

    public void run(){
        // this code runs when a player is hit
        // what should happen: changes the character that represents the creature.
        // The new character is specified by the actionCharValue element in the ChangeDisplayType XML description.
        // Used primarily in a death action for the Player to change what a dead player looks like.
        changeChar(owner.getPosX(), owner.getPosY()); // the player
    }
}