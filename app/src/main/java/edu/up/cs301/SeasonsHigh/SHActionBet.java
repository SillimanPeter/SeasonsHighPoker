package edu.up.cs301.SeasonsHigh;


import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * handles the bet action
 *
 * @author Peter S. Silliman
 */
public class SHActionBet extends SHActionMove {

    private static final long serialVersionUID = 0L;

    private int betAmount; // amount to bet

    /**
     * Constructor for the SHActionBet class.
     *
     * @param player  the player making the move
     */
    public SHActionBet(GamePlayer player, int amount) {
        super(player);
        betAmount = amount;
    }

    /**
     * @return whether this action is a "Bet" move
     */
    public boolean isBetRaiseCheckCall() { return true; }

    public int getBetAmount() {
        return betAmount;
    }
}
