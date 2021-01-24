package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Armor extends Item{
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED
    
    // private String name;
    // private int room;
    // private int serial;
    private char charOnScreen = ']';

    // private int posX; // position of scroll
    // private int posY;
    // private int itemIntValue; 
    // public Creature owner; 

    public Armor(String _name){
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

    public int getItemIntValue(){ // protectino that the sword offers ??
        return itemIntValue;
    }

    public char getCharacter(){ 
        return charOnScreen;
    } 

    @Override // the one in displayable. i don't even use this one
    // draws armor on the objectGrid
    public void draw(ObjectDisplayGrid objectGrid){ // how to properly use objectDisplayGrid in here
        Char armor = new Char(']'); // char representing a Scroll
        objectGrid.addObjectToDisplay(armor, posX, posY); // don't fix what isn't broken
        // should be armor though ??
    }
}