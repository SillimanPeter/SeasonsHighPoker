package edu.up.cs301.SeasonsHigh.Actions;

import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the Card0Select action
 *
 * @author Peter S. Silliman
 */
public class SHActionCard0Select extends SHActionMove {

    private static final long serialVersionUID = 0L;

    /**
     * Constructor for the SHActionCard0Select class.
     *
     * @param player  the player making the move
     */
    public SHActionCard0Select(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Card0Select" move
     */
    public boolean isCard0Select() { return true; }

}
