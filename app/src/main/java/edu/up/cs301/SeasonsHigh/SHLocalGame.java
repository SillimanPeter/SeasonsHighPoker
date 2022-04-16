package edu.up.cs301.SeasonsHigh;

import android.util.Log;



import java.util.List;

import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHLocalGame extends LocalGame {

    //initializing variables
    private SHState SHGS;

    /**
     * declares the goldenGameState and creates a copy that excludes the hand for each
     */
    public SHLocalGame(){
        super();
        Log.i("SHLocalGame", "creating game");
        // create the state for the beginning of the game
        this.SHGS = new SHState();
        super.state = this.SHGS;
    }

    public SHLocalGame(SHState initState) {
        Log.i("SHLocalGame", "creating game");
        // create the state for the beginning of the game
        this.SHGS = initState;
        super.state = initState;
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        Log.d("CanMove","has been called");
        Log.d("Current Phase", "it is the " + SHGS.getCurrentPhase());
        if (playerIdx < 0 || playerIdx > 2
                || SHGS.getPlayerTurnId() != playerIdx
                || SHGS.getPlayersArray()[playerIdx].getFolded()) {
            // if our player-number is out of range or it is not this players turn
            //      or player folded, return false
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        Log.d("MakeMove","has been called");

        // check that we have slap-jack action; if so cast it
        if (!(action instanceof SHActionMove)) {
            return false;
        }
        SHActionMove sham = (SHActionMove) action;

        // get the index of the player making the move; return false
        int thisPlayerIdx = getPlayerIdx(sham.getPlayer());
        //to clean up code a bit
        Player p = SHGS.getPlayersArray()[thisPlayerIdx];

        if (thisPlayerIdx < 0 || thisPlayerIdx > 2
                || SHGS.getPlayerTurnId() != thisPlayerIdx
                || SHGS.getPlayersArray()[thisPlayerIdx].getFolded()) { // illegal player
            Log.d("no action made",
                 "Player tried to take an action on someone else's turn or after they folded");
            return false;
        }

        if(sham instanceof SHActionDraw){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")) {
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            }
            for(int i = 0; i < 4; i++){
                if(p.getHand()[i].getIsSelected()){
                    p.getHand()[i] = null; //removes card from hand
                    p.getHand()[i] = SHGS.draw(); //draws new card
                    p.getHand()[i].setIsDealt(true);
                    Log.d("Draw Action","player drew new cards");
                }
            }
        }

        else if(sham instanceof SHActionHold){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                //do nothing
                Log.d("Hold Action","player held");
            }
        }

        else if(sham instanceof SHActionCard0Select){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[0].toggleIsSelected();
                Log.d("Card0Select Action","was called and Card 0 was toggled");

            }
        }

        else if(sham instanceof SHActionCard1Select){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[1].toggleIsSelected();
                Log.d("Card1Select Action","was called and Card 1 was toggled");

            }
        }

        else if(sham instanceof SHActionCard2Select){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[2].toggleIsSelected();
                Log.d("Card2Select Action","was called and Card 2 was toggled");

            }
        }

        else if(sham instanceof SHActionCard3Select){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[3].toggleIsSelected();
                Log.d("Card3Select Action","was called and Card 3 was toggled");

            }
        }

        else if (sham instanceof SHActionBet) {
            if(!SHGS.getCurrentPhase().equals("Betting-Phase")) {
                Log.d("flash red","It must be the Betting-Phase for that action");
                return false;
            }
            //is the players balance greater than or equal to the bet value?
            else if (p.getBalance() < SHGS.getCurrentBet()) {
                Log.d("flash red","not enough balance for that action");
                return false;
            }
            //is the bet value greater than or equal to current bet?
            else if (p.getLastBet() > SHGS.getCurrentBet()) {
                Log.d("flash red","not high enough bet for that action");
                return false;
            } else {
                //commits bet made
                p.setCurrentBet(.getText()); //TODO: change currentBet of player
                SHGS.setCurrentBet(p.getCurrentBet());
                SHGS.setPotBalance(SHGS.getPotBalance() + p.getCurrentBet());
                p.setLastBet(p.getCurrentBet());
                Log.d("Bet Action","was made");
            }

        }

        else if(sham instanceof SHActionChangeBetValue){
            if(!SHGS.getCurrentPhase().equals("Betting-Phase")) {
                Log.d("flash red","It must be the Betting-Phase for that action");
                return false;
            } else {
                SHGS.getPlayersArray()[thisPlayerIdx].setCurrentBet(5/**TODO: Need to get text from the editText box*/);

                Log.d("ChangeBetValueAction", "player has changed their currentBet");
            }
        } //hard coded 5 should come from progress of seekbar

        else if(sham instanceof SHActionFold) {
            SHGS.getPlayersArray()[thisPlayerIdx].setFolded(true);
            Log.d("Fold Action","player has folded");
        }

        else { // some unexpected action
            return false;
        }

        //checks if a game phase change is needed and makes that change
        afterActionMade();

        //changes whose turn it is
        SHGS.getPlayersArray()[thisPlayerIdx].setIsTurn(false);

        int nextPlayerIdx = thisPlayerIdx++;
        if(thisPlayerIdx == 2){
            nextPlayerIdx = 0;
        }
        SHGS.getPlayersArray()[nextPlayerIdx].setIsTurn(true);
        Log.d("playerTurn","it is player " + nextPlayerIdx + "'s turn");

        return true;
    }//makeMove

    private void afterActionMade(){
        //find how many players have folded
        int numFolded = 0;
        for(int h = 0; h < SHGS.getPlayersArray().length; h++){
            if(SHGS.getPlayersArray()[h].getFolded()){
                numFolded ++;
            }
        }

        //find how many players have lost the game
        int haveLost = 0;
        for(Player i: SHGS.getPlayersArray()){
            if(i.getBalance() <= 5/*minimumBet*/){
                haveLost++;
            }
        }

        //declare winner and end game
        if(haveLost == SHGS.getPlayersArray().length - 1){
            //TODO: game is over, declare winner and end game
        }

        //checks if the betting phase is over, then changes it.
        int playersMatched = 0;
        for(int i = 0; i < SHGS.getPlayersArray().length; i++) { //loops through the players array
            if (SHGS.getPlayersArray()[i].getLastBet() == SHGS.getCurrentBet() //checks if player has called
                    && !SHGS.getPlayersArray()[i].getFolded()) {
                playersMatched ++;
            }
        }
        if(playersMatched == SHGS.getPlayersArray().length - numFolded){
            SHGS.changeGamePhase();
            Log.d("numFolded","" + numFolded);
            Log.d("numPlayersMatched","" + playersMatched);
            Log.d("currentBet","" + SHGS.getCurrentBet());
            Log.d("PlayersArrayLength","" + SHGS.getPlayersArray().length);
            Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
        }

        //checks if the drawing phase is over, then changes it.
        int playersHaveDrawnOrHeld = 0;
        for(int i = 0; i < SHGS.getPlayersArray().length; i++) {
            if (SHGS.getPlayersArray()[i].getHasDrawnOrHeld()
                    || !SHGS.getPlayersArray()[i].getFolded()) {
                playersHaveDrawnOrHeld ++;
            }
        }
        if(playersHaveDrawnOrHeld == SHGS.getPlayersArray().length - numFolded){
            SHGS.changeGamePhase();
            Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
        }

        //checks if the ante phase is over, draws players' cards then changes it
        int playersAnted = 0;
        if(SHGS.getCurrentPhaseLocation() == 0){
            for(int i = 0; i < SHGS.getPlayersArray().length; i++) {
                if (SHGS.getPlayersArray()[i].getLastBet() == SHGS.getCurrentBet()
                        || !SHGS.getPlayersArray()[i].getFolded()) {
                    playersAnted ++;
                }
            }
            if(playersAnted == SHGS.getPlayersArray().length - numFolded){
                for(int j = 0; j < SHGS.getPlayersArray().length; j++){
                    if(!SHGS.getPlayersArray()[j].getFolded()){
                        for(int handIndex = 0; handIndex < 4; handIndex++){
                            SHGS.getPlayersArray()[j].getHand()[handIndex] = SHGS.draw();
                        }
                    }
                }
                SHGS.changeGamePhase();
                Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
            }
        }

        //checks if the round is over, then reset hands, and give the winner the potBalance
        int winnerId = -1;
        if(SHGS.getCurrentPhaseLocation() == SHGS.getPhases().length - 1){
            //TODO: calculate who won by hand strengths

            //add pot balance to winner balance
            SHGS.getPlayersArray()[winnerId].addBalance(SHGS.getPotBalance());
            SHGS.setPotBalance(0);

            //resets all players' data for this round
            for(int k = 0; k < SHGS.getPlayersArray().length; k++){
                //resets all players hands to card backs
                for(int l = 0; l < SHGS.getPlayersArray()[k].getHand().length; l++){
                    SHGS.getPlayersArray()[k].getHand()[l] = new Card('c','b');
                }
                //resets lastBet
                SHGS.getPlayersArray()[k].setLastBet(0);
                //resets folded
                SHGS.getPlayersArray()[k].setFolded(false);
                //resets hasDrawnOrHeld
                SHGS.getPlayersArray()[k].setHasDrawnOrHeld(false);
                //resets current bet
                SHGS.getPlayersArray()[k].setCurrentBet(0);
            }

            for(Card allCards: SHGS.getDeckArray()){
                allCards.toggleIsSelected();
                allCards.setIsDealt(false);
            } //resets the deck

            Log.d("This round is over", SHGS.getPName(winnerId) + " has won this round");
            SHGS.changeGamePhase();
            Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
        }

    }

    public void compareHands(){

    }

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // if there is no state to send, ignore
        if (SHGS == null) {
            return;
        }
        // make a copy of the state; null out all cards except for the
        // top card in the middle deck
        SHState stateForPlayer = new SHState(SHGS); // copy of state

        // send the modified copy of the state to the player
        p.sendInfo(stateForPlayer);

        SHState playerGS = new SHState(SHGS);
        p.sendInfo(playerGS);
    }//sendUpdatedS

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        int haveLost = 0;
        String winnerName = "";
        for(Player i: SHGS.getPlayersArray()){
            if(i.getBalance() == 0){
                haveLost++;
            }
            else { winnerName = i.getName(); }
        }
        if(haveLost == SHGS.getPlayersArray().length - 1){
            return winnerName + " has won!";
        } else { return null;}
    }

}
