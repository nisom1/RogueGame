package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// if it exists in the parent, don't redefine

public class Displayable { // am i supposed to define attributes here AND within the derived classes ?
    protected String name;
    protected int serial;
    protected int room;
    protected int maxHit; 
    protected int hp; 
    protected int hpMoves; 
    protected char type; 
    protected char characterOnScreen;
    
    protected int intValue; 
    protected char charValue; 
    
    protected int posX; 
    protected int posY; 
    protected int width; 
    protected int height;  

    protected int itemIntValue;
    
    public Displayable(){
    }
    public void draw(ObjectDisplayGrid _objectGrid){
        // each displayable can override this 
    }
    public void setInvisible(){
        // 
    } 
    public void setVisible(){
        // 
    } 
    public void setName(String _name){
        name = _name;
    }
    public void setSerial(int _serial){
        serial = _serial;
    }
    public void setMaxHit(int _maxHit){
        maxHit = _maxHit;
    } 
    public void setHp(int _hp){
        hp = _hp;
    }
    public void setHpMoves(int _hpMoves){
        hpMoves = _hpMoves;
    } 
    public void setType(char _type){
        type = _type;
    } 
    public void setIntValue(int _intValue){
        intValue = _intValue;
    } 
    
    public void setCharValue(char _charValue){
        charValue = _charValue;
    } 
    
    public void setPosX(int _posX){
        posX = _posX;
    } 
    public void setPosY(int _posY){
        posY = _posY;
    } 

    public void setWidth(int _width){
        width = _width;
    } 
    public void setHeight(int _height){
        height = _height;
    } 
    public void setRoom(int _room){
        room = _room;
    }
    public void setItemIntValue(int _itemIntValue){
        itemIntValue = _itemIntValue;
    }

    public int getRoom(){
        return room;
    }
    public char getCharacter(){ 
        return characterOnScreen;
    } 
    public int getPosX(){ 
        return posX;
    } 
    public int getPosY(){ 
        return posY;
    } 
    public int getWidth(){
        return width;
    } 
    public int getHeight(){
        return height;
    } 
    public int getMaxHit(){
        return maxHit;
    } 
    public int getHp(){
        return hp;
    } 
    public String getName(){
        return name;
    }
    public int getSerial(){
        return serial;
    }

    public ObjectDisplayGrid getGrid() {
        return ObjectDisplayGrid.getObjectDisplayGrid(-1, -1);
    }
    public void addCreatureAction(CreatureAction creatureAction){
    }  
}

  // @Override
    // public String toString( ) {
    //     String str = "Displayable type \n";        
    //     return str;
    // } 