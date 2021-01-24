package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.Random;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {
    
    private static AsciiPanel terminal;
    private List<InputObserver> inputObservers = null;
    
    // private Stack<Displayable>[][] objectGrid = null;
    private Stack<Displayable>[][] objectGrid = null;
    private static int width;
    private static int height;
    // private static int topHeight = 2;
    // private static int bottomHeight = 4;
    // private static int gameHeight;
    private static Player player;
    private static Dungeon Dungeon;

    private static ObjectDisplayGrid instance;
    public static ObjectDisplayGrid getObjectDisplayGrid(int _width, int _height){
        if (instance == null)
            instance = new ObjectDisplayGrid(_width, _height);
        return instance;
    }
    
    // @SupressWarnings("unchecked") // piazza @367
    private ObjectDisplayGrid(int _width, int _height){
        width = _width;
        height = _height; // is this right ??
        
        terminal = new AsciiPanel(width, height);
        
        objectGrid = (Stack<Displayable>[][]) new Stack[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                objectGrid[i][j] = new Stack<Displayable>();
            }
        }
        
        clearDisplay(); 
        setTopAndBottomDisplay(); // top is at (0,0). bottom is at (0, totalHieght - 1)

        super.add(terminal);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(width * 9, (int)(height * 16.5) ); 

        // super.repaint(); // previously commented out
        // terminal.repaint( );// previously commented out
        
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        // if (DEBUG > 0) {
        //     System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        // }
        inputObservers.add(observer);
    }

    public void removeInputObserver(InputObserver observer) {
        // if (DEBUG > 0) {
        //     System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        // }
        inputObservers.remove(observer);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // if (DEBUG > 0) {
        //     System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        // }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar()); // pass the character pressed > to notify observers
    }

    private void notifyInputObservers(char ch) {
        Iterator<InputObserver> iterator = inputObservers.iterator();
        
        while ( iterator.hasNext() ) {
            InputObserver inputObs = iterator.next();
            if( inputObs.observerUpdate(ch) ){
                iterator.remove();
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void clearDisplay() {  // create the display. ours will look different
        Displayable ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addObjectToDisplay(ch, i, j); // i,j is the POSITION where the object should be added
                // add more objects to display, and give their (x,y) position 
                // Should other functions be calling this **** (where, parser? how to do rooms?)
            }
        }
        terminal.repaint();
    }

    public final void setTopAndBottomDisplay() {  // create the display. ours will look different
        terminal.write("HP: ", 0, 0);
        terminal.write("Score: 0", 10, 0);

        terminal.write("Pack:", 0, height-3 );
        terminal.write("Info: ", 0, height-1);
        terminal.repaint();
    }

    public final void displayHP(int newPlayerHP) {  // create the display. ours will look different
        terminal.write("HP: "+ "     ", 0, 0); // clear whatever is there
        terminal.write("HP: "+ newPlayerHP, 0, 0); // input new value
        terminal.repaint();
    }

    public final void displayPack(ArrayList<Item> pack) {  /// CHANGE THIS SO IT PRINTS Displayable.getChar
        String message = "";
        if (pack.size() == 0){
            displayPackLine(" "); // update the info line? I also want to update the PACK line
            displayInfo("Your pack is empty, sis"); // display this completed messsage, under the INFO section
        }
        else {
            for (int i = 0; i < pack.size(); i++){
                if (pack.get(i) instanceof Scroll){
                    Scroll scroll = (Scroll) pack.get(i);
                    message += ("  "+ (i+1) + ": " + scroll.getName() ); // 1: +10 Armor  2: +5 Sword
                }
                else if ( pack.get(i) instanceof Armor){
                    Armor armor = (Armor) pack.get(i);
                    message += ("   "+ (i+1) + ": +" +armor.getItemIntValue()+" Armor" ); // 1: +10 Armor 
                    if(armor.getIsUsed() == 1){ // if wielded ( pack.get(i-1).isWielded )
                        message += (" (a)");
                    }
                }
                else{ // else, it's a Sword
                    Sword sword = (Sword) pack.get(i);
                    message += ("   "+ (i+1) + ": +" +sword.getItemIntValue()+" Sword" ); // 1: +10 Armor 
                    if(sword.getIsUsed() == 1){ // if wielded ( pack.get(i-1).isWielded )
                        message += (" (w)");
                    }
                }
            }
            displayInfo(message);
            displayPackLine(message);
        }
        
        // display this completed messsage, under the INFO section
        // update the info line? I also want to update the PACK line
        // BUT NOT THE OTHER WAY AROUNd
    }
    public final void updatePackLine(ArrayList<Item> pack) {  //updates the pack line ONLY
        String message = "";
        if (pack.size() == 0){
            displayPackLine(" "); // update the info line? I also want to update the PACK line
        }
        else {
            for (int i = 0; i < pack.size(); i++){
                if ( (pack.get(i).getCharacter()) == ('?') ){ // it's a Scroll
                    Scroll scroll = (Scroll) pack.get(i);
                    message += ("  "+ (i+1) + ": " + scroll.getName() ); // 1: +10 Armor  2: +5 Sword
                }
                else if ( (pack.get(i).getCharacter()) == (']') ){ // it's an Armor
                    Armor armor = (Armor) pack.get(i);
                    message += ("   "+ (i+1) + ": +" +armor.getItemIntValue()+" Armor" ); // 1: +10 Armor 
                    if(armor.getIsUsed() == 1){ // if wielded ( pack.get(i-1).isWielded )
                        message += (" (a)");
                    }
                }
                else if ( (pack.get(i).getCharacter()) == (')') ){ // else, it's a Sword
                    Sword sword = (Sword) pack.get(i);
                    message += ("   "+ (i+1) + ": +" +sword.getItemIntValue()+" Sword" ); // 1: +10 Armor 
                    if(sword.getIsUsed() == 1){ // if wielded ( pack.get(i-1).isWielded )
                        message += (" (w)");
                    }
                }
                else{
                    System.out.println("Don't recognize the pack item: "+pack.get(i).getName() );
                }
            }
            displayPackLine(message);
        }
    }


    public final void displayHitInfo(String player, int playerInflicted, String monster, int monsterInflicted) {  // this does not modify the " Pack:  " line!!!!
        // " attackingCreature inflicted -[damage from attacking Creture] damage on victimCreature "
        String message = " ";
        if (playerInflicted == 0)
            message += ( player + " inflicted " +playerInflicted+ " damage.  ") ;
        else 
            message += ( player + " inflicted -" +playerInflicted+ " damage.  ") ;

        if (monsterInflicted == 0)
            message += ( monster + " inflicted " +monsterInflicted+ " damage.  ") ;
        else 
            message += ( monster + " inflicted -" +monsterInflicted+ " damage.  ") ;

        displayInfo(message); // display this completed messsage, under the INFO section
    }

    // PACK: LINE
    public final void displayPackLine(String message) {  // create the display. ours will look different
        // clear whatever was there previously (make a loop, like room's X's)
        terminal.clear(' ', 0, height - 3, width-1, 1); 
        
        // write / input the new message
        terminal.write("Pack: "+ message, 0, height-3);
        terminal.repaint();
    }
    // INFO: LINE
    public final void displayInfo(String message) {  // create the display. ours will look different
        // clear whatever was there previously (make a loop, like room's X's)
        terminal.clear(' ', 0, height - 1, width-1, 1); // clear from (0, height - 1) to (width-1, height - 1). a line)

        // write / input the new message
        terminal.write("Info: "+ message, 0, height-1);
        terminal.repaint();
    }

    
    
    public void addObjectToDisplay(Displayable ch, int x, int y) { // Char ch = should indicate what to add (i.e. M=Monster)
        if ((0 <= x) && (x < objectGrid.length)) { //should i have changed this condition
            if ((0 <= y) && (y < objectGrid[0].length)) {
                // Char ch1 = new Char(ch);
                objectGrid[x][y].push(ch); // Stack<Displayable> objectGrid[x][y] 
                writeToTerminal(x, y); // display on ascii terminal
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        Displayable displayable = (Displayable) objectGrid[x][y].peek(); 
        char ch = displayable.getCharacter(); 
        terminal.write(ch, x, y);
        terminal.repaint();
    }

    public void removeObjectFromDisplay(int x, int y) { // Char ch = should indicate what to add (i.e. M=Monster)
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y].pop();  
                writeToTerminal(x, y); // writes whatever is under it to that (x,y) position
            }
        }
    }
    public char getObjectFromDisplay(int x, int y) { // Char ch = should indicate what to add (i.e. M=Monster)
        if ( (objectGrid[x][y].peek() instanceof Creature) | (objectGrid[x][y].peek() instanceof Monster) | (objectGrid[x][y].peek() instanceof Player) ){
            char ch = objectGrid[x][y].peek().getCharacter(); 
            return ch;
        }
        else if ( (objectGrid[x][y].peek() instanceof Item) | (objectGrid[x][y].peek() instanceof Scroll) | (objectGrid[x][y].peek() instanceof Sword) | (objectGrid[x][y].peek() instanceof Armor) ){
            char ch = objectGrid[x][y].peek().getCharacter(); 
            return ch;
        }
        else {
            // char ch = objectGrid[x][y].peek().getCharacter();
            Char ch1 = (Char) objectGrid[x][y].peek(); 
            char ch = ch1.getCharacter(); 
            return ch;
        }
    }
    public Displayable getDisplayableFromDisplay(int x, int y) { // Char ch = should indicate what to add (i.e. M=Monster)
        if ( (objectGrid[x][y].peek() instanceof Creature) | (objectGrid[x][y].peek() instanceof Monster) | (objectGrid[x][y].peek() instanceof Player) ){
            return objectGrid[x][y].peek() ;
        }
        else if ( (objectGrid[x][y].peek() instanceof Item) | (objectGrid[x][y].peek() instanceof Scroll) | (objectGrid[x][y].peek() instanceof Sword) | (objectGrid[x][y].peek() instanceof Armor) ){
            return objectGrid[x][y].peek() ;
        }
        else {
            // char ch = objectGrid[x][y].peek().getCharacter();
            Char object = (Char) objectGrid[x][y].peek(); 
            return object;
        }
    }

    // used for checking the item UNDER a player, without removing the player from the physical, terminal screen
    public char pollObjectFromDisplay(int x, int y) { // get and remove object from the display
        // char ch = objectGrid[x][y].peek().getCharacter(); // save/get the object
        char ch = objectGrid[x][y].peek().getCharacter(); // item.getCharacter()
        if ((0 <= x) && (x < objectGrid.length)) {      // then remove it from the grid
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y].pop(); // remove from objectDisplay
                writeToTerminal(x, y); 
            } 
        }
        return ch; // save and remove the object, because it goes in the pack
    }


    public void restoreGrid() { // restore grid to it's usual state
        // public void addObjectToDisplay(Displayable ch, int x, int y) { // Char ch = should indicate what to add (i.e. M=Monster)
        for (int x = 0; x < width; x++) {
            for (int y = 2; y < (height-4); y++) { 
                writeToTerminal(x, y); // this function READS FROM THE LOGICAL GRID (aka our Stacks) ???
            }
        }
    }

    public void hallucinateGrid() { // write a random character to the grid
        Displayable topOfStack;
        char charOnTop;
        char ch;
        for (int x = 0; x < width; x++) {
            for (int y = 2; y < (height-4); y++) { // height an width of the GAME ???
                charOnTop = objectGrid[x][y].peek().getCharacter(); // get char
                if ( charOnTop != ' ' ) { // if it is NOT a blank spot on in the Dungeon
                    ch = getRandomChar();
                    terminal.write(ch, x, y); // write a random character there!
                }
            }
        }
        terminal.repaint(); // do i need this ??
    }

    public char getRandomChar() { // restore grid to it's usual state
        char[] displayableChars = {'X', '.', '#', 'T', 'S', 'H', '?', ']', ')', '+','@' };
        int randIndex;
        char randomChar;

        Random random = new Random(); // int value = random.nextInt(max - min) + min;
        randIndex = random.nextInt( displayableChars.length - 1 );  // range of indexes to randomly select
        randomChar = displayableChars[randIndex];

        return randomChar;
    }

    


}
