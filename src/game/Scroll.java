package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Scroll extends Item{
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED

    // private String name;
    // private int room;
    // private int serial;
    private char charOnScreen = '?';


    public ArrayList<ItemAction> itemActions = new ArrayList<ItemAction>( );
    // Hallucinate and BlessCursedOwner objects

    // private int posX; // set in the end tag
    // private int posY;
    // private Creature owner; // how to use this ??
    ObjectDisplayGrid displayGrid = getGrid();
    Player player = Player.getPlayer();
    
    public Scroll(String _name){
        super();
        // sets owner = _owner
        // sets posX = _posX
        // sets posY = _posY
        // sets itemIntValue = _itemIntValue ... which is doesn't even have
        name = _name; 

    }
    public void setID(int _room, int _serial){
        room = _room; 
        serial = _serial; 
    } 
    
    // @Override // because scroll doesn't have this
    public void setIntValue(){
    }

    public void setItemIntValue(int _itemIntValue){
    }

    public char getCharacter(){ 
        return charOnScreen;
    }

    @Override
    // scroll adding to board. Used this for step 2, but now I push the whole Scroll-Displayable onto the stack. Not just the Char '?'
    public void draw(ObjectDisplayGrid objectGrid){ // how to properly use objectDisplayGrid in here
        Char scroll = new Char('?'); // char representing a Scroll
        objectGrid.addObjectToDisplay(scroll, posX, posY);
    }

    public void addItemAction(ItemAction itemAction){
        itemActions.add(itemAction);
    }


    // Call scroll.executeItemActions()
    public void executeItemActions(){ // execute the item actions of the scroll when it is read 'r' by the player
        int length = itemActions.size();
        int i = 0;
        for (i=0; i<length; i++){
            // System.out.println( "Scroll Actions ["+i+"] :  "+ itemActions.get(i).getName() );
            itemActions.get(i).run();
        }
    }
}
