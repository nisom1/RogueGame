package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Hallucinate extends ItemAction implements InputObserver{

    // Action defines:
    // protected String name; // set in the start tag (displayable method)
    // protected String type;
    // protected Creature owner;

    // protected String actionMessage; // set in the end tag
    // protected int actionIntValue;
    // protected char actionCharValue;

    private static int numMoves = 1; // current number of moves
    Player player = Player.getPlayer();

    public Hallucinate(String name, String type){
        super(name, type);
    }

    @Override
    public boolean observerUpdate(char inputChar){
        String movesLeft = "";

        if (numMoves <  actionIntValue) {
            if ( (inputChar == 'h') | (inputChar == 'j') | (inputChar == 'k') | (inputChar == 'l') ){
                numMoves++; 
                movesLeft += "Hallucination lasts for "+ (actionIntValue-numMoves+1) +" more moves";
                getGrid().displayInfo( movesLeft );
                getGrid().hallucinateGrid();
            }
            return false;
        }
        else{ // end hallucinate
            numMoves = 1;            
            getGrid().restoreGrid();
            getGrid().displayInfo( "Hallucinate Scroll has been used. Effects are over." );
            return true; // true, we no longer want this function to observe the inputs
        }
    }

    
    @Override
    public void run(){
        getGrid().hallucinateGrid(); // once the scroll is read, immediately change the board
        // and imediate print an info message
        String message = actionMessage;
        message += " Effects last for "+actionIntValue+" moves";
        getGrid().displayInfo( message );        
        getGrid().registerInputObserver(this);
    }

}
// @Override
            // public void observerUpdate(char inputChar){
            //     String movesLeft = "";
            //     if ( (inputChar == 'h') | (inputChar == 'j') | (inputChar == 'k') | (inputChar == 'l') ){
            //         if (numMoves <  actionIntValue) {
            //             numMoves++; 
            //             movesLeft += "Hallucination lasts for "+ (actionIntValue-numMoves+1) +" more moves";
            //             displayGrid.displayInfo( movesLeft );
            //             displayGrid.hallucinateGrid();
            //         }
            //         else{ // end hallucinate
            //             numMoves = 1;
            //             System.out.println("IN OBSERVER UPDATE:  just set numMoves = 0" );

            //             displayGrid.removeInputObserver(this);
            //             System.out.println("IN OBSERVER UPDATE:  just executed removeInputObserver()" );
                        
            //             displayGrid.restoreGrid();
            //             System.out.println("IN OBSERVER UPDATE:  just executed restoreGrid()" );

            //             displayGrid.displayInfo( "Hallucinate Scroll has been used. Effects are over." );
            //             System.out.println("IN OBSERVER UPDATE:  just executed displayInfo()" );

            //         }
            //     }
            // }
