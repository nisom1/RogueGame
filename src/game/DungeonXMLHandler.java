package game;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
// import src.*;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class DungeonXMLHandler extends DefaultHandler {

    // the two lines that follow declare a DEBUG flag to control
    // debug print statements and to allow the class to be easily
    // printed out.  These are not necessary for the parser.
    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";

    // data can be called anything, but it is the variables that
    // contains information found while parsing the xml file
    private StringBuilder data = null;

    private Dungeon dungeon = null;
    private ArrayList<Displayable> displayables = new ArrayList<Displayable>( );
    private Player player = null;
    
    private ObjectDisplayGrid objectDisplayGrid = null;
    private int topHeight;
   // private Action action = null;


    private Dungeon dungeonBeingParsed = null;
    private Structure structureBeingParsed = null;    
    private Item itemBeingParsed = null;
    private Creature creatureBeingParsed = null;
    private Action actionBeingParsed = null;

    // The bX fields here indicate that at corresponding field is
    // having a value defined in the XML file.  In particular, a
    // line in the xml file might be:
    // <instructor>Brook Parke</instructor> 
    // The startElement method (below) is called when <instructor>
    // is seen, and there we would set bInstructor.  The endElement
    // method (below) is called when </instructor> is found, and
    // in that code we check if bInstructor is set.  If it is,
    // we can extract a string representing the instructor name 
    // from the data variable above.
    public boolean bVisible = false; // false means we haven't entered the tag
    public boolean bPosX = false;
    public boolean bPosY = false;
    public boolean bWidth = false;
    public boolean bHeight = false;

    public boolean bActionMessage = false;
    public boolean bActionIntValue = false;
    public boolean bActionCharValue = false;

    public boolean bHp = false;
    public boolean bHpMoves = false;
    public boolean bMaxHit = false;

    public boolean bItemIntValue = false;
    public boolean bType = false;


    // Used by code outside the class to get the list of Student objects
    // that have been constructed.
    // public Dungeon[] getDungeon() {
    //     return dungeon;
    // }
    public Dungeon getDungeon() {
        return dungeon;
    } 

    public ArrayList<Displayable> getDisplayables() {
        return displayables;
    }  
    public Player getPlayer() {
        return player;
    } 

    private int totalHeight;
    public void setTotalHeight(int _totalHeight) {
        totalHeight = _totalHeight;
    } 
    public int getTotalHeight() {
        return totalHeight;
    } 

    // A constructor for this class.  It makes an implicit call to the
    // DefaultHandler zero arg constructor, which does the real work
    // DefaultHandler is defined in org.xml.sax.helpers.DefaultHandler;
    // imported above, and we don't need to write it.  We get its 
    // functionality by deriving from it!
    public DungeonXMLHandler() {

    }

    // startElement is called when a <some element> is called as part of 
    // <some element> ... </some element> start and end tags.
    // Rather than explain everything, look at the xml file in one screen
    // and the code below in another, and see how the different xml elements
    // are handled.
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        if (qName.equalsIgnoreCase("Dungeon")) {
            String name = attributes.getValue("name");
            int width = Integer.parseInt(attributes.getValue("width"));
            topHeight = Integer.parseInt(attributes.getValue("topHeight")); // global var
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            int totalHeight = topHeight + gameHeight + bottomHeight;
            this.setTotalHeight(totalHeight);
            objectDisplayGrid = ObjectDisplayGrid.getObjectDisplayGrid(width, totalHeight);
            dungeon = Dungeon.getDungeon(name, width, gameHeight); // create dungeon

            // System.out.println("Dungeon Name: " + name );
            // System.out.println("Dungeon width: " + width);
            // System.out.println("Game Height: " + gameHeight);
            // System.out.println("Top Height: " + topHeight);
            // System.out.println("Bottom Height: " + bottomHeight);
            dungeonBeingParsed = dungeon;
        } 
        
        else if (qName.equalsIgnoreCase("Rooms")) {
            // System.out.println("Rooms tag was passed. Nothing altered.");

        } 

        else if (qName.equalsIgnoreCase("Room")) {
            int roomID = Integer.parseInt(attributes.getValue("room"));
            Room room = new Room(roomID);

            dungeon.addRoom(room); // add room to dungeon
            displayables.add(room);
            structureBeingParsed = room;
            // roomBeingParsed = room;
            // System.out.println("This constructor creates the Room with ID " + roomID);

        } 
        else if (qName.equalsIgnoreCase("Scroll")) { //addItem
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name);
            scroll.setID(roomNum, serial);
           
            if ( creatureBeingParsed instanceof Player ){ // if we're ARE parsing a player --> Add item to the PLAYER
                player.setScroll(scroll);// this also adds it to the pack
            }
            else { // if we're NOT parsing player --> Add item to the dungeon
                dungeon.addItem(scroll); // add the item to the  dungeon attribute
            }
            displayables.add(scroll);
            itemBeingParsed = scroll;
        } 
        else if (qName.equalsIgnoreCase("Player")) { // creature
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            player = Player.getPlayer(); // create the player object
            player.setID(roomNum, serial);
            player.setName(name); 

            dungeon.addCreature(player);
            displayables.add(player);
            creatureBeingParsed = player;
            // System.out.println("This constructor adds a Creature (" + name + ") to room " + roomNum + " and with serial " + serial);
        } 
        else if (qName.equalsIgnoreCase("ItemAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");

            if (name.equals("Hallucinate")){
                Hallucinate itemAction = new Hallucinate(name,type);
                itemBeingParsed.addItemAction(itemAction);
                actionBeingParsed = itemAction;
            }
            else if (name.equals("BlessArmor")){
                BlessCurseOwner itemAction = new BlessCurseOwner(name,type);
                itemBeingParsed.addItemAction(itemAction);
                actionBeingParsed = itemAction;
            }
            else {
                System.out.println("Don't recognize the parsed itemAction");
            }
            // if an itemAction if being parsed, it is within a scroll!!
            // therefore: itemBeingParsed == Scroll scroll
            //    scroll.addItemAction(itemAction); // has name == hallucinate/bless
            
            // itemBeingParsed.addItemAction(itemAction);
            // actionBeingParsed = itemAction;
            // System.out.println("This constructor creates a ItemAction with name:" + name + " and type: " + type);
        } 

        else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");

            // if we're parsing a player/monster: add the creature action to an array (in player/monster) of CreatureActions
            // Creature creatureBeingParsed == Creature owner
            if ( name.equals("ChangeDisplayedType") ){
                ChangeDisplayedType creatureAction = new ChangeDisplayedType(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                // is creatureBeingParsed == null or something ?? 
                // try a type cast on line 190 (and the like ... )
                actionBeingParsed = creatureAction;
            }
            else if ( name.equals("UpdateDisplay")){
                UpdateDisplay creatureAction = new UpdateDisplay(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                actionBeingParsed = creatureAction;
            }
            else if ( name.equals("EndGame") ){
                EndGame creatureAction = new EndGame(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                actionBeingParsed = creatureAction;
            }
            else if ( name.equals("Remove") ){
                Remove creatureAction = new Remove(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                actionBeingParsed = creatureAction;
            }
            else if ( name.equals("YouWin") ){
                YouWin creatureAction = new YouWin(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                actionBeingParsed = creatureAction;
            }
            else if ( name.equals("Teleport") ){
                Teleport creatureAction = new Teleport(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                actionBeingParsed = creatureAction;
            }
            else if ( name.equals("DropPack") ){
                DropPack creatureAction = new DropPack(name, type, creatureBeingParsed);
                creatureBeingParsed.addCreatureAction(creatureAction);
                actionBeingParsed = creatureAction;
            }
            else {
                System.out.println("Don't recognize the parsed creatureAction");
            }  
        } 
        else if (qName.equalsIgnoreCase("Sword")) { // addItem
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name);
            sword.setID(roomNum, serial);

            
            if ( creatureBeingParsed instanceof Player ){ // if we're ARE parsing a player --> Add item to the PLAYER
                player.addToPack(sword);// this also adds it to the pack
            }
            else { // if we're NOT parsing player --> Add item to the dungeon
                dungeon.addItem(sword);
            }
            
            displayables.add(sword);
            itemBeingParsed = sword;

            // System.out.println("This constructor adds a Sword (" + name + ") to room " + roomNum + " and with serial " + serial);
        } 
        else if (qName.equalsIgnoreCase("Monster")) { // addCreature
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Monster monster = new Monster();
            monster.setName(name);
            monster.setID(roomNum, serial);

            dungeon.addCreature(monster);
            // room.setCreature(monster); // do I need this ??
            displayables.add(monster);
            creatureBeingParsed = monster;
            // System.out.println("This constructor adds a Monster (" + name + ") to room " + roomNum + " and with serial " + serial);
        } 
        else if (qName.equalsIgnoreCase("Armor")) { // addItem
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name);
            armor.setName(name);
            armor.setID(roomNum, serial);
            
            if ( !(creatureBeingParsed instanceof Player) ){ // if we're NOT parsing player / is null --> Add item to the dungeon
                dungeon.addItem(armor); // ???
            }
            else if ( creatureBeingParsed instanceof Player ){ // if we're ARE parsing a player --> Add item to the PLAYER
                player.addToPack(armor);
            }

            displayables.add(armor);
            itemBeingParsed = armor;  
            // System.out.println("This constructor adds an Armor (" + name + ") to room " + roomNum + " and with serial " + serial);
        } 
        else if (qName.equalsIgnoreCase("Passages")) { 
            // System.out.println("Passage tag was passed. Nothing altered");

        } 
        else if (qName.equalsIgnoreCase("Passage")) { // addPassage
            String name = attributes.getValue("name");
            int roomNum1 = Integer.parseInt(attributes.getValue("room1"));
            int roomNum2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage(name, roomNum1, roomNum2);

            dungeon.addPassage(passage);
            displayables.add(passage);
            structureBeingParsed = passage;
            // System.out.println("This constructor adds a Passage from " + roomNum1 + " to " + roomNum2);
        } 
        
        // else if (qName.equalsIgnoreCase("visible")) { bVisible = true;}
        else if (qName.equalsIgnoreCase("visible")) { bVisible = true;}
        else if (qName.equalsIgnoreCase("posX")) { bPosX = true;}
        else if (qName.equalsIgnoreCase("posY")) { bPosY = true;}
        else if (qName.equalsIgnoreCase("width")) { bWidth = true;}
        else if (qName.equalsIgnoreCase("height")) { bHeight = true;}
        else if (qName.equalsIgnoreCase("actionMessage")) { bActionMessage = true;}
        else if (qName.equalsIgnoreCase("actionIntValue")) { bActionIntValue = true;}
        else if (qName.equalsIgnoreCase("actionCharValue")) { bActionCharValue = true;}
        else if (qName.equalsIgnoreCase("hp")) { bHp = true;}
        else if (qName.equalsIgnoreCase("hpMoves")) { bHpMoves = true;}
        else if (qName.equalsIgnoreCase("maxhit")) { bMaxHit = true;}
        else if (qName.equalsIgnoreCase("ItemIntValue")) { bItemIntValue = true;}
        else if (qName.equalsIgnoreCase("type")) { bType = true;}
        else {
            System.out.println("Unknown qname: " + qName);
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bVisible) {
            if(itemBeingParsed != null){ // creature
                if(data.toString() == "1") { itemBeingParsed.setVisible() ;}
                else { itemBeingParsed.setInvisible(); }
                bVisible = false;
            }
            else if(creatureBeingParsed != null){ // item
                if(data.toString() == "1") { creatureBeingParsed.setVisible(); }
                else { creatureBeingParsed.setInvisible(); }
                bVisible = false; // we've exited the tag
            }
            else if(structureBeingParsed != null){ // structure
                if(data.toString() == "1") { structureBeingParsed.setVisible(); }
                else { structureBeingParsed.setInvisible(); }
                bVisible = false;
            }
        }

        else if (bPosX) { // if it's the posY tag for a Room:
            if(itemBeingParsed != null){ // items & creatures are based on the position of room top left corner
                // if an item is parsed, we're NOT in a passage. therefore structureBP == Room
                int xCorner = structureBeingParsed.getPosX();
                itemBeingParsed.setPosX( Integer.parseInt(data.toString()) + xCorner ); 
                bPosX = false;
            }
            else if(creatureBeingParsed != null){
                int xCorner = structureBeingParsed.getPosX();
                creatureBeingParsed.setPosX( Integer.parseInt(data.toString()) + xCorner ); 
                bPosX = false;
            }
            else if(structureBeingParsed != null){ // structure
                structureBeingParsed.setPosX( Integer.parseInt(data.toString()) );
                bPosX = false;
            }
        } // adding topHeight: shifts [ everything ] down by {topHeight}, so leave room for the message box at the top
        // [ everything == everything with a setPosY ]: rooms (5,0) -> (5, 0 + 2 ) -> (5,2)    
        // [ everything == everything with a setPosY ]: rooms (5,0) -> (5, 0 + 2 ) -> (5,2) 

        else if (bPosY) { // if it's the posY tag for a Room:
            if(itemBeingParsed != null){ // items & creatures are based on the position of room top left corner
                // if an item is parsed, we're NOT in a passage. therefore structureBP == Room
                int yCorner = structureBeingParsed.getPosY();
                itemBeingParsed.setPosY( Integer.parseInt(data.toString()) + yCorner ); 
                bPosY = false;
            }
            else if(creatureBeingParsed != null){ // item
                int yCorner = structureBeingParsed.getPosY();
                creatureBeingParsed.setPosY( Integer.parseInt(data.toString()) + yCorner ); 
                bPosY = false;
            }
            else if(structureBeingParsed != null){ // structure
                structureBeingParsed.setPosY( Integer.parseInt(data.toString()) + topHeight );
                bPosY = false;
            } // adding topHeight: shifts [ everything ] down by {topHeight}, so leave room for the message box at the top
        }     // [ everything == everything with a setPosY ]: rooms (5,0) -> (5, 0 + 2 ) -> (5,2)    
        
        else if (bWidth) {
            if(getDisplayableObj() != null){ 
                getDisplayableObj().setWidth(Integer.parseInt(data.toString()) ); // parse the data in the tag, and convert it to a string
                bWidth = false;
            }
        }

        else if (bHeight) {
            if(getDisplayableObj() != null){ // room
                getDisplayableObj().setHeight(Integer.parseInt(data.toString()) ); // parse the data in the tag, and convert it to a string
                bHeight = false;
            }
        }
        
        // ActionMessage -->  CreatureAction, ItemAction 
        else if (bActionMessage) { 
            if(actionBeingParsed != null){ // we're parsing a room. the posX is for a room
                actionBeingParsed.setMessage(data.toString()); // parse the data in the tag, and convert it to a string
                bActionMessage = false;
            } 
        }
        // ActionCharValue -->  CreatureAction, ItemAction 
        else if (bActionCharValue) {
            if(actionBeingParsed != null){ // we're parsing a room. the posX is for a room
                actionBeingParsed.setCharValue( data.toString().charAt(0) ); // parse the data in the tag, and convert it to a string
                bActionCharValue = false;
            } 
        }
        // ActionIntValue -->  CreatureAction, ItemAction 
        else if (bActionIntValue) {
            if(actionBeingParsed != null){ // we're parsing a room. the posX is for a room
                actionBeingParsed.setIntValue(Integer.parseInt(data.toString())); // parse the data in the tag, and convert it to a string
                bActionIntValue = false;
            } 
        }

        // Hp --> Creature (Monster, Player)
        else if (bHp) {
            if(creatureBeingParsed != null ){ // we're parsing a room. the posX is for a room
                creatureBeingParsed.setHp(Integer.parseInt(data.toString())); // parse the data in the tag, and convert it to a string
                bHp = false;
            } 
        }

        // HpMoves --> Player
        else if (bHpMoves) {
            if(creatureBeingParsed != null){ 
                creatureBeingParsed.setHpMoves(Integer.parseInt(data.toString()));
                bHpMoves = false;
            }
        }

        // MaxHit --> Monster, Player
        else if (bMaxHit) {
            if(creatureBeingParsed != null){ // we're parsing a room. the posX is for a room
                creatureBeingParsed.setMaxHit(Integer.parseInt(data.toString())); // parse the data in the tag, and convert it to a string
                bMaxHit = false;
            } 
        }
        
        // ItemIntValue -> Armor, Sword
        else if (bItemIntValue) {
            if(itemBeingParsed != null){ // item
                itemBeingParsed.setItemIntValue( Integer.parseInt(data.toString()) );
                bItemIntValue = false;
            }
        }

        // Type -> Monster
        else if (bType) {
            if(creatureBeingParsed != null){ // itemAction
                if (creatureBeingParsed instanceof Monster){
                    creatureBeingParsed.setType( data.toString().charAt(0) );
                    bType = false;
                }
            }
        }

        else if (qName.equalsIgnoreCase("Dungeon")) {;}
        else if (qName.equalsIgnoreCase("Rooms")) {structureBeingParsed = null; ;}
        else if (qName.equalsIgnoreCase("Room")) { structureBeingParsed = null; } // done parsing it
        else if (qName.equalsIgnoreCase("Passages")) {structureBeingParsed = null; ;}
        else if (qName.equalsIgnoreCase("Passage")) { structureBeingParsed = null; }

        else if (qName.equalsIgnoreCase("Player")) { creatureBeingParsed = null; }
        else if (qName.equalsIgnoreCase("Monster")) { creatureBeingParsed = null; }

        else if (qName.equalsIgnoreCase("Scroll")) { itemBeingParsed = null; }
        else if (qName.equalsIgnoreCase("Sword")) { itemBeingParsed = null; }
        else if (qName.equalsIgnoreCase("Armor")) { itemBeingParsed = null; }

        else if (qName.equalsIgnoreCase("CreatureAction")) { actionBeingParsed = null; }
        else if (qName.equalsIgnoreCase("ItemAction")) { actionBeingParsed = null; }

        
        
        else {
            System.out.println("Main Tag Error. Unknown qname: " + qName);
        }
    }

    public Displayable getDisplayableObj(){
        if (itemBeingParsed != null){ // then set the coordinates of the item
            return itemBeingParsed;                  
        }
        else if (creatureBeingParsed != null){ // then set the coordinates of the creature
            return creatureBeingParsed;                
        }
        else if(structureBeingParsed != null){ // we're parsing a room. the posX is for a room
            return structureBeingParsed;     
        }
        else
            return null;
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        // if (DEBUG > 1) {
        //     System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
        //     System.out.flush();
        // }
    }

    // @Override
    // public String toString() {
    //     String str = "DungeonXMLHandler\n";
    //     str += "   maxStudents: " + maxStudents + "\n";
    //     str += "   studentCount: " + studentCount + "\n";
    //     for (Student student : students) {
    //         str += student.toString() + "\n";
    //     }
    //     str += "   studentBeingParsed: " + studentBeingParsed.toString() + "\n";
    //     str += "   activityBeingParsed: " + activityBeingParsed.toString() + "\n";
    //     str += "   bInstructor: " + bInstructor + "\n";
    //     str += "   bCredit: " + bInstructor + "\n";
    //     str += "   bName: " + bInstructor + "\n";
    //     str += "   bNumber: " + bInstructor + "\n";
    //     str += "   bLocation: " + bInstructor + "\n";
    //     str += "   bMeetingTime: " + bInstructor + "\n";
    //     str += "   bMeetingDay: " + bInstructor + "\n";
    //     return str;
    // }
}
