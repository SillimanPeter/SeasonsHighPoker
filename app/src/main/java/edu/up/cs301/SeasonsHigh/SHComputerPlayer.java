package edu.up.cs301.SeasonsHigh;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

public class SHComputerPlayer extends GameComputerPlayer {
    // the minimum reaction time for this player, in milliseconds
    private boolean isSmart;

    // the most recent state of the game
    private SHState savedState;

    private int id;

    /**
     * Constructor for the SHComputerPlayer class; creates an "average"
     * player.
     *
     * @param name
     * 		the player's name
     */
    public SHComputerPlayer(String name, int id) {
        // invoke general constructor to create player whose average reaction
        // time is half a second.
        this(name, false, id);
    }

    /**
     * Constructor for the SHComputerPlayer class
     */
    public SHComputerPlayer(String name, boolean intelligence, int pId) {
        // invoke superclass constructor
        super(name);

        isSmart = intelligence;
        this.id = pId;
    }

    /**
     * callback method, called when we receive a message, typicallly from
     * the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // if we don't have a game-state, ignore
        if (!(info instanceof SHState)) {
            return;
        }

        // update our state variable
        this.savedState = (SHState)info;

        //helper variable
        int handStrength = this.savedState.getHandStrength(this.id); /*this ai's id*/


        /**Create the computerPlayer brain here (what moves to make when)*/

        //Pro AI brain here
        if(this.isSmart) {
            // if it's not their turn do nothing else make a move depending
            //      on the phase of the game
            if (this.savedState.getPlayerTurnId() != this.playerNum) {
                //do nothing if not their turn
            } else if (this.savedState.getPlayerTurnId() == this.playerNum) {
                //betting phase actions
                if(this.savedState.getCurrentPhase() == "Betting-Phase"){
                    if(this.savedState.getPlayersArray()[id].getBalance()
                            > this.savedState.getCurrentBet() + 10 && handStrength > 1010) {
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Bet Action");
                        //bet the minimum amount to stay in the game
                        this.savedState.getPlayersArray()[id]
                                .setCurrentBet(this.savedState.getCurrentBet() + 10);
                        this.game.sendAction(new SHActionBet(this));
                    } //makes a bet if they have a great hand
                    else if(this.savedState.getPlayersArray()[id].getBalance() > this.savedState.getCurrentBet()
                        && handStrength % 1000 > 100 && handStrength % 100 > 10)
                    { //if hand is better than 10 pair
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Bet Action");
                        //bet the minimum amount to stay in the game
                        this.savedState.getPlayersArray()[id]
                                .setCurrentBet(this.savedState.getCurrentBet());
                        this.game.sendAction(new SHActionBet(this));
                    } //makes a bet if they have a decent hand
                    else {
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Fold Action");
                        this.game.sendAction(new SHActionFold(this));
                    } //folds if they have a bad hand
                }

                //draw phase actions
                else if(this.savedState.getCurrentPhase() == "Drawing-Phase"){
                    if(handStrength >= 1000){ //if has seasoned
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Hold Action");
                        this.game.sendAction((new SHActionHold(this)));
                    } else {
/**TODO:find out which cards are the same suit and send cardSelectAction for the lower valued card*/
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Draw Action");
                        this.game.sendAction(new SHActionCard0Select(this));
                        this.game.sendAction((new SHActionDraw(this)));
                    }
                }
            }
        }

        //Noob AI brain here
        else {
            // if it's not their turn do nothing else make a move depending
            //      on the phase of the game
            if (this.savedState.getPlayerTurnId() != this.playerNum) {
                //do nothing if not their turn
            } else if (this.savedState.getPlayerTurnId() == this.playerNum) {
                //random int to dictate random actions
                Random gen = new Random();
                int move = gen.nextInt(4);
                //betting phase action
                if(this.savedState.getCurrentPhase() == "Betting-Phase"){
                    if(move <= 1) {
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Bet Action");
                        this.game.sendAction(new SHActionCard0Select(this));
                        this.game.sendAction(new SHActionBet(this));
                    } else {
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Fold Action");
                        this.game.sendAction(new SHActionFold(this));
                    }
                }
                //draw phase action
                else if(this.savedState.getCurrentPhase() == "Drawing-Phase"){
                    if(move == 0){
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Hold Action");
                        this.game.sendAction((new SHActionHold(this)));
                    }else if (move == 1){
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Fold Action");
                        this.game.sendAction(new SHActionFold(this));
                    }else if(move > 1){
                        sleep(2.0 * Math.random());
                        Log.d("Computer sendAction", "Attempting Draw Action");
                        this.game.sendAction((new SHActionDraw(this)));
                    }
                }
            }
        }

    }

}
