package edu.up.cs301.SeasonsHigh.Actions;

import edu.up.cs301.SeasonsHigh.SHActionMove;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHActionChangeBetValue extends SHActionMove {

    private static final long serialVersionUID = 9L;

    /**
     * Constructor for the SHActionChangeBetValue class.
     *
     * @param player  the player making the ChangeBetValue
     */
    public SHActionChangeBetValue(GamePlayer player) {
        super(player);
    }

    /**
     * @return whether this action is a "ChangeBetValue" move
     */
    public boolean isChangeBetValue() { return true; }
}
