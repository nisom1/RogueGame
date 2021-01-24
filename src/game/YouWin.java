package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class YouWin extends CreatureAction{
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;
    public ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();

    public YouWin(String name, String type, Creature owner){
        super(name, type, owner);
    }

    public void run(){
        // this code runs when a player is hit
        // what should happen: This is typically executed by the creature that is killed.
        // We will print out the message given in the actionMessage element of the XML definition of the action.
        displayGrid.displayInfo(actionMessage);

        // It updates the player’s points. ???
        // int currentHp = player.getHp();

    }
}