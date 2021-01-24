package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class UpdateDisplay extends CreatureAction{
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();

    public UpdateDisplay(String name, String type, Creature owner){
        super(name, type, owner);
    }
    public void run(){
        // causes the creature to update the display of itself.
        // ??        
        // If the creature is the player, the update the HP at the top
        if (owner instanceof Player){
            displayGrid.displayHP(player.getHp()); // hp has been properly updated
        }
 
    }
}