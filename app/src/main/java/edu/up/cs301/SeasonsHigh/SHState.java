package edu.up.cs301.SeasonsHigh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.R;

public class SHState extends GameState {

    private static final long serialVersionUID = 91L;

    /** Instantiate variables and lists, and declare initial values */
    private int potBalance;
    private int currentBet;
    private int currentPhaseLocation;
    private int playersTurnId;
    private int minimumBet;
    private String currentPhase;
    private String[] phases;
    private Player[] players;
    private List<Card> deck;
    final private String[] cardRecName = {"card_2c","card_2d","card_2h","card_2s",
                                        "card_3c","card_3d","card_2h","card_2s",
                                        "card_4c","card_4d","card_2h","card_2s",
                                        "card_5c","card_5d","card_2h","card_2s",
                                        "card_6c","card_6d","card_2h","card_2s",
                                        "card_7c","card_7d","card_2h","card_2s",
                                        "card_8c","card_8d","card_2h","card_2s",
                                        "card_9c","card_9d","card_2h","card_2s",
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
        this.currentBet = 0;
        this.currentPhaseLocation = 0;
        this.minimumBet = 5; //$5k (this may change)
        this.playersTurnId = 0;
        this.phases = new String[9];
        this.deck = new ArrayList<Card>();
        this.players = new Player[3]; //3 player max

        this.players[0] = new Player("Player 0", 0);
        this.players[1] = new Player("Player 1", 1);
        this.players[2] = new Player("Player 2", 2);

        this.players[0].toggleIsTurn();

        //Creates all 52 card objects and puts them into the deck arraylist
        for (char s : "SHDC".toCharArray()) {
            for (char r : "KQJT98765432A".toCharArray()) {
                this.deck.add(new Card(r, s));
            }
        }

        //Sets up game phases and their order
        int index = 0;
        this.phases[index] = "Betting-Phase";
        index++;
        for(int i = 0; i < 3; i++) {
            this.phases[index] = "Betting-Phase";
            index++;
            this.phases[index] = "Draw-Phase";
            index++;
        }
        this.phases[index] = "Reveal-Phase";
        index++;
        this.phases[index] = "Reset-Phase";
        //sets current phase
        this.currentPhase = this.phases[this.currentPhaseLocation];

    }

    //copy constructor
    public SHState(SHState orig){
        this.potBalance = orig.potBalance;
        this.currentBet = orig.currentBet;
        this.currentPhaseLocation = orig.currentPhaseLocation;
        this.minimumBet = orig.minimumBet;
        this.currentPhase = orig.currentPhase;
        this.players = new Player[3]; //3 player max
        //creates deep copy of the phases array
        this.phases = new String[orig.phases.length];
        for(int i = 0; i < orig.phases.length; i++){
            this.phases[i] = orig.phases[i];
        }
        //creates deep copy of the deck arrayList
        this.deck = new ArrayList<>();
        for(int h = 0; h < this.deck.size(); h++){
            this.deck.add(new Card(orig.getDeckArray().get(h)));
        }

        //creates deep copy of the players array
        for(int j = 0; j < orig.players.length; j++){
            Player player = new Player(orig.players[j]);
            this.players[j] = player;
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
        int numPlayersFolded = 0;
        for(Player i: this.players){ //counts how many players have folded
            if(i.getFolded()){
                numPlayersFolded++;
            }
        }
        if(this.currentPhaseLocation + 1 == this.currentPhase.length()){
            this.currentPhaseLocation = 0;
        } else if(numPlayersFolded == this.players.length - 1){//checks last person standing situation
            this.currentPhaseLocation = this.currentPhase.length() - 1;//skips to Reset-Phase
        } else {
            this.currentPhaseLocation++;
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
        playerString += "\nIt is " + this.players[playersTurnId].getName() + "'s turn";

        gameState = "\nCurrent Phase: " + getCurrentPhase()
                + "\nPot Balance: " + getPotBalance()
                + "\nMinimum Bet: " + getMinimumBet()
                + "\nCurrent Bet: " + getCurrentBet()
                + "\nCurrent Players Turn: " + this.playersTurnId
                + "\n" //leave a gap for formatting
                + "\n" + playerString
                + "\n" + deckString
                + "\n" + discardPileString;
        return gameState;
    }

    public int getPCardRecId(int playerId, int handIndex){
        String recName = "card_" + players[playerId].getHand()[handIndex].toString();
        int recIndex = -1;
        for(int i = 0; i < cardRecName.length; i++){
            if(cardRecName[i].equals(recName)){
                recIndex = i;
            }
        }
        int rec = cardRecIds[recIndex];
        return rec;
    }

    public void addPlayer(Player p, int index){
        this.players[index] = p;
    }

    public int getPBal(int playerId){ return players[playerId].getBalance(); }

    public String getPName(int playerId){ return players[playerId].getName(); }

    public void setCurrentBet(int value){ this.currentBet = value; }

    public void setPotBalance(int value){ this.potBalance += value; }

    public int getCurrentBet(){ return this.currentBet; }

    public int getPotBalance(){ return this.potBalance; }

    public int getMinimumBet(){ return this.minimumBet; }

    public Player[] getPlayersArray(){ return this.players; }

    public List<Card> getDeckArray(){ return this.deck; }

    public String getCurrentPhase(){ return this.currentPhase; }

    public int getPlayerTurnId(){ return this.playersTurnId; }

}
