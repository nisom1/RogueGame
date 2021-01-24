package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Remove extends CreatureAction{
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();
    
    public Remove(String name, String type, Creature owner){
        super(name, type, owner);
    }

    public void run(){
        // this code runs when a player is hit
        // what should happen: removes the creature from the display.
        // Primarily used to remove a dead monster from the objectGrid and the terminal.
        displayGrid.removeObjectFromDisplay(owner.getPosX(), owner.getPosY()); // the player
    }

}