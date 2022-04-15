package edu.up.cs301.SeasonsHigh;


import java.util.Random;

import edu.up.cs301.card.Rank;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

public class SHComputerPlayer extends GameComputerPlayer {
    // the minimum reaction time for this player, in milliseconds
    private double minReactionTimeInMillis;

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
        this(name, 0.5);
    }

    /**
     * Constructor for the SHComputerPlayer class
     */
    public SHComputerPlayer(String name, double avgReactionTime) {
        // invoke superclass constructor
        super(name);

        // set the minimim reaction time, which is half the average reaction
        // time, converted to milliseconds (0.5 * 1000 = 500)
        minReactionTimeInMillis = 500*avgReactionTime;
    }

//    /**
//     * Invoked whenever the player's timer has ticked. It is expected
//     * that this will be overridden in many players.
//     */
//    @Override
//    protected void timerTicked() {
//        // we had seen a Jack, now we have waited the requisite time to slap
//
//        // look at the top card now. If it's still a Jack, slap it
//        Card topCard = savedState.getDeck(2).peekAtTopCard();
//        if (topCard != null && topCard.getRank() == Rank.JACK) {
//            // the Jack is still there, so submit our move to the game object
//            game.sendAction(new SJSlapAction(this));
//        }
//
//        // stop the timer, since we don't want another timer-tick until it
//        // again is explicitly started
//        getTimer().stop();
//    }

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

        /**TODO create the computerPlayer brain here (what moves to make when)*/
        // access the state's middle deck


        // look at the top card


        // if it's not their turn do nothing else make a move depending
        //  on the phase of the game
        if (savedState.getPlayerTurnId() != this.playerNum) {
            //do nothing if not their turn
        }
       else if (savedState.getPlayerTurnId() == this.playerNum) {
            Random gen = new Random();
            int move = gen.nextInt(4);

            if(savedState.getCurrentPhase() == "Betting-Phase"){
                if(move <= 1) {
                    sleep(2.0 * Math.random());
                    game.sendAction(new SHActionBet(this));
                }else{
                    sleep(2.0 * Math.random());
                    game.sendAction(new SHActionFold(this));
                }
            }else if(savedState.getCurrentPhase() == "Drawing-Phase"){
                if(move == 0){
                    sleep(2.0 * Math.random());
                    game.sendAction((new SHActionHold(this)));
                }else if (move == 1){
                    sleep(2.0 * Math.random());
                    game.sendAction(new SHActionFold(this));
                }else if(move > 1){
                    sleep(2.0 * Math.random());
                    game.sendAction((new SHActionDraw(this)));
                }
            }else{
                    sleep(2.0 * Math.random());
                    game.sendAction(new SHActionFold(this));
            }

        }
    }
}
