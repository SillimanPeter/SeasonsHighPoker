package edu.up.cs301.SeasonsHigh.Actions;


import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHActionFold extends SHActionMove {

    private static final long serialVersionUID = 2L;

    /**
     * Constructor for the SHActionMove class.
     *
     * @param player  the player making the move
     */
    public SHActionFold(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Fold" move
     */
    public boolean isFold() { return true; }
}
