package edu.up.cs301.SeasonsHigh.Actions;


import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * A game-move object that a tic-tac-toe player sends to the game to make
 * a move.
 *
 * @author Steven R. Vegdahl
 * @version 2 July 2001
 */
public abstract class SHActionMove extends GameAction {

    private static final long serialVersionUID = -3L;

    /**
     * Constructor for SJMoveAction
     *
     * @param player the player making the move
     */
    public SHActionMove(GamePlayer player)
    {
        // invoke superclass constructor to set source
        super(player);
    }

    /**
     * @return
     * 		whether the move was a "Hold"
     */
    public boolean isHold(){ return false; }

    /**
     * @return
     * 		whether the move was a "ChangeBetValue"
     */
    public boolean isChangeBetValue(){ return false; }

    /**
     * @return
     * 		whether the move was a "Fold"
     */
    public boolean isFold(){ return false; }

    /**
     * @return
     * 		whether the move was a "Draw"
     */
    public boolean isDraw(){ return false; }

    /**
     * @return
     * 		whether the move was a "Card0Select"
     */
    public boolean isCard0Select(){ return false; }

    /**
     * @return
     * 		whether the move was a "Card1Select"
     */
    public boolean isCard1Select(){ return false; }

    /**
     * @return
     * 		whether the move was a "Card2Select"
     */
    public boolean isCard2Select(){ return false; }

    /**
     * @return
     * 		whether the move was a "Card3Select"
     */
    public boolean isCard3Select(){ return false; }

    /**
     * @return
     * 		whether the move was a "Bet", "Raise", "Check" or "Call"
     * 	(each do very similar things so they are combined into one action)
     */
    public boolean isBet(){ return false; }

}
