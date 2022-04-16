package edu.up.cs301.SeasonsHigh.Actions;


import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHActionDraw extends SHActionMove {

    private static final long serialVersionUID = 9L;

    /**
     * Constructor for the SHActionMove class.
     *
     * @param player  the player making the move
     */
    public SHActionDraw(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "Draw" move
     */
    public boolean isDraw() { return true; }
}
