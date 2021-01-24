package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// protected: if the attribute is already in the parent, DO NOT REPEAT

public class Action {
    protected String name; // set in the start tag (displayable method)
    protected String type;
    protected Creature owner;

    protected String actionMessage; // set in the end tag
    protected int actionIntValue;
    protected char actionCharValue;

    public ObjectDisplayGrid getGrid() {
        return ObjectDisplayGrid.getObjectDisplayGrid(-1, -1);
    }

    public Action(){ 
    }

    public void setName(String _name){
        name = _name;
    }

    public void setType(String _type){
        type = _type;
    } 

    public void setOwner(Creature _owner){
        owner = _owner;
    } 

    public void setMessage(String _actionMessage){
        actionMessage = _actionMessage;
    }

    public void setIntValue(int _actionIntValue){
        actionIntValue = _actionIntValue;
    }

    public void setCharValue(char _actionCharValue){
        actionCharValue = _actionCharValue;
    }    

    // getters
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public Creature getOwner(){
        return owner;
    }
    public String getMessage(){
        return actionMessage;
    }
    public int getActionIntValue(){
        return actionIntValue;
    }
    public char getCharValue(){
        return actionCharValue;
    }  
       
}