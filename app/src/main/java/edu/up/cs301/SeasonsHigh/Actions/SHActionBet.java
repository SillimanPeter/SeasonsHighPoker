package edu.up.cs301.SeasonsHigh.Actions;


import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the bet action
 *
 * @author Peter S. Silliman
 */
public class SHActionBet extends SHActionMove {

    private static final long serialVersionUID = 0L;

    /**
     * Constructor for the SHActionBet class.
     *
     * @param player  the player making the move
     */
    public SHActionBet(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Bet" move
     */
    public boolean isBetRaiseCheckCall() { return true; }

}
