package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Dungeon{
    
    private String name;
    private int width;
    private int gameHeight;
    
    private int topHeight; // of object display grid
    private int bottomHeight; // of object display grid

    private ArrayList<Room> rooms = new ArrayList<Room>( ); // list of creatures in the room
    private ArrayList<Passage> passages = new ArrayList<Passage>( ); // list of creatures in the room
    private ArrayList<Creature> creatures = new ArrayList<Creature>( ); // list of creatures in the room
    private ArrayList<Item> items = new ArrayList<Item>( ); // do I need this ??
    private static Dungeon instance;

    private Dungeon(String _name, int _width, int _gameHeight){
        name = _name;
        width = _width;
        gameHeight = _gameHeight;
    }
    public static Dungeon getDungeon(String _name, int _width, int _gameHeight){
        if (instance == null) {
            instance = new Dungeon(_name, _width, _gameHeight);
        }
        return instance;
    }

    // setters
    public void addRoom(Room room){
        rooms.add(room);
    }
    public void addCreature(Creature creature){
        creatures.add(creature);
    }
    public void addPassage(Passage passage){
        passages.add(passage);
    }
    public void addItem(Item item){
        items.add(item);
    }
    
    // getters
    public ArrayList<Room> getRooms(){
        // System.out.println();
        return rooms;
    }
    public ArrayList<Creature> getCreatures(){
        return creatures;
    }
    public ArrayList<Passage> getPassages(){
        return passages;
    }
    public ArrayList<Item> getItems(){
        return items;
    }

    public String getName(){
        return name;
    }

    public int getWidth(){
        return width;
    }

    public int getGameHeight(){
        return gameHeight;
    } 
    public int getTopHeight(){
        return topHeight;
    } 
    public int getBottomHeight(){
        return bottomHeight;
    }    
}