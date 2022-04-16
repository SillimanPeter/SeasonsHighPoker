package edu.up.cs301.SeasonsHigh;

import android.util.Log;


import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class SHLocalGame extends LocalGame {

    //initializing variables
    private SHState SHGS;

    /**
     * declares the goldenGameState and creates a copy that excludes the hand for each
     */
    public SHLocalGame(){
        super();
        Log.i("SHLocalGame", "creating game");
        // create the state for the beginning of the game
        this.SHGS = new SHState();
        super.state = this.SHGS;
    }

    public SHLocalGame(SHState initState) {
        Log.i("SHLocalGame", "creating game");
        // create the state for the beginning of the game
        this.SHGS = initState;
        super.state = initState;
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        Log.d("CanMove","has been called");
        Log.d("Current Phase", "it is the " + SHGS.getCurrentPhase());
        if (playerIdx < 0 || playerIdx > 2
                || SHGS.getPlayerTurnId() != playerIdx
                || SHGS.getPlayersArray()[playerIdx].getFolded()) {
            // if our player-number is out of range or it is not this players turn
            //      or player folded, return false
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        Log.d("MakeMove","has been called");

        // check that we have slap-jack action; if so cast it
        if (!(action instanceof SHActionMove)) {
            return false;
        }
        SHActionMove sham = (SHActionMove) action;

        // get the index of the player making the move; return false
        int thisPlayerIdx = getPlayerIdx(sham.getPlayer());
        //to clean up code a bit
        Player p = SHGS.getPlayersArray()[thisPlayerIdx];

        if (thisPlayerIdx < 0 || thisPlayerIdx > 2
                || SHGS.getPlayerTurnId() != thisPlayerIdx
                || SHGS.getPlayersArray()[thisPlayerIdx].getFolded()) { // illegal player
            Log.d("no action made",
                 "Player tried to take an action on someone else's turn or after they folded");
            return false;
        }

        if(sham.isDraw()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")) {
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            }
            for(int i = 0; i < 4; i++){
                if(p.getHand()[i].getIsSelected()){
                    p.getHand()[i] = null; //removes card from hand
                    p.getHand()[i] = SHGS.draw(); //draws new card
                    p.getHand()[i].setIsDealt(true);
                    Log.d("Draw Action","player drew new cards");
                }
            }
        }

        else if(sham.isHold()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                //do nothing
                Log.d("Hold Action","player held");
            }
        }

        else if(sham.isCard0Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[0].toggleIsSelected();
                Log.d("Card0Select Action","was called and Card 0 was toggled");

            }
        }

        else if(sham.isCard1Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[1].toggleIsSelected();
                Log.d("Card1Select Action","was called and Card 1 was toggled");

            }
        }

        else if(sham.isCard2Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[2].toggleIsSelected();
                Log.d("Card2Select Action","was called and Card 2 was toggled");

            }
        }

        else if(sham.isCard3Select()){
            if(!SHGS.getCurrentPhase().equals("Draw-Phase")){
                Log.d("flash red","It must be the Draw-Phase for that action");
                return false;
            } else {
                p.getHand()[3].toggleIsSelected();
                Log.d("Card3Select Action","was called and Card 3 was toggled");

            }
        }

        else if (sham.isBet()) {
            if(!SHGS.getCurrentPhase().equals("Betting-Phase")) {
                Log.d("flash red","It must be the Betting-Phase for that action");
                return false;
            }
            //is the players balance greater than or equal to the bet value?
            else if (p.getBalance() < SHGS.getCurrentBet()) {
                Log.d("flash red","not enough balance for that action");
                return false;
            }
            //is the bet value greater than or equal to current bet?
            else if (p.getLastBet() > SHGS.getCurrentBet()) {
                Log.d("flash red","not high enough bet for that action");
                return false;
            } else {
                //commits bet made
                p.setCurrentBet(SHGS.getCurrentBet());
                SHGS.setCurrentBet(p.getCurrentBet());
                SHGS.setPotBalance(SHGS.getPotBalance() + p.getCurrentBet());
                p.setLastBet(p.getCurrentBet());
                Log.d("Bet Action","was made");

            }

        }

        else if(sham instanceof SHActionFold) {
            SHGS.getPlayersArray()[thisPlayerIdx].setFolded(true);
            Log.d("Fold Action","player has folded");
        }

        else { // some unexpected action
            return false;
        }

        //checks if a game phase change is needed and makes that change
        afterActionMade();

        //changes whose turn it is
        SHGS.getPlayersArray()[thisPlayerIdx].setIsTurn(false);

        int nextPlayerIdx = thisPlayerIdx++;
        if(thisPlayerIdx == 2){
            nextPlayerIdx = 0;
        }
        SHGS.getPlayersArray()[nextPlayerIdx].setIsTurn(true);
        Log.d("playerTurn","it is player " + nextPlayerIdx + "'s turn");

        return true;
    }//makeMove

    private void afterActionMade(){
        //find how many players have folded
        int numFolded = 0;
        for(int h = 0; h < SHGS.getPlayersArray().length; h++){
            if(SHGS.getPlayersArray()[h].getFolded()){
                numFolded ++;
            }
        }

        //find how many players have lost the game
        int haveLost = 0;
        for(Player i: SHGS.getPlayersArray()){
            if(i.getBalance() <= 5/*minimumBet*/){
                haveLost++;
            }
        }

        //declare winner and end game
        if(haveLost == SHGS.getPlayersArray().length - 1){
            //TODO: game is over, declare winner and end game
        }

        //checks if the betting phase is over, then changes it.
        int playersMatched = 0;
        for(int i = 0; i < SHGS.getPlayersArray().length; i++) { //loops through the players array
            if (SHGS.getPlayersArray()[i].getLastBet() == SHGS.getCurrentBet() //checks if player has called
                    && !SHGS.getPlayersArray()[i].getFolded()) {
                playersMatched ++;
            }
        }
        if(playersMatched == SHGS.getPlayersArray().length - numFolded){
            SHGS.changeGamePhase();
            Log.d("numFolded","" + numFolded);
            Log.d("numPlayersMatched","" + playersMatched);
            Log.d("currentBet","" + SHGS.getCurrentBet());
            Log.d("PlayersArrayLength","" + SHGS.getPlayersArray().length);
            Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
        }

        //checks if the drawing phase is over, then changes it.
        int playersHaveDrawnOrHeld = 0;
        for(int i = 0; i < SHGS.getPlayersArray().length; i++) {
            if (SHGS.getPlayersArray()[i].getHasDrawnOrHeld()
                    || !SHGS.getPlayersArray()[i].getFolded()) {
                playersHaveDrawnOrHeld ++;
            }
        }
        if(playersHaveDrawnOrHeld == SHGS.getPlayersArray().length - numFolded){
            SHGS.changeGamePhase();
            Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
        }

        //checks if the ante phase is over, draws players' cards then changes it
        int playersAnted = 0;
        if(SHGS.getCurrentPhaseLocation() == 0){
            for(int i = 0; i < SHGS.getPlayersArray().length; i++) {
                if (SHGS.getPlayersArray()[i].getLastBet() == SHGS.getCurrentBet()
                        || !SHGS.getPlayersArray()[i].getFolded()) {
                    playersAnted ++;
                }
            }
            if(playersAnted == SHGS.getPlayersArray().length - numFolded){
                for(int j = 0; j < SHGS.getPlayersArray().length; j++){
                    if(!SHGS.getPlayersArray()[j].getFolded()){
                        for(int handIndex = 0; handIndex < 4; handIndex++){
                            SHGS.getPlayersArray()[j].getHand()[handIndex] = SHGS.draw();
                        }
                    }
                }
                SHGS.changeGamePhase();
                Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
            }
        }

        //checks if the round is over, then reset hands, and give the winner the potBalance
        int winnerId = -1;
        if(SHGS.getCurrentPhaseLocation() == SHGS.getPhases().length - 1){
            //TODO: calculate who won by hand strengths

            //add pot balance to winner balance
            SHGS.getPlayersArray()[winnerId].addBalance(SHGS.getPotBalance());
            SHGS.setPotBalance(0);

            //resets all players' data for this round
            for(int k = 0; k < SHGS.getPlayersArray().length; k++){
                //resets all players hands to card backs
                for(int l = 0; l < SHGS.getPlayersArray()[k].getHand().length; l++){
                    SHGS.getPlayersArray()[k].getHand()[l] = new Card('c','b');
                }
                //resets lastBet
                SHGS.getPlayersArray()[k].setLastBet(0);
                //resets folded
                SHGS.getPlayersArray()[k].setFolded(false);
                //resets hasDrawnOrHeld
                SHGS.getPlayersArray()[k].setHasDrawnOrHeld(false);
                //resets current bet
                SHGS.getPlayersArray()[k].setCurrentBet(0);
            }

            for(Card allCards: SHGS.getDeckArray()){
                allCards.toggleIsSelected();
                allCards.setIsDealt(false);
            } //resets the deck

            Log.d("This round is over", SHGS.getPName(winnerId) + " has won this round");
            SHGS.changeGamePhase();
            Log.d("phase change", "It is now the " + SHGS.getCurrentPhase());
        }

    }

    public int compareHands(){

        /** instantiate and declare helper variables */
        //creates copies of each players hand
        Card[] p0Cards = new Card[4];
        Card[] p1Cards = new Card[4];
        Card[] p2Cards = new Card[4];

        int[] pHandScores = new int[3];
        for(int score: pHandScores){
            score = 0;
        }

        boolean seasoned = true;
        boolean fourKind = true;
        boolean straight = false;
        boolean threeKind = false;
        int pairNum = 0;
        int handNum = 0;
        int numPairs = 0;

        //copy player hand array to new help arrays
        for(int i = 0; i < SHGS.getPlayersArray().length; i++){
            for(int h = 0; h < SHGS.getPlayersArray()[i].getHand().length; h++){
                if (i == 0) {
                    for(int handIndex = 0; handIndex < p0Cards.length; handIndex++){
                        p0Cards[handIndex] = new Card(SHGS.getPlayersArray()[i].getHand()[handIndex]);
                    }
                } else if (i == 1) {
                    for(int handIndex = 0; handIndex < p1Cards.length; handIndex++){
                        p1Cards[handIndex] = new Card(SHGS.getPlayersArray()[i].getHand()[handIndex]);
                    }
                } else { //i == 2
                    for(int handIndex = 0; handIndex < p2Cards.length; handIndex++){
                        p2Cards[handIndex] = new Card(SHGS.getPlayersArray()[i].getHand()[handIndex]);
                    }
                }
            }
        }

        //compare player 0's cards
        for(int pc = 0; pc < p0Cards.length; pc++){
            for(int pc2 = 0; pc2 < p0Cards.length; pc2++){
                if(pc < pc2){
                    if(p0Cards[pc].getSuit() != p0Cards[pc2].getSuit()){
                        seasoned = false;
                    }
                    if(p0Cards[pc].getValue() == p0Cards[pc2].getValue()){
                        if(pairNum == p0Cards[pc].getValue()){
                            if(threeKind) {
                                threeKind = false;
                                fourKind = true;
                            } else {
                                threeKind = true;
                            }
                        }
                        numPairs++;
                        pairNum = p0Cards[pc].getValue();
                    }
                }
            }
        }
        //add values relative to the hand to the player 0's handStrength
        if(seasoned){ pHandScores[0] += 100; } //seasoned

        if(fourKind){ pHandScores[0] += 50; } //four of a kind
        else if(straight){ pHandScores[0] += 40; } //straight
        else if(threeKind){ pHandScores[0] += 30; } //three of a kind
        else if(numPairs == 2){ pHandScores[0] += 20; } //two pair
        else if(numPairs == 1){ pHandScores[0] += 10; } //single pair

        pHandScores[0] += handNum; //handType rank

        //reset helper variables
        seasoned = true;
        fourKind = true;
        straight = false;
        threeKind = false;
        pairNum = 0;
        handNum = 0;
        numPairs = 0;

        //compare player 1's cards
        for(int pc = 0; pc < p1Cards.length; pc++){
            for(int pc2 = 0; pc2 < p1Cards.length; pc2++){
                if(pc < pc2){
                    if(p1Cards[pc].getSuit() != p1Cards[pc2].getSuit()){
                        seasoned = false;
                    }
                    if(p1Cards[pc].getValue() == p1Cards[pc2].getValue()){
                        if(pairNum == p1Cards[pc].getValue()){
                            if(threeKind) {
                                threeKind = false;
                                fourKind = true;
                            } else {
                                threeKind = true;
                            }
                        }
                        numPairs++;
                        pairNum = p1Cards[pc].getValue();
                    }
                }
            }
        }
        //add values relative to the hand to the player 0's handStrength
        if(seasoned){ pHandScores[1] += 100; } //seasoned

        if(fourKind){ pHandScores[1] += 50; } //four of a kind
        else if(straight){ pHandScores[1] += 40; } //straight
        else if(threeKind){ pHandScores[1] += 30; } //three of a kind
        else if(numPairs == 2){ pHandScores[1] += 20; } //two pair
        else if(numPairs == 1){ pHandScores[1] += 10; } //single pair

        pHandScores[1] += handNum; //handType rank

        //reset helper variables
        seasoned = true;
        fourKind = true;
        straight = false;
        threeKind = false;
        pairNum = 0;
        handNum = 0;
        numPairs = 0;

        //compare player 2's cards
        for(int pc = 0; pc < p2Cards.length; pc++){
            for(int pc2 = 0; pc2 < p2Cards.length; pc2++){
                if(pc < pc2){
                    if(p2Cards[pc].getSuit() != p2Cards[pc2].getSuit()){
                        seasoned = false;
                    }
                    if(p2Cards[pc].getValue() == p2Cards[pc2].getValue()){
                        if(pairNum == p1Cards[pc].getValue()){
                            if(threeKind) {
                                threeKind = false;
                                fourKind = true;
                            } else {
                                threeKind = true;
                            }
                        }
                        numPairs++;
                        pairNum = p1Cards[pc].getValue();
                    }
                }
            }
        }
        //add values relative to the hand to the player 0's handStrength
        if(seasoned){ pHandScores[2] += 100; } //seasoned

        if(fourKind){ pHandScores[2] += 50; } //four of a kind
        else if(straight){ pHandScores[2] += 40; } //straight
        else if(threeKind){ pHandScores[2] += 30; } //three of a kind
        else if(numPairs == 2){ pHandScores[2] += 20; } //two pair
        else if(numPairs == 1){ pHandScores[2] += 10; } //single pair

        pHandScores[2] += handNum; //handType rank


        /** find out which player (or players) have won*/
        int highestScorePlayerId = -1;//id of player with highest score
        int highestScore = 0;
        int highestScoreTieId = -1;//id of player that tied highest score (if tied)
        int highestScoreTie = 0;//needed to ensure there has been a tie
        for(int v = 0; v < pHandScores.length; v++){
            if(pHandScores[v] > highestScore && !SHGS.getPlayersArray()[v].getFolded()){
                highestScore = pHandScores[v];
                highestScorePlayerId = v;
            } else if(pHandScores[v] == highestScore && !SHGS.getPlayersArray()[v].getFolded()){
                highestScoreTieId = v;
            }
        }
        if(highestScore == highestScoreTie){
            //there has been a tie return both winners
            Log.d("The round is over", "Player " + highestScorePlayerId +
                    " and Player " + highestScoreTieId + "have Tied!");
        } else {
            Log.d("The round is over", "Player " + highestScorePlayerId + " has Won!");
        }

        return highestScorePlayerId; //TODO: doesn't account for ties
    }

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // if there is no state to send, ignore
        if (SHGS == null) {
            return;
        }
        // make a copy of the state; null out all cards except for the
        // top card in the middle deck
        SHState stateForPlayer = new SHState(SHGS); // copy of state

        // send the modified copy of the state to the player
        p.sendInfo(stateForPlayer);

        SHState playerGS = new SHState(SHGS);
        p.sendInfo(playerGS);
    }//sendUpdatedS

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        int haveLost = 0;
        String winnerName = "";
        for(Player i: SHGS.getPlayersArray()){
            if(i.getBalance() == 0){
                haveLost++;
            }
            else { winnerName = i.getName(); }
        }
        if(haveLost == SHGS.getPlayersArray().length - 1){
            return winnerName + " has won!";
        } else { return null;}
    }

}
