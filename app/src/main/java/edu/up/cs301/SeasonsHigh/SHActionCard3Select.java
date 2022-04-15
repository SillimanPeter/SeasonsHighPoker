package edu.up.cs301.SeasonsHigh;

import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the Card3Select action
 *
 * @author Peter S. Silliman
 */
public class SHActionCard3Select extends SHActionMove {

    private static final long serialVersionUID = 0L;

    /**
     * Constructor for the SHActionCard3Select class.
     *
     * @param player  the player making the move
     */
    public SHActionCard3Select(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Card3Select" move
     */
    public boolean isCard3Select() { return true; }

}