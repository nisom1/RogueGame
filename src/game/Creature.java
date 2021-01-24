package game;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Creature extends Displayable {
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED

    // private String name;

    // private int posX;
    // private int posY;
    // private int hp;
    // private int hpMoves;
    // private char type;
    // private int maxHit;

    // list of HIT actions. loop through this array when the player is hit
    protected ArrayList<CreatureAction> hitActions = new ArrayList<CreatureAction>( );

    // list of DEATH actions. loop through this array when the player is hit
    protected ArrayList<CreatureAction> deathActions = new ArrayList<CreatureAction>( );
    // private static ObjectDisplayGrid displayGrid;
    // private static Player player;

    public Creature(){
        super(); // sets name, posX, posY, hp, hpMoves, type, maxHit
    } 

    public void addCreatureAction(CreatureAction creatureAction){ // do I need to make specialized versions --> like addYouWin, etc ???
        if( creatureAction.getType().equals("hit") ) // it's a hit action
            hitActions.add(creatureAction);
        else if( creatureAction.getType().equals("death") ) // it's a death action
            deathActions.add(creatureAction);
    }  

    public void updateHp(int damageFromCreature){ // only the monster uses this
        hp = hp - damageFromCreature;
    }

    public void checkHp(Creature killedCreature, Creature attackingCreature){ // check if either was killed
        if ( hp < 0 ){ // If they were killed --> Execute Death Actions
            getGrid().displayInfo(attackingCreature.getName()+ " killed the "+killedCreature.getName()+"!"); // this message will display if there are no death actions
            executeDeathActions();
            // displayGrid.removeObjectFromDisplay(killedCreature.getPosX(),killedCreature.getPosY());
        }
        else{ // The Creature was just hit--> Execute Hit Actions
            executeHitActions();
        }
    }

    public void executeDeathActions(){ // execute when hp < 0
        int length = deathActions.size();
        int i = 0;
        for (i=0; i<length; i++){
            deathActions.get(i).run();
            // System.out.println( "Death Actions ["+i+"] :  "+ deathActions.get(i).getName()+",  " + deathActions.get(i).getType()+",  " + deathActions.get(i).getOwner().getClass().getSimpleName() );
        }
        // player death actions : ChangeDisplayedType , UpdateDisplay , EndGame , YouWin
        // monster death actions : Remove , YouWin
    }

    public void executeHitActions(){ 
        int length = hitActions.size();
        int i = 0;
        for (i=0; i<length; i++){
            hitActions.get(i).run();
            // System.out.println( "Hit Actions ["+i+"] :  "+ hitActions.get(i).getName()+",  " + hitActions.get(i).getType()+",  " + hitActions.get(i).getOwner().getClass().getSimpleName());

        }
        // player hit actions : DropPack
        // monster hit actions : Teleport
    }
}