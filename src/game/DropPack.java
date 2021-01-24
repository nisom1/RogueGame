package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class DropPack extends CreatureAction{
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();
    
    public DropPack(String name, String type, Creature owner){
        super(name, type, owner);
    }

    public void run(){
        // this code runs when a player is hit
        // what should happen: drop the item in position 0 of the pack container.
        // The action of dropping is the same as the drop command above, under Commands.
        // If the pack is empty this does nothing.
        if (player.getPack().size() >= 1) {
            player.dropItem(1, player.getPosX(), player.getPosY()); // drop the 1st item in the pack (index 0)
            displayGrid.displayInfo(actionMessage); // print the actionMessage to grid
        }
    }

}