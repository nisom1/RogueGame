package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class EmptyPack extends CreatureAction{
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();

    public EmptyPack(String name, String type, Creature owner){
        super(name, type, owner);
    }

    public void run(){
        // this code isn't tested
        // what should happen: the same as DropPack, except items are dropped repeatedly until the pack is empty.
        int i = 0;
        for (i = 0; i < player.getPack().size(); i++) {
            player.dropItem(i, player.getPosX(), player.getPosY()); // drop the 1st item in the pack (index 0)
        }
        displayGrid.displayInfo("Entire pack was emptied."); // print the actionMessage to grid
    }

}