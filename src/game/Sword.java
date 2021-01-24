package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Sword extends Item{
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED
    // private String name;
    // private int room;
    // private int serial;
    private char charOnScreen = ')';

    // private int posX; // send in the end tag
    // private int posY;
    // public int itemIntValue; 
    
    // public Creature owner; 
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();
    

    public Sword(String _name){
        super();
        // sets owner = _owner
        // sets posX = _posX
        // sets posY = _posY
        // sets itemIntValue = _itemIntValue
        name = _name; 
    }
    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
    } 

    public void setItemIntValue(int _itemIntValue){
        itemIntValue = _itemIntValue;
    }

    public int getSerial(){ // set getters in all classes
        return serial;
    }
    public char getCharacter(){ 
        return charOnScreen;
    } 

    @Override 
    // draws Sword on the objectGrid
    // scroll adding to board. Used this for step 2, but now I push the whole Sword-Displayable onto the stack. Not just the Char '?'
    public void draw(ObjectDisplayGrid objectGrid){ // how to properly use objectDisplayGrid in here
        Char sword = new Char(')'); // char representing a Scroll
        objectGrid.addObjectToDisplay(sword, posX, posY);
    }
}