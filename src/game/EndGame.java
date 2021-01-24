package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class EndGame extends CreatureAction{
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();

    public EndGame(String name, String type, Creature owner){
        super(name, type, owner);
    }

    public void run(){
        // this code runs when a player is hit
        // what should happen: causes the game to be ended and all further input to be ignored.
        // Set a static flag in the Player that signals the end of the game (player flag is read in KeyStroke class/file)
        displayGrid.displayInfo(actionMessage);
        player.setEndGameChar(1); // when EndGameChar == 1, the game is over
    }
}