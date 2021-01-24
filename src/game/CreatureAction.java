package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// if the attribute is already in the parent, DO NOT REPEAT

public class CreatureAction extends Action{
    
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;

    public CreatureAction(String _name, String _type, Creature _owner){ // for creatureAction
        name = _name;
        type = _type;
        owner = _owner;
    }

    public void run(){
        // will be overidden by derived classes
    }


    // types of creature actions:
    // hit
    // death
}