package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// owner will always be the player!

public class BlessCurseOwner extends ItemAction{
    
    // Action defines:
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;

    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();
    
    public BlessCurseOwner(String name, String type){
         super(name, type);
        // System.out.println("BlessCurseOwner");
    }


    @Override
    // The actionCharValue in the XML definition of the action will be ‘a’ if it affects the armor being worn
    // and a ‘w’ if it affects the sword, or weapon being worn.
    // If the item to be affected is not being worn or wielded, the scroll has no effect.
        
    // Print a message to the bottom message area indicating that the scroll was blessed or cursed.
    // I print the message: 
    // --- scroll of cursing does nothing because " + name + " not being used ---(when the object is not worn or wielded)
    // and --- name + " cursed! " + intValue + " taken from its effectiveness ---
    //  where name is the name of the item.
    public void run(){ // do i need to define this in item action
        String message = "";
        // This action is associated with scroll.
        
        // It reduces the effectiveness of a sword or armor that is being WORN or WIELDED.
        // AFFECTS THE ITEMINTVALUE. reduces it by the scroll's ACTIONINTVALUE
        // ok but does this update the pack?? i think so 
        if (actionCharValue == 'w') { // hits SWORDS (wield)
            if ( player.getSword() != null ){ // if player HAS a sword, and the scroll HITS swords
                message += "Sword has been cursed! " + actionIntValue + " taken from its effectiveness";
                int currentValue = player.getSword().getItemIntValue();
                player.getSword().setItemIntValue(currentValue + actionIntValue);
            }
            else{ // if player does NOT a sword, and the scroll HITS swords
                message += "Scroll of cursing does nothing because no Sword is wielded";
            }
            displayGrid.displayInfo(message);
        }

        else if (actionCharValue == 'a'){ // hits ARMOR
            if ( player.getArmor() != null ){ // if player HAS armor, and the scroll HITS armor
                message += "Armor has been cursed! " + actionIntValue + " taken from its effectiveness";
                int currentValue = player.getArmor().getItemIntValue();
                player.getArmor().setItemIntValue(currentValue + actionIntValue);
            }
            else { 
                message += "Scroll of cursing does nothing because no Armor is worn";
            } 
            displayGrid.displayInfo(message);
        }

        else {
            System.out.println("Don't regnize the BlessCursed's actionCharValue. not a 'w or 'a' ");
        }
    }
}