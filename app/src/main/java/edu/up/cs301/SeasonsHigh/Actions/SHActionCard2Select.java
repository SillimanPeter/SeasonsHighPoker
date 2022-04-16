package edu.up.cs301.SeasonsHigh.Actions;

import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the Card2Select action
 *
 * @author Peter S. Silliman
 */
public class SHActionCard2Select extends SHActionMove {

    private static final long serialVersionUID = 0L;

    /**
     * Constructor for the SHActionCard2Select class.
     *
     * @param player  the player making the move
     */
    public SHActionCard2Select(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Card2Select" move
     */
    public boolean isCard2Select() { return true; }

}