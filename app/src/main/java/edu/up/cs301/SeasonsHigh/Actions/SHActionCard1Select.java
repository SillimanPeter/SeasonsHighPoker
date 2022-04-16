package edu.up.cs301.SeasonsHigh.Actions;

import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the Card1Select action
 *
 * @author Peter S. Silliman
 */
public class SHActionCard1Select extends SHActionMove {

    private static final long serialVersionUID = 0L;

    /**
     * Constructor for the SHActionCard1Select class.
     *
     * @param player  the player making the move
     */
    public SHActionCard1Select(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Card1Select" move
     */
    public boolean isCard1Select() { return true; }

}