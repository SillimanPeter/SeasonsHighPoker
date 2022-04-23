package edu.up.cs301.SeasonsHigh;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.R;

public class SHState extends GameState {

    private static final long serialVersionUID = 91L;

    /** Instantiate variables and lists, and declare initial values */
    private int potBalance;
    private int currentBet;
    private int lastBet;
    private int currentPhaseLocation;
    private int currentTurnId;
    private int minimumBet;
    private String currentPhase;
    private String[] phases;
    private Player[] players;
    private boolean[] hasMoved;
    private List<Card> deck;
    private String phaseMessage;
    private String actionMessage;
    private int winnerID;
    final private String[] cardRecName = {"card_2c","card_2d","card_2h","card_2s",
                                        "card_3c","card_3d","card_3h","card_3s",
                                        "card_4c","card_4d","card_4h","card_4s",
                                        "card_5c","card_5d","card_5h","card_5s",
                                        "card_6c","card_6d","card_6h","card_6s",
                                        "card_7c","card_7d","card_7h","card_7s",
                                        "card_8c","card_8d","card_8h","card_8s",
                                        "card_9c","card_9d","card_9h","card_9s",
                                        "card_ac","card_ad","card_ah","card_as",
                                        "card_jc","card_jd","card_jh","card_js",
                                        "card_kc","card_kd","card_kh","card_ks",
                                        "card_qc","card_qd","card_qh","card_qs",
                                        "card_tc","card_td","card_th","card_ts",
                                        "card_cb"};
    final private int[] cardRecIds = {
                        R.drawable.card_2c,R.drawable.card_2d,R.drawable.card_2h,R.drawable.card_2s,
                        R.drawable.card_3c,R.drawable.card_3d,R.drawable.card_3h,R.drawable.card_3s,
                        R.drawable.card_4c,R.drawable.card_4d,R.drawable.card_4h,R.drawable.card_4s,
                        R.drawable.card_5c,R.drawable.card_5d,R.drawable.card_5h,R.drawable.card_5s,
                        R.drawable.card_6c,R.drawable.card_6d,R.drawable.card_6h,R.drawable.card_6s,
                        R.drawable.card_7c,R.drawable.card_7d,R.drawable.card_7h,R.drawable.card_7s,
                        R.drawable.card_8c,R.drawable.card_8d,R.drawable.card_8h,R.drawable.card_8s,
                        R.drawable.card_9c,R.drawable.card_9d,R.drawable.card_9h,R.drawable.card_9s,
                        R.drawable.card_ac,R.drawable.card_ad,R.drawable.card_ah,R.drawable.card_as,
                        R.drawable.card_jc,R.drawable.card_jd,R.drawable.card_jh,R.drawable.card_js,
                        R.drawable.card_kc,R.drawable.card_kd,R.drawable.card_kh,R.drawable.card_ks,
                        R.drawable.card_qc,R.drawable.card_qd,R.drawable.card_qh,R.drawable.card_qs,
                        R.drawable.card_tc,R.drawable.card_td,R.drawable.card_th,R.drawable.card_ts,
                        R.drawable.card_cb};

    /**
     * constructor method declares variables and populates the arrays and lists
     */
    public SHState(){
        //declare default values for the variables
        this.potBalance = 0;
        this.currentBet = 5;
        this.minimumBet = 5; //$0 players are allowed to check
        this.currentTurnId = 0;

        this.lastBet = 0;
        this.phases = new String[9];
        this.deck = new ArrayList<Card>();
        this.players = new Player[3]; //3 player max
        this.hasMoved = new boolean[3];
        this.phaseMessage = "";
        this.actionMessage = "";
        this.winnerID = -1; //default

        this.players[0] = new Player("Player 0", 0);
        this.hasMoved[0] = false;
        this.players[1] = new Player("Player 1", 1);
        this.hasMoved[1] = false;
        this.players[2] = new Player("Player 2", 2);
        this.hasMoved[2] = false;

        this.players[1].setIsTurn(true);

        //Creates all 52 card objects and puts them into the deck arraylist
        for (char s : "shdc".toCharArray()) {
            for (char r : "kqjt98765432a".toCharArray()) {
                this.deck.add(new Card(r, s));
            }
        }

        //Sets up game phases and their order
        this.currentPhaseLocation = 0;
        int index = 0;
        this.phases[index] = "Betting-Phase";
        index++;
        for(index = 1; index < 7; index++) {
            if(index %2 != 0) {
                this.phases[index] = "Draw-Phase";
            }else {
                this.phases[index] = "Betting-Phase";
            }
        }
        this.phases[index] = "Reveal-Phase";
        index++;
        this.phases[index] = "Reset-Phase";
        //sets current phase
        this.currentPhase = this.phases[this.currentPhaseLocation];

        shuffleDeck();

    }

    //copy constructor
    public SHState(SHState orig){
        this.potBalance = orig.potBalance;
        this.currentBet = orig.currentBet;
        this.minimumBet = orig.minimumBet;
        this.currentTurnId = orig.currentTurnId;
        this.hasMoved = orig.hasMoved;
        this.phaseMessage = orig.phaseMessage;
        this.actionMessage = orig.actionMessage;
        this.lastBet = orig.lastBet;
        this.winnerID = orig.winnerID;

        //creates deep copy of the phases array
        this.currentPhaseLocation = orig.currentPhaseLocation;
        this.currentPhase = orig.currentPhase;
        this.phases = new String[orig.phases.length];
        for(int i = 0; i < orig.phases.length; i++){
            this.phases[i] = orig.phases[i];
        }
        //Log.d("SHState",this.currentPhase);

        //creates deep copy of the deck arrayList
        this.deck = new ArrayList<>();
        for(int h = 0; h < orig.deck.size(); h++){
            this.deck.add(new Card(orig.getDeckArray().get(h)));
        }

        //creates deep copy of the players array
        this.players = new Player[orig.players.length]; //3 player max
        for(int j = 0; j < orig.players.length; j++){
            this.players[j] = new Player(orig.players[j]);
        }

    }

    /**
     * finds the first card in the deck that has not been dealt
     *
     * @caviat deck must already be shuffled
     *
     * @return the card drawn
     */
    public Card draw(){
        for(Card i: this.deck){
            if(!i.getIsDealt()){
                i.setIsDealt(true);
                return i;
            }
        }
        /* this "should" never happen (i.e. this would happen if all cards in
         * the deck had been dealt before being shuffled which will not happen
         * due to the max possible cards drawn per round) */
        System.exit(305);
        return this.deck.get(-1);
    }

    /**
     * Changes the active game phase and restarts the series if at the end of the phases.
     * Also if all but one player has folded, skip to the Reset-Phase.
     */
    public void changeGamePhase(){
        this.currentPhaseLocation++;
        if(currentPhaseLocation == 1){
            shuffleDeck();
            dealCards();
        }else if(currentPhaseLocation >= phases.length-1){
            currentPhaseLocation = 8;
        }
        this.currentPhase = this.phases[this.currentPhaseLocation];
    }

    /**
     * External Citation
     * Date: 23 February 2022
     * Problem: Randomizing the order of the Card object array to imitate shuffling a deck
     * Resource: Margo Brown's big brain
     * Solution: Use an ArrayList for deck array and shuffle the list with Collections.shuffle()
     */
    public void shuffleDeck(){ Collections.shuffle(this.deck); }

    public void dealCards(){
        Random gen = new Random();
        int idx;
        for(int i = 0; i < players.length; i++){
            for(int j = 0; j <  players[i].getHand().length; j++){
                idx = gen.nextInt(deck.size());
                while(deck.get(idx).getIsDealt()) {
                    idx = gen.nextInt(deck.size());
                }
                players[i].hand[j] = deck.get(idx);
                deck.get(idx).setIsDealt(true);
            }
        }
    }

    public int getHandStrength(int playerId){
        Card[] cards = new Card[4];
        boolean seasoned = true;
        boolean fourKind = true;
        boolean straight = false;
        boolean threeKind = false;
        int pairNum = 0;
        int handNum = 0;
        int numPairs = 0;
        int score = 0;

        for(int c = 0; c < cards.length; c++){
            cards[c] = new Card(getPlayersArray()[playerId].getHand()[c]);
        }

        //find handType and if seasoned
        for(int pc = 0; pc < cards.length; pc++){
            for(int pc2 = 0; pc2 < cards.length; pc2++){
                if(pc < pc2){
                    if(cards[pc].getSuit() != cards[pc2].getSuit()){
                        seasoned = false;
                    }
                    if(cards[pc].getValue() == cards[pc2].getValue()){
                        if(pairNum == cards[pc].getValue()){
                            if(threeKind) {
                                threeKind = false;
                                fourKind = true;
                            } else {
                                threeKind = true;
                            }
                        }
                        numPairs++;
                        pairNum = cards[pc].getValue();
                    }
                }
            }
        }

        //add values relative to the hand to the player 0's handStrength
        if(seasoned){ score += 1000; } //seasoned

        if(fourKind){ score += 500; } //four of a kind
        else if(straight){ score += 400; } //straight
        else if(threeKind){ score += 300; } //three of a kind
        else if(numPairs == 2){ score += 200; } //two pair
        else if(numPairs == 1){ score += 100; } //single pair

        score += handNum; //handType rank TODO: calculate handNum

        return score;
    }

    /**
     * Finds out which player has the best hand and if there has been a tie
     *
     * @return ArrayList<Integer> of player's that have the highest, or tied the highest, hand score
     */
    public ArrayList<Integer> compareHands(){

        /** instantiate and declare helper variables */
        ArrayList<Integer> winnerIds = new ArrayList<Integer>();
        int highestScorePlayerId = -1;//id of player with highest score
        int highestScore = 0;
        int highestScoreTieId = -1;//id of 2nd player that has the highest score (for ties)
        int highestScoreTie = 0;//needed to ensure there has been a tie
        int highestScoreTie2Id = -1;//id of 3rd player that has the highest score (for 3-way ties)
        int highestScoreTie2 = 0;//needed to ensure there has been a 3-way tie
        int[] pHandScores = new int[3];
        for(int score: pHandScores){
            score = 0;
        }

        //get the handStrengths of each player
        for(int e = 0; e < pHandScores.length; e++){
            pHandScores[e] = getHandStrength(e);
        }

        //compare their handStrengths and find out who won
        for(int v = 0; v < pHandScores.length; v++){
            if(pHandScores[v] > highestScore && !getPlayersArray()[v].getFolded()){
                highestScore = pHandScores[v];
                highestScorePlayerId = v;
            } else if(pHandScores[v] == highestScore && !getPlayersArray()[v].getFolded()){
                if(highestScoreTieId != -1){
                    highestScoreTie2Id = v;
                } else {
                    highestScoreTieId = v;
                }
            }
        }
        //declare winner in debug log
        if(highestScore == highestScoreTie2){
            winnerIds.add(highestScorePlayerId);
            winnerIds.add(highestScoreTieId);
            winnerIds.add(highestScoreTie2Id);
            Log.d("The round is over", "Player " + highestScorePlayerId +
                    ", Player " + highestScoreTieId + " and Player " + highestScoreTie2Id + "have all Tied!");
        } else if(highestScore == highestScoreTie){
            winnerIds.add(highestScorePlayerId);
            winnerIds.add(highestScoreTieId);
            Log.d("The round is over", "Player " + highestScorePlayerId +
                    " and Player " + highestScoreTieId + "have Tied!");
        } else {
            winnerIds.add(highestScorePlayerId);
            Log.d("The round is over", "Player " + highestScorePlayerId + " has Won!");
        }

        return winnerIds;

    }

    /**
     * formats the GameState variables and arrays into a easily read paragraph
     *
     * @return String of ALL GameState info
     */
    @Override
    public String toString(){
        String gameState;
        String deckString = "Draw Pile Card List: ";
        String discardPileString = "Discard Pile Card List: ";
        String playerString = "";
        //append Cards' toString to discardPileString if has been dealt if not to discardPileString
        for(Card i: this.deck){
            if(i.getIsDealt()){
                discardPileString += "\n" + i.toString();
            } else {
                deckString += "\n" + i.toString();
            }
        }
        //append Player details to player string
        for(Player h: this.players){
            playerString += "\nPlayer Name: " + h.getName()
                    + "\nLast bet: " + h.getLastBet()
                    + "\nBalance: " + h.getBalance()
                    + "\nFolded: " + h.getFolded()
                    + "\n";
        }
        playerString += "\nIt is " + this.players[currentTurnId].getName() + "'s turn";

        gameState = "\nCurrent Phase: " + getCurrentPhase()
                + "\nPot Balance: " + getPotBalance()
                + "\nMinimum Bet: " + getMinimumBet()
                + "\nCurrent Bet: " + getCurrentBet()
                + "\nCurrent Players Turn: " + this.currentTurnId
                + "\n" //leave a gap for formatting
                + "\n" + playerString
                + "\n" + deckString
                + "\n" + discardPileString;
        return gameState;
    }

    public int getPCardRecId(int playerId, int handIndex){
        String recName = "card_" + players[playerId].getHand()[handIndex].toString();
        int recIndex = -1;
        int rec = -1;
        for(int i = 0; i < cardRecName.length; i++){
            if(cardRecName[i].equals(recName)){
                recIndex = i;
                rec = cardRecIds[recIndex];
                return rec;
            }
        }

        return rec;
    }

    //setter methods
    public void setCurrentBet(int value){ this.currentBet = value; }

    public void setPotBalance(int value){ this.potBalance = value; }

    public void setGamePhase(int idx){
        this.currentPhaseLocation = idx;
        this.currentPhase = phases[idx];
    }

    public void setWinnerID(int idx){
        this.winnerID = idx;
    }

    public void setCurrentTurnId(int initTurn){ this.currentTurnId = initTurn;}

    public void setHasMoved(int idx, boolean b){ this.hasMoved[idx] = b;}

    public void setPhaseMessage(String initMessage){
        this.phaseMessage = initMessage;
    }

    public void setLastBet(int val){this.lastBet = val;}

    public void setActionMessage(String initMessage){ this.actionMessage = initMessage;}
    //getter methods
    public String getPhaseMessage(){return this.phaseMessage;}
    public String getActionMessage(){return this.actionMessage;}

    public boolean getHasMoved(int idx){ return this.hasMoved[idx];}

    public boolean[] getHasMovedArray(){ return this.hasMoved;}

    public int getCurrentBet(){ return this.currentBet; }

    public int getPotBalance(){ return this.potBalance; }

    public int getWinnerID() { return winnerID;  }

    public int getLastBet(){return this.lastBet;}

    public int getMinimumBet(){ return this.minimumBet; }

    public int getPBal(int playerId){ return players[playerId].getBalance(); }

    public String getPName(int playerId){ return players[playerId].getName(); }

    public Player[] getPlayersArray(){ return this.players; }

    public List<Card> getDeckArray(){ return this.deck; }

    public String getCurrentPhase(){ return this.currentPhase; }

    public String[] getPhases(){ return this.phases; }

    public int getCurrentTurnId(){ return this.currentTurnId;}

    public int getCurrentPhaseLocation(){ return this.currentPhaseLocation; }

    public int getPlayerTurnId(){ return this.currentTurnId; }

}
