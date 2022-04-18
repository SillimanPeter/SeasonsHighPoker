package edu.up.cs301.SeasonsHigh;



import android.graphics.Color;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * This is the primary activity for SeasonsHigh
 */
public class SHMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 4752;

    /** a slapjack game for two players. The default is human vs. computer */
    @Override

    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("human player") {
            public GamePlayer createPlayer(String name) {
                return new SHHumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("computer player (pro)") {
            public GamePlayer createPlayer(String name) {
                return new SHComputerPlayer(name, true, 1);
            }});
        playerTypes.add(new GamePlayerType("computer player (noob)") {
            public GamePlayer createPlayer(String name) {
                return new SHComputerPlayer(name, false, 2);
            }});

        // Create a game configuration class for SlapJack
        GameConfig defaultConfig = new GameConfig(playerTypes, 3, 3, "SeasonsHigh", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 2);
        defaultConfig.addPlayer("Computer", 1);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Guest", "", 1);

        //done!
        return defaultConfig;
    }//createDefaultConfig

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null) {
            gameState = new SHState();
        }

        return new SHLocalGame((SHState) gameState);
    }

}