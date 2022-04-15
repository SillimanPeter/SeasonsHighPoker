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
        if (playerIdx < 0 || playerIdx > 2 ||
                SHGS.getPlayersArray()[playerIdx].getFolded()) {
            // if our player-number is out of range, return false
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

        // check that we have slap-jack action; if so cast it
        if (!(action instanceof SHActionMove)) {
            return false;
        }
        SHActionMove sham = (SHActionMove) action;

        // get the index of the player making the move; return false
        int thisPlayerIdx = getPlayerIdx(sham.getPlayer());
        //to clean up code a bit
        Player p = SHGS.getPlayersArray()[thisPlayerIdx];

        if (thisPlayerIdx < 0 || thisPlayerIdx > 2) { // illegal player
            return false;
        }

        if(sham.isDraw()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")) {
                return false;
            }
            for(int i = 0; i < 4; i++){
                if(p.getHand()[i].getIsSelected()){
                    p.getHand()[i] = null; //removes card from hand
                    p.getHand()[i] = SHGS.draw(); //draws new card
                    p.getHand()[i].setIsDealt(true);
                }
            }
        }

        else if(sham.isHold()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                return false;
            } else {
                //do nothing
            }
        }

        else if(sham.isCard0Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                return false;
            } else {
                p.getHand()[0].toggleIsSelected();
            }
        }

        else if(sham.isCard1Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                return false;
            } else {
                p.getHand()[1].toggleIsSelected();
            }
        }

        else if(sham.isCard2Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                return false;
            } else {
                p.getHand()[2].toggleIsSelected();
            }
        }

        else if(sham.isCard3Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                return false;
            } else {
                p.getHand()[3].toggleIsSelected();
            }
        }

        else if (sham.isBet()) {
            if(!SHGS.getCurrentPhase().equals("Bet-Phase")) {
                return false;
            }
            //is the players balance greater than or equal to the bet value?
            else if (p.getBalance() < SHGS.getCurrentBet()) {
                return false;
            }
            //is the bet value greater than or equal to current bet?
            else if (p.getLastBet() > SHGS.getCurrentBet()) {
                return false;
            } else {
                //commits bet made
                SHGS.setCurrentBet(p.getCurrentBet());
                SHGS.setPotBalance(SHGS.getPotBalance() + p.getCurrentBet());
                p.setLastBet(p.getCurrentBet());
            }

        }

        else if(sham.isFold()) {
            SHGS.getPlayersArray()[thisPlayerIdx].toggleFolded();
        }

        else { // some unexpected action
            return false;
        }

        //changes whose turn it is
        SHGS.getPlayersArray()[thisPlayerIdx].toggleIsTurn();
        int nextPlayerIdx = thisPlayerIdx++;
        if(thisPlayerIdx == 2){
             nextPlayerIdx = 0;
        }
        SHGS.getPlayersArray()[nextPlayerIdx].toggleIsTurn();

        return true;
    }//makeMove

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
