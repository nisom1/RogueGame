package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Monster extends Creature{
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED

    // private int posX;
    // private int posY;
    // private int hp;
    // private char type; // == T (troll), S (snake), H (hobglobin) // Defined in Main
    // private int maxHit;

    // private CreatureAction deathAction;
    // private CreatureAction hitAction;
    
    // private String name;
    private int room; // room number that the object is in 
    private int serial;

    public Monster(){
        super(); // Displayable sets posX, posY, hp, type, maxHit
        // Creature deathAction, hitAction
    }

    public void setName(String _name){
        name = _name;
    } 
    public void setType(char _type){
        type = _type;
    } 
    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
    } 
    
    // getters
    public char getType(){
        return type;
    } 

    // @Override // because monster doesn't have this
    public void setHpMoves(){
    }

    public char getCharacter(){ 
        return type;
    } 

    @Override 
    // Print the char TYPE of the monster, to the grid, don't use this to add monsters to display
    public void draw(ObjectDisplayGrid objectGrid){ // how to properly use objectDisplayGrid in here
        Char monsterLetter = new Char(type); // type representing the Mosnter
        objectGrid.addObjectToDisplay(monsterLetter, posX, posY);
    }

}