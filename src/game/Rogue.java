package game;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
// import src.*;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.SAXException;

public class Rogue implements Runnable {

    private static final int DEBUG = 0;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid;
    private Thread keyStrokePrinter;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static Player player;

    public Rogue(int width, int height) {
    }

    @Override
    public void run() { // how does "run()" get the displayables arrayList ???
        //displayGrid.fireUp();
        // displayGrid.clearDisplay();
        
        // ArrayList<Displayable> displayables = handler.getDisplayables(); // get the list passed in
        // for(int i = 0; i < displayables.size(); i++){
        //     Displayable element = displayables.get(i);
        //     element.draw(displayGrid); // what is display grid ??
        // }
    }

    public static void main(String[] args) throws Exception {

        // 1: Get Dungeon filled with objects (from XML file) (parse XML file)
        // 2: Create Grid (test stuff)
        // 3: Clear Grid (clearGrid())
        // 4: Print objects from the Dungeon ONTO the grid (draw function)  
        
        
        // 1: Get Dungeon filled with objects (from XML file) (parse XML file)
        // check if a filename is passed in.  If not, print a usage message.
        // If it is, open the file
        String fileName = null;
        switch (args.length) {
            case 0: fileName = "xmlfiles/testDrawing.xml";
                break;
            case 1:
                fileName = "xmlfiles/" + args[0]; // file path may depend on what IDE you are
                break;
            
            default:
                System.out.println("java Test <xmlfilename>"); 
            return;
        }

        // Create a saxParserFactory, that will allow use to create a parser
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try { // am i supposed to do everything from here ??
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DungeonXMLHandler handler = new DungeonXMLHandler();
            saxParser.parse(new File(fileName), handler); // This will parse the xml file given by fileName
            Dungeon dungeon = handler.getDungeon();
            ArrayList<Displayable> displayables = handler.getDisplayables();
            Player player = handler.getPlayer();
            // verifying if the displayables list even exists
        
            // after this, we should have a complete dungeon object

            // 1: Create Grid (test stuff)
            int height = handler.getTotalHeight();
            int width = dungeon.getWidth();
            
            displayGrid = ObjectDisplayGrid.getObjectDisplayGrid(width, height);
            // Rogue test = new Rogue(width, height);
            Thread.sleep(1000);
            // add elements to grid
            ArrayList<Room> rooms = dungeon.getRooms();
            ArrayList<Passage> passages = dungeon.getPassages();
            ArrayList<Creature> creatures = dungeon.getCreatures();
            ArrayList<Item> items = dungeon.getItems(); // when we add items to this array, are we adding Displayables? or just Chars ?
            
            // print rooms
            for(int i = 0; i < rooms.size(); i++){
                rooms.get(i).draw(displayGrid);
            }
            // print passages
            for(int i = 0; i < passages.size(); i++){
                passages.get(i).draw(displayGrid);
            }
            // print items
            for(int i = 0; i < items.size(); i++){
                // items.get(i).draw(displayGrid);
                displayGrid.addObjectToDisplay(items.get(i), items.get(i).getPosX(),items.get(i).getPosY() );
            }
            // print creatures
            for(int i = 0; i < creatures.size(); i++){
                // creatures.get(i).draw(displayGrid);
                // Creature creature = creatures.get(i);
                displayGrid.addObjectToDisplay(creatures.get(i), creatures.get(i).getPosX(),creatures.get(i).getPosY() );
            }
            int playerHp = player.getHp();
            displayGrid.displayHP(playerHp);
            displayGrid.updatePackLine(player.getPack());

            KeyStrokePrinter keyStrokePrinter = new KeyStrokePrinter(displayGrid);
            keyStrokePrinter.run();

            // Thread testThread = new Thread(test);
            // testThread.start();

            // test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
            // test.keyStrokePrinter.start();

            // testThread.join();
            // test.keyStrokePrinter.join();
        }
        catch (ParserConfigurationException | SAXException | IOException e ) {
            e.printStackTrace(System.out);
            // e.printStackTrace(System.err);
        }
    }
}

// old way: for printing objects
            // for(int i = 0; i < displayables.size(); i++){
            //     // Displayable element = displayables.get(i);
            //     (displayables.get(i)).draw(displayGrid);
            // }