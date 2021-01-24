package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// if the attribute is already in the parent, DO NOT REPEAT

public class ItemAction extends Action{
    
    // Action defines:
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;

    public ItemAction(String _name, String _type){ // for itemAction
        name = _name;
        type = _type;
    }

    public void setType(String _type){
        type = _type;
    } 

    public void run(){  // gets overridden by derived classes
    
    }
    

}