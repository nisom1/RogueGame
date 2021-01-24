package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Item extends Displayable {
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED

    // private String name;
    protected int room;
    protected int serial;

    // private int posX; 
    // private int posY;
    // private int itemIntValue; 

    protected Creature owner;
    protected int isUsed = 0;

    public Item(){
        super(); // sets name, posX, posY, itemIntValue
    }
    public void setOwner(Creature _owner){
        owner = _owner;
    }  

    public void setItemIntValue(int _itemIntValue){
        itemIntValue = _itemIntValue;
    }

    public void setRoom(int _room){
        room = _room;
    }

    public void setSerial(int _serial){
        serial = _serial;
    }

    public void addItemAction(ItemAction itemAction){
    }

    public int getItemIntValue(){
        return itemIntValue;
    }

    public int getSerial(){
        return serial;
    }

// means that this item is being used/worn
    public void setIsUsed(int num){ // armor.setIsUsed(1) . means that this item is being used/worn
        isUsed = num;
    }

    public int getIsUsed(){ // armor.setIsUsed(1) . means that this item is being used/worn
        return isUsed;
    }

}