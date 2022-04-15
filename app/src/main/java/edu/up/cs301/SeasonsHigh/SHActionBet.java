package edu.up.cs301.SeasonsHigh;


import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the bet, raise, check, and call actions as all have very similar functionality
 *
 * @author Peter S. Silliman
 */
public class SHActionBet extends SHActionMove {

    private static final long serialVersionUID = 0L;

    /**
     * Constructor for the SHActionMove class.
     *
     * @param player  the player making the move
     */
    public SHActionBet(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "BetRaiseCheckCall" move
     */
    public boolean isBetRaiseCheckCall() { return true; }

}
