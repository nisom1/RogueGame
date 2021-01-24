package game;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    private static Player player;
    private static char letterPressed = ' ';

    public KeyStrokePrinter(ObjectDisplayGrid grid) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        // displayGrid = ObjectDisplayGrid.getObjectDisplayGrid(1, 1);
    }

    @Override
    public boolean observerUpdate(char ch) {
        // if (DEBUG > 0) {
        //     System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        // }
        inputQueue.add(ch);
        return false; // false, because never stops listening
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;
        player = Player.getPlayer(); 

        boolean processing = true;
        while (processing) {
            if ( player.getEndGameChar() == 1) {  // stop collecting keyboard input
                processing = false;
            } 
            else if ( (inputQueue.peek() == null) ) {
                processing = false;
            }
            else {
                // ch = Character.toLowerCase(inputQueue.poll());
                ch = inputQueue.poll();
                // if (DEBUG > 1) {
                //     System.out.println(CLASSID + ".processInput peek is " + ch);
                // }
                if (ch == 'x') { // exit command
                    System.out.println("got an X, ending input checking");
                    return false;
                }
                else if (letterPressed == 'H') { // q for questions ?? 
                    char command = ch;
                    explainCommands(command); // ??? Can I put this in a function in this file ??
                    letterPressed = ' ';
                }

                else if( letterPressed != ' ' ){ // if a letter has been pressed prior the inputted char
                    // do / execute the code for that correspond <letter> <number>
                    // try to convert the inputted ch ! See if we can convert to an integer! maybe it's an int !
                    boolean isInt = Character.isDigit(ch); // assume ch (the value entered) isn't an integer
                    if(isInt) {
                        int numPressed = Character.getNumericValue(ch); // <letterPressed> <numPressed>
                        switch (letterPressed) {
                        case 'd': // drop item <int>
                            player.dropItem(numPressed, player.getPosX(), player.getPosY());
                            break;
                        case 'r': // read scroll <int>
                            player.readScroll(numPressed); // scroll = pack.get(numPressed-1)
                                                            // scroll.executeItemActions()
                            break;
                        case 't': // take out weapon <int>
                            player.useSword(numPressed);
                            break;
                        case 'w': // wear armor <int>
                            player.wearArmor(numPressed);
                            break;
                        default:
                            displayGrid.displayInfo("The inputted letter: " +letterPressed+ "isn't recognized.");
                            break;
                        }
                        displayGrid.updatePackLine(player.getPack());
                        letterPressed = ' ';
                    }
                    
                    else { // if isInt == false
                        // maybe ch == y, the 2nd character to oficially end the game
                        if ( (ch == 'y') | (ch == 'Y') ){
                            displayGrid.displayInfo("Game Over! Player ended the game.");
                            player.setEndGameChar(1); // stops all kyboard input
                            letterPressed = ' ';
                        }
                        else{ // else, if the 2nd input wasn't an int or a 'y' , we don't know
                            System.out.println("The letter: "+ letterPressed + " was followed by the non-integer "+ ch + "");
                            letterPressed = ' ';
                        }
                    }
                }

                else if (ch == 'h') {
                    letterPressed = ' '; // kinda want a char variable called "char letterPressed"
                    // player moves left
                    player.move(-1,0);
                    player.countMoves(); // updates the number of moves, until the player reaches hpMpves/hpMax
                                        // where in it gets it's player.Hp updated!
                }
                else if (ch == 'k') {
                    letterPressed = ' ';
                    // player moves up
                    player.move(0,-1);
                    player.countMoves();
                }
                else if (ch == 'j') {
                    letterPressed = ' ';
                    // player moves down
                    player.move(0,1);
                    player.countMoves();
                }
                else if (ch == 'l') {
                    letterPressed = ' ';
                    // player moves right
                    player.move(1,0);
                    player.countMoves();
                }
                else if (ch == 'p') {
                    letterPressed = ' ';
                    // player wants to pick up an item at spot (x,y)
                    player.pickUpItem( player.getPosX(), player.getPosY() ); // check if there is an item at that position
                    // lowercase P, because the METHOD isn't static
                    displayGrid.updatePackLine(player.getPack());
                }

                else if (ch == 'i') {
                    letterPressed = ' ';
                    if(player.getPack().size() == 0){ // player is trying to drop an item - nothing in the pack
                        displayGrid.displayInfo("Pack is empty.");
                        letterPressed = ' ';
                    }
                    else { // if pack is not empty ... we can drop items !
                        displayGrid.displayPack( player.getPack() );
                    }
                }

                else if (ch == 'e') { // end game. ??? <Y | y >
                    letterPressed = 'e';
                    displayGrid.displayInfo( "Press <Y | y> to end the game." );
                }
                
                // show the different commands in the info section of the display. This is the bottom message display area.
                else if (ch == '?') { 
                    letterPressed = ' ';
                    displayGrid.displayInfo( "Commands: c, e, q, i, p, a, h<int>, d<int>, r<int>, t<int>, w<int>" );
                }

                // Processing the FIRST input. For the 2-keyboard-input-commands : 
                else if (ch == 'H') { // help command
                    letterPressed = 'H';
                    displayGrid.displayInfo( "Enter a command key to learn more about it" );
                }
                else if (ch == 'd') { // drop command
                    if(player.getPack().size() == 0){ // player is trying to drop an item - nothing in the pack
                        displayGrid.displayInfo("Pack is empty. Nothing to drop.");
                        letterPressed = ' ';
                    }
                    else { // if pack is not empty ... we can drop items !
                        letterPressed = 'd'; // register the letter press, because the pack isn't empty
                    }
                }
                else if (ch == 'r') { // read scroll
                    letterPressed = 'r'; // I could check if there is even a scroll in the pack
                }
                else if (ch == 't') { // take OUT / use sword
                    letterPressed = 't'; // register the letter press, because the pack isn't empty
                }
                else if (ch == 'w') { // wear armor
                    letterPressed = 'w';
                }
                else if (ch == 'c') { // changing armor
                    letterPressed = ' ';
                    if(player.getArmor() == null) {
                        displayGrid.displayInfo( "No armor is being worn. Nothing to remove." );
                    }
                    else{ // if the player is wearing  armor, and the user wants to remove / change it
                        player.getArmor().setIsUsed(0);
                        player.setHasArmor(0);
                        
                        displayGrid.displayInfo( "Armor is no longer worn. Stll in pack." );
                        displayGrid.updatePackLine(player.getPack());
                        // player.setArmor(null);
                    }
                }

                // processing in the SECOND input. For the 2 keyboard-input-commands : 

                else { 
                    System.out.println("Unknown character pressed: "+ ch + "");
                }

            }
        }
        return true;
    }

    @Override
    public void run() {
        // System.out.println("running");
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }
    public void explainCommands(char command) {
        switch (command) {// command can === any of the commands
            case 'c': // drop item <int>
                displayGrid.displayInfo(" 'c': Remove and return the armor worn to the pack.");
                break;
            case 'd': // 
                displayGrid.displayInfo(" d<int>: Drop item at index <int> of the pack.");
                break;
            case 'e':
                displayGrid.displayInfo(" 'e': End the game.");
                break;
            case 'a':
                displayGrid.displayInfo(" 'a': Displays all the different commands.");
                break;

            case 'q':
                displayGrid.displayInfo(" q<command>: Displays the specifics of the command");
                break;
            case 'i':
                displayGrid.displayInfo(" 'i': Displays all the items in the pack (inventory).");
                break;
            case 'p':
                displayGrid.displayInfo(" 'p': Pick up an item that you're stnanding on");
                break;
            case 'r':
                displayGrid.displayInfo(" r<int>: Read the scroll at index <int> of the pack. ");
                break;
            case 't':
                displayGrid.displayInfo(" t<int>: Wield the sword at index <int> of the pack.");
                break;
            case 'w':
                displayGrid.displayInfo(" w<int>: Wear the armor at index <int> of the pack.");
                break;
            
            default:
                displayGrid.displayInfo("Inputted command isn't recognized");
                break;
        }
    }

}

