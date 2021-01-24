package game;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Room extends Structure{
    
    private int roomID; // room number
    private ArrayList<Creature> creatures = new ArrayList<Creature>( ); // list of creatures in the room
    private ArrayList<Item> items = new ArrayList<Item>( ); // do I need this ??
    

    private char charOnScreen = 'X';
    // private int posX; // top left
    // private int posY; // corner of the room
    // private int width; // amount it extend to the left
    // private int height; // amount it extends down


    public Room(int _roomID){ // room number
        roomID = _roomID;
    }
    public void setID(int _roomID){ // room nubmer... don't really use
        roomID = _roomID;
    } 
    public void setCreature(Creature monster){ // where do i call this / why.
        creatures.add(monster); // theres already an array in Dungeon of creatures ...?
    } 
    public void setItem(Item item){ 
        items.add(item); // is this allowed / proper syntax
    } 
    public char getCharacter(){ 
        return charOnScreen;
    } 
    
    @Override // the one in displayable
    // draws the perimeter of the room
    // corner1  X X X corner2 
    //    X              X
    //    X              X 
    // corner3  X X X corner4 
    // Set up the whole room with dots >> then write X's along the border
    public void draw(ObjectDisplayGrid objectGrid){ // do I call this in Rogue? call "draw" on each object?
        Char dot = new Char('.'); // dots in the room
        Char ch = new Char('X'); // X's on the border
        int x;
        int y;
        // drawing .'s in the whole room
        for (y = posY + 1 ; y < (posY + height); y++) { 
            for (x = posX + 1; x < (posX + width); x++) {
                objectGrid.addObjectToDisplay(new Char('.'), x, y); // i,j is the POSITION where the object should be added
            }
        }

        // drawing X's along the border
        int corner1X = posX;
        int corner1Y = posY;

        int corner2X = corner1X + width - 1;
        int corner2Y = corner1Y; // same Y as corner1

        int corner3X = corner1X; // same X as corner1
        int corner3Y = posY + height - 1;

        int corner4X = corner2X; // same X as corner2
        int corner4Y = corner3Y; // same Y as corner3
        
        // draw from corner1 --> corner2
        for(x = corner1X; x <= corner2X; x++) {
            objectGrid.addObjectToDisplay(ch, x, corner1Y); // constant Y height
        }
        // draw from corner3 --> corner4
        for(x = corner3X; x <= corner4X; x++) {
            objectGrid.addObjectToDisplay(ch, x, corner3Y); // constant Y height
        }
        // draw from corner1 --> corner3
        for(y = corner1Y; y <= corner3Y; y++) {
            objectGrid.addObjectToDisplay(ch, corner1X, y); // constant X height
        }
        // draw from corner2 --> corner4
        for(y = corner2Y; y <= corner4Y; y++) {
            objectGrid.addObjectToDisplay(ch, corner2X, y); // constant X height
        }
    }

}