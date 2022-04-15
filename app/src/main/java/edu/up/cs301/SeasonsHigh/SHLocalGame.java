package edu.up.cs301.SeasonsHigh;

import android.util.Log;



import java.util.List;

import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHLocalGame extends LocalGame {
    //initializing variables
    private int playersTurnId;
    //variable names with "golden" in them are a pointer to the goldenGameState variables
    private SHState SHGS;
    private Player[] goldenPlayers;
    private List<Card> goldenDeck;
    //creates a different copy for each player
    //TODO: turn into an array with length players.size()
    private List<SHState> playerCopies;

    /**
     * declares the goldenGameState and creates a copy that excludes the hand for each
     */
    public SHLocalGame(){

        //declare base variables
        this.SHGS = new SHState();
        this.goldenDeck = this.SHGS.getDeckArray();
        this.goldenPlayers = this.SHGS.getPlayersArray();
        //sets who the first player
        this.playersTurnId = 0;
        this.SHGS.getPlayersArray()[0].toggleIsTurn();

        //populates playerCopies with copies of the gameState excluding other players' hands
        for(int n = 0; n < goldenPlayers.length; n++){
            SHState playerGSCopy = new SHState(SHGS);
            //removes other player's hands
            for(int m = 0; m < playerGSCopy.getPlayersArray().length; m++) {
                for(int b = 0; b < goldenPlayers.length; b++){
                    if(b != n){ //skips the player who's gameStateCopy this is
                        //clears the player's hand
                        playerGSCopy.getPlayersArray()[b].getHand()[m] = null;
                    }
                }
            }
            this.playerCopies.add(playerGSCopy);
        }

    }

    public SHLocalGame(SHState initState) {
        Log.i("SJLocalGame", "creating game");
        // create the state for the beginning of the game
        this.SHGS = initState;
        super.state = initState;
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if(SHGS.getPlayerTurnId() == playerIdx){
            return true;
        }
        return false;
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //TODO: this method
        return true;
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
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
