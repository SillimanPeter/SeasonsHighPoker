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

    /**
     * Constructor for the SHComputerPlayer class; creates an "average"
     * player.
     *
     * @param name
     * 		the player's name
     */
    public SHComputerPlayer(String name) {
        // invoke general constructor to create player whose average reaction
        // time is half a second.
        this(name, false);
    }

    /**
     * Constructor for the SHComputerPlayer class
     */
    public SHComputerPlayer(String name, boolean intelligence) {
        // invoke superclass constructor
        super(name);

        isSmart = intelligence;
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
        savedState = (SHState)info;

        /**Create the computerPlayer brain here (what moves to make when)*/
        //Pro AI brain here
        if(isSmart) {
            // if it's not their turn do nothing else make a move depending
            //      on the phase of the game
            if (savedState.getPlayerTurnId() != this.playerNum) {
                //do nothing if not their turn
            } else if (savedState.getPlayerTurnId() == this.playerNum) {
                if(savedState.getCurrentPhase() == "Betting-Phase"){

//                    if(move <= 1) {
//                        sleep(2.0 * Math.random());
//                        game.sendAction(new SHActionCard0Select(this));
//                        game.sendAction(new SHActionBet(this));
//                        Log.d("Computer sendAction", "Bet Action");
//                    }else{
//                        sleep(2.0 * Math.random());
//                        game.sendAction(new SHActionFold(this));
//                        Log.d("Computer sendAction", "Fold Action");
//                    }
//                }else if(savedState.getCurrentPhase() == "Drawing-Phase"){
//                    if(move == 0){
//                        sleep(2.0 * Math.random());
//                        game.sendAction((new SHActionHold(this)));
//                        Log.d("Computer sendAction", "Hold Action");
//                    }else if (move == 1){
//                        sleep(2.0 * Math.random());
//                        game.sendAction(new SHActionFold(this));
//                        Log.d("Computer sendAction", "Fold Action");
//                    }else if(move > 1){
//                        sleep(2.0 * Math.random());
//                        game.sendAction((new SHActionDraw(this)));
//                        Log.d("Computer sendAction", "Draw Action");
//                    }
                }else{
                    sleep(2.0 * Math.random());
                    game.sendAction(new SHActionFold(this));
                    Log.d("Computer sendAction", "Fold Action");
                }
            }
        }
        //Noob AI brain here
        else {
            // if it's not their turn do nothing else make a move depending
            //      on the phase of the game
            if (savedState.getPlayerTurnId() != this.playerNum) {
                //do nothing if not their turn
            } else if (savedState.getPlayerTurnId() == this.playerNum) {
                Random gen = new Random();
                int move = gen.nextInt(4);

                if(savedState.getCurrentPhase() == "Betting-Phase"){
                    if(move <= 1) {
                        sleep(2.0 * Math.random());
                        game.sendAction(new SHActionCard0Select(this));
                        game.sendAction(new SHActionBet(this));
                        Log.d("Computer sendAction", "Bet Action");
                    }else{
                        sleep(2.0 * Math.random());
                        game.sendAction(new SHActionFold(this));
                        Log.d("Computer sendAction", "Fold Action");
                    }
                }else if(savedState.getCurrentPhase() == "Drawing-Phase"){
                    if(move == 0){
                        sleep(2.0 * Math.random());
                        game.sendAction((new SHActionHold(this)));
                        Log.d("Computer sendAction", "Hold Action");
                    }else if (move == 1){
                        sleep(2.0 * Math.random());
                        game.sendAction(new SHActionFold(this));
                        Log.d("Computer sendAction", "Fold Action");
                    }else if(move > 1){
                        sleep(2.0 * Math.random());
                        game.sendAction((new SHActionDraw(this)));
                        Log.d("Computer sendAction", "Draw Action");
                    }
                }else{
                    sleep(2.0 * Math.random());
                    game.sendAction(new SHActionFold(this));
                    Log.d("Computer sendAction", "Fold Action");
                }

            }
        }
    }
}
