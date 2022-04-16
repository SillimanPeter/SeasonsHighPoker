package edu.up.cs301.SeasonsHigh.Actions;


import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHActionHold extends SHActionMove {

    private static final long serialVersionUID = 8L;

    /**
     * Constructor for the SHActionMove class.
     *
     * @param player  the player making the move
     */
    public SHActionHold(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Hold" move
     */
    public boolean isHold() { return true; }
}
