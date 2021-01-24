package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
// import java.util.Random.nextInt;

// if the attribute is already in the parent, DO NOT REPEAT
public class Player extends Creature{
    // if the attribute is already in the parent, DO NOT REPEAT
    // if the attribute used by derived clases, MAKE IT PROTECTED

    // private int posX;
    // private int posY;

    // private int hp;
    // private int hpMoves;
    // private int maxHit;
    private char charOnScreen = '@';
    private int room;
    private int serial;
    
    private Sword sword = null;
    private Armor armor = null;
    // private Scroll scroll = null; // this wasn't included in the instructions
    private static Player player;

    private static ArrayList<Item> pack = new ArrayList<Item>( );
    private static int countMoves; // num of moves until the player reaches hpMoves
    // when they do, reset counter, and update HP++ on the screen
    public static int endGameChar = 0;
    public static int hasSword = 0;
    public static int hasArmor = 0;
    // public static int hasScroll = 0; Don't need a hasScroll.
    // When the scroll is read it it dropped --> and then poped from grid. AKA removed from the game
    ObjectDisplayGrid displayGrid = getGrid();

    private Player(){
        // sets the posX, posY, hp, hpMoves, maxHit
        // doesn't have the deathAction of hitAction
    }

    public static Player getPlayer(){
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
    } 

    // when a player USES and item ('T')
    // we have to setArmor
    // when a player drops their armor, we set it back to null
    public void setSword(Sword _sword){
        sword = _sword;
        pack.add(sword);
        // hasSword = 1; Sword is ADDED TO THE PACK. But not neccessarily WIELDED
    } 

    public void setArmor(Armor _armor){
        armor = _armor;
        pack.add(armor);
        // hasArmor = 1; Armor is ADDED TO THE PACK/ But not neccessarily WORN
    } 
    public Sword getSword(){ // returns 1 if player is wielding a sword
        return sword;
    } 

    public Armor getArmor(){ // returns 1 if player is wearing armor
        return armor;
    }

    public void setScroll(Scroll scroll){
        // scroll = _scroll;
        pack.add(scroll);
    }

    public int hasSword(){ // returns 1 if player is wielding a sword
        return hasSword;
    } 

    public int hasArmor(){ // returns 1 if player is wearing armor
        return hasArmor;
    } 

    public void setHasSword(int num){ // returns 1 if player is wielding a sword
        hasSword = num;
        if(num == 0){
            sword = null;
        }
    } 

    public void setHasArmor(int num){ // returns 1 if player is wearing armor
        hasArmor = num;
        if(num == 0){
            armor = null;
        }
    } 

    public int getHasSword(){ // returns 1 if player is wielding a sword
        return hasSword;
    } 

    public int getHasArmor(){ // returns 1 if player is wearing armor
        return hasArmor;
    } 

    

    // no set scroll, because the player can't start with a scroll
    
    // @Override // because player doesn't have this
    public void setType(char _ch){
    }

    public void setEndGameChar(int num){
       endGameChar = num;
    }
    public int getEndGameChar(){
       return endGameChar;
    }

    public char getCharacter(){ 
        return charOnScreen;
    }

    public ArrayList<Item> getPack(){ 
        return pack;
    }

    public void addToPack(Item displayableItem){  // every item in the pack needs a NUMBER and a CHARACTER
        // ObjectDisplayGrid displayGrid = getGrid();
        pack.add(displayableItem);
        // displayGrid.displayInfo("There is no item under you to pick up.");
        // might want to print something to the info like whenever you add to the pack !
    }

    public void pickUpItem(int x, int y){
        ObjectDisplayGrid displayGrid = getGrid();
        // check if there is an item under the player
        displayGrid.removeObjectFromDisplay(x,y); // removes player from the top
        char itemUnder = displayGrid.getObjectFromDisplay(x,y); // returns the char for itemUnder
        displayGrid.addObjectToDisplay(player,x,y); // push the player BACK on top

        boolean isItem = (itemUnder == '?') | (itemUnder == ')' ) | (itemUnder == ']') ;
        if(isItem){ // if there IS an item under the player, we want to add it to the pack
            displayGrid.removeObjectFromDisplay(x,y);   // remove the player
            Item displayableItem = (Item) displayGrid.getDisplayableFromDisplay(x,y); // store the displayableItem from the grid
            displayGrid.removeObjectFromDisplay(x,y);   // now that the item is stored, remove it from the grid
            displayGrid.addObjectToDisplay(player,x,y); // push the player BACK on top
            displayGrid.updatePackLine(pack); // update the pack line

            pack.add(displayableItem); // itemUnder is a char 
        }
        else{
            // whatever is under the player is NOT an item. leave it there
            displayGrid.displayInfo("There is no item under you to pick up.");
        }
    }

    private int verifyMove(int offsetX, int offsetY){ 
        ObjectDisplayGrid displayGrid = ObjectDisplayGrid.getObjectDisplayGrid(-1, -1);
        
        int newX = posX + offsetX; // calculate the new position they want to move to
        int newY = posY + offsetY;
        // check what is at that position
        char ch = displayGrid.getObjectFromDisplay(newX, newY);

        if ( (ch == 'X') | (ch == ' ') ){
            return 0; // not allowed to move into a wall or blank space
        }
        else 
            return 1; // allowed to move to said position
    }

    private int verifyMonster(int offsetX, int offsetY){ 
        ObjectDisplayGrid displayGrid = getGrid();
        
        int newX = posX + offsetX; // calculate the new position they want to move to
        int newY = posY + offsetY;
        // check what is at that position
        char ch = displayGrid.getObjectFromDisplay(newX, newY);

        if ( (ch == 'T') | (ch == 'S') | (ch == 'H') ){ 
            // there is a monster there! and the player just hit it. fight:
            Monster monster = (Monster) displayGrid.getDisplayableFromDisplay(newX, newY) ; //get the WHOLE DISPLAYABLE, not jsut the char

        // so they hit each other
            Random random = new Random(); // int value = random.nextInt(max - min) + min;
            int damageFromMonster = random.nextInt(monster.getMaxHit()+ 1 );  // damage the monster inflicts
            int damageFromPlayer = random.nextInt(player.getMaxHit()+1 );// damage the player inflicts
            
            if (sword != null){ // if the player wields a sword: compute the value of the hit and add the value of the sword to that. 
                damageFromPlayer = damageFromPlayer + sword.getItemIntValue();
            } // else do nothing, no change in the damageFromPlayer / on the Monster 

        // assess/show the damage it did
            player.updateHp(damageFromMonster); // update the player's health points, with damage from monster
            monster.updateHp(damageFromPlayer);
            
            // display the hit damage
            displayGrid.displayHitInfo("Player", damageFromPlayer, monster.getName(), damageFromMonster); // player attacking creature

            // check if ether was killed --> execute hit or death actions.
            player.checkHp(player, monster); // checkHp( Creature killedCreature, Creature attackingCreature )
            monster.checkHp(monster, player);
        
            return 0; // not allowed to move into the monster. can only fight it
        }
        else // no monster there, free to move to the location
            return 1; // allowed to move to said position
    }

    public void move(int offsetX, int offsetY){ // ??
        ObjectDisplayGrid displayGrid = getGrid();
        Player player = Player.getPlayer();
        
        // if (hallucinate.isOn() == 1){
               
        // }

        int verifyMove = verifyMove(offsetX, offsetY); 
        int verifyMonster = verifyMonster(offsetX, offsetY);
            
        if ( verifyMove + verifyMonster == 2){ // if both are true, safe to move there
            displayGrid.removeObjectFromDisplay(posX, posY); // remove player from the old spot
            
            // determine the new position for the Player
            setPosX(posX + offsetX); // updated setPosX
            setPosY(posY + offsetY); // updated setPosY
            displayGrid.addObjectToDisplay(player, posX , posY); // update the player's position (on the screen)
        }
        else {
            // do nothing, player can't move into a boundary / on a monster
        }
    }

    @Override 
    // Every time we DRAW the @, we have to remove it from the previous position, and add it to the new position
    public void draw(ObjectDisplayGrid objectGrid){ // how to properly use objectDisplayGrid in here
        objectGrid.addObjectToDisplay(player, posX, posY);
    }

    public void dropItem(int numPressed, int x, int y){
        int packIndex = numPressed - 1;
        if ( ( numPressed>= 1 ) & ( numPressed <= player.getPack().size() ) ){ // if it's in the range -> Proceed
            
            Item droppedItem = pack.get(packIndex); // Displayable item
            char charItem = pack.get(packIndex).getCharacter(); // char item (character-representation)
            // we want to drop a whole displayable, so that a Displayable item can be picked up (and all attributes attached)

            // set attributes of the item that's about to be dropped
            droppedItem.setPosX(x);
            droppedItem.setPosY(y);

            // if the dropped item THAT IS ABOUT TO BE DROPPED is a sword or weapon:
            if (sword != null) { // if the player wields a sword
                if ((pack.get(packIndex)) instanceof Sword) { // if the item we drop IS a sword ?? will this even return Sword?? cause its an Item
                    Sword droppedSword  = (Sword) pack.get(packIndex);
                    if(droppedSword.getIsUsed() == 1){ // if the player is dropping its WIELDED sword
                        hasSword = 0; // player has dropped their sword
                        droppedSword.setIsUsed(0); // this function affects the actual property of the sword (if it's held)
                        sword = null; // this sword is just the sword the player holds
                        displayGrid.displayInfo("The Sword being wielded was dropped and removed from your pack." );
                    }
                }
            }
            else if (armor != null) { // if the player wears armor
                if ((pack.get(packIndex)) instanceof Armor) { // if the item we drop IS an armor
                    Armor droppedArmor = (Armor) pack.get(packIndex);
                    if(droppedArmor.getIsUsed() == 1){ // if the player is dropping its WORN armor
                        droppedArmor.setIsUsed(0); // this function affects the actual property of the sword (if it's held)
                        hasArmor = 0; // player has dropped their armor
                        armor = null;
                        displayGrid.displayInfo("The Armor being worn was dropped and removed from your pack." );
                    }
                }
            }
            else{ // the item dropped wasn't a WIELDED Sword OR a WORN ARMOR. Just a regular item dropped from the pack
                displayGrid.displayInfo("A "+ droppedItem.getName() + " was dropped from your pack." );
            }

            // NOW we can drop the item
            displayGrid.removeObjectFromDisplay(x, y); // removes the player
            displayGrid.addObjectToDisplay(droppedItem, x, y); // add THE DISPLAYABLE to the grid (push onto stack)
            displayGrid.addObjectToDisplay(player,x,y); // push the player on top lol
            pack.remove(packIndex);// remove item from pack
            displayGrid.updatePackLine(pack); // update the pack line

        }
        
        else{ // index pressed is NOT in the pack range
            displayGrid.displayInfo("The number ("+ numPressed + ") isn't in the pack range.");
        }
    }

    public void countMoves(){
        // updates the number of moves, until the player reaches hpMpves/hpMax
        // where in it gets it's player.Hp updated!
         // incase we need to update HP on the grid
        countMoves++;
        if (countMoves == hpMoves){
            countMoves = 0;             // reset
            hp++;                       // player health points (HP) increases by 1
            displayGrid.displayHP(hp);  // update on the display grid 
        }
    }

    // the Player's HP is updated differently. The damage it takes depends on the armor is has on 
    @Override
    public void updateHp(int damageFromCreature){
        // hp is updated DEPENDING on the armor the player wears!!
        if ( armor != null) { 
            hp = hp - damageFromCreature + armor.getItemIntValue(); // player takes less of a beating with the armor
        }
        else {
            hp = hp - damageFromCreature;
        }
    }

    public void readScroll(int numPressed){
        // in order for the number that the user pressed --> to correllate to pack index --> numPressed - 1
        int packIndex = numPressed - 1;
        if ( ( numPressed>= 1 ) & ( numPressed <= player.getPack().size() ) ){ // if it's in the range -> Proceed            
            // Check that the item the player requested to read IS A SCROLL
            if ( pack.get(packIndex) instanceof Scroll ){
                Scroll selectedScroll = (Scroll) pack.get(packIndex); 
                selectedScroll.executeItemActions();
                pack.remove(packIndex);// remove item from pack
                displayGrid.updatePackLine(pack); // update the pack line
            }
            else{
                displayGrid.displayInfo("The item you selected to read, 'r', is not a scroll");
            }
        }
        else{
            displayGrid.displayInfo("Number pressed does not correlate to an item in the pack.");
        }
    }

    public void useSword(int numPressed){
        // in order for the number that the user pressed --> to correllate to pack index --> numPressed - 1
        int packIndex = numPressed - 1;
        if ( ( numPressed>= 1 ) & ( numPressed <= player.getPack().size() ) ){ // if it's in the range -> Proceed            
            // Check that the item the player requested to read IS A SCROLL
            if ( pack.get(packIndex) instanceof Sword ){
                Sword selectedSword = (Sword) pack.get(packIndex); 
                selectedSword.setIsUsed(1); // this function affects the actual property of the sword ((1 == stating it's being wielded)
                hasSword = 1; // player has dropped their sword
                sword = selectedSword; // this sword is NOW the sword the player holds
                displayGrid.displayInfo("The Sword at position "+(numPressed)+" in the pack is being wielded." );              
                displayGrid.updatePackLine(pack); // update the pack line
            }
            else{
                displayGrid.displayInfo("The item you selected to wield, 't', is not a sword");
            }
        }
        else{
            displayGrid.displayInfo("Number pressed does not correlate to an item in the pack.");
        }
    }

    public void wearArmor(int numPressed){
        // in order for the number that the user pressed --> to correllate to pack index --> numPressed - 1
        int packIndex = numPressed - 1;
        if ( ( numPressed>= 1 ) & ( numPressed <= player.getPack().size() ) ){ // if it's in the range -> Proceed            
            // Check that the item the player requested to read IS A SCROLL
            if ( pack.get(packIndex) instanceof Armor ){
                
                if (armor != null) { // if the player IS ALREADY WEARING armor:
                    // remove that old armor before placing on the new armor
                    armor.setIsUsed(0); // this function affects the actual property of the armor (1 == stating it's being worn)
                    hasArmor = 0; // player wears armor
                    armor = null; 
                }
                
                Armor selectedArmor = (Armor) pack.get(packIndex); 
                selectedArmor.setIsUsed(1); // this function affects the actual property of the armor (1 == stating it's being worn)
                hasArmor = 1; // player wears armor
                armor = selectedArmor; // this armor is NOW the armor the player wears
                displayGrid.displayInfo("The Armor at position "+(numPressed)+" in the pack is being worn." );                
                displayGrid.updatePackLine(pack); // update the pack line
                
            }
            else{
                displayGrid.displayInfo("The item you selected to wear, 'w', is not armor");
            }
        }
        else{
            displayGrid.displayInfo("Number pressed does not correlate to an item in the pack.");
        }
    }
}