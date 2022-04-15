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
                                        "back_of_card"};
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
                        R.drawable.back_of_card};

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



        //Creates all 52 card objects and puts them into the deck arraylist
        for (char s : "SHDC".toCharArray()) {
            for (char r : "KQJT98765432A".toCharArray()) {
                this.deck.add(new Card(r, s));
            }
        }

        //Sets up game phases and their order
        int index = 0;
        this.phases[index] = "Betting-Phase 0";
        index++;
        for(int i = 0; i < 3; i++) {
            this.phases[index] = "Betting-Phase " + i;
            index++;
            this.phases[index] = "Draw-Phase " + i;
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

    //TODO: implement legalFold() method
    /**
     *
     *
     * @param player is the player that is attempting the move
     *
     * @return true if bet is a legal move for player and that is has been committed
     */
    public boolean legalFold(Player player) {
        //is it the players turn?
        if (getPlayerTurnId() == player.getTurnId()) {
            player.toggleFolded();
            //changes whose turn it is
            player.toggleIsTurn();
            changeFirstPlayer();
            getPlayersArray()[getPlayerTurnId()].toggleIsTurn();

            return true;
        }
        //if not all of the above is true
        return false;
    }

    /**
     * checks if it's players turn, it's the betting-phase, the betting value is
     *      at least minimum bet and not greater than the players balance and changes
     *
     * @param bet the value of
     * @param player is the player that is attempting the move
     *
     * @return true if bet is a legal move for player and that is has been committed
     */
    public boolean legalBet(int bet, Player player) {
        //is it currently the bet phase?
        if (getCurrentPhase().equals("Betting-Phase")) {
            //is it the players turn?
            if (getPlayerTurnId() == player.getTurnId()) {
                //is the players balance greater than or equal to the bet value?
                if (player.getBalance() >= getCurrentBet()) {
                    //is the bet value greater than or equal to current bet?
                    if (player.getLastBet() >= getCurrentBet()) {
                        //commits bet made
                        setCurrentBet(bet);
                        setPotBalance(getPotBalance() + bet);
                        player.setLastBet(bet);
                        //changes whose turn it is
                        player.toggleIsTurn();
                        changeFirstPlayer();
                        getPlayersArray()[getPlayerTurnId()].toggleIsTurn();

                        return true;
                    }
                }
            }
        }
        //if not all of the above is true
        return false;
    }

    /**
     * checks if it's players turn, it's the betting-phase, if player is not first bet,
     * if player has the money they are trying to bet, if bet is at least the minimum bet value
     *
     * @param bet
     * @param player is the player that is attempting the move
     *
     * @return true if draw is a legal move
     */
    public boolean legalRaise(int bet, Player player){
        //is it currently the bet phase?
        if(getCurrentPhase().equals("Betting-Phase")){
            //is it the players turn?
            if(getPlayerTurnId() == player.getTurnId()){
                //is the current bet greater than 0? i.e. a player can't raise if they're first bet.
                if(getCurrentBet() > 0){
                    //is the players balance greater than or equal to the bet value?
                    if(player.getBalance() >= bet){
                        //is the bet value greater than or equal to current bet plus minimum bet?
                        if(bet == getCurrentBet() + getMinimumBet()){
                            //commits the bet made
                            setCurrentBet(bet);
                            setPotBalance(getPotBalance() + bet);
                            player.setLastBet(bet);
                            player.setBalance(player.getBalance()-bet);
                            //changes whose turn it is
                            player.toggleIsTurn();
                            changeFirstPlayer();
                            getPlayersArray()[getPlayerTurnId()].toggleIsTurn();

                            return true;
                        }
                    }
                }
            }
        }
        //if not all of the above is true
        return false;
    }

    /**
     * Checks if it is players turn, if player is first bet and if it is currently the
     * betting-phase then changes whose turn it is without making a bet.
     *
     * @param player is the player that is attempting the move
     *
     * @return true if draw is a legal move
     */
    public boolean legalCheck(Player player){
        //is it currently the bet phase?
        if(getCurrentPhase().equals("Betting-Phase")){
            //is it the players turn?
            if(getPlayerTurnId() == player.getTurnId()){
                //is the current bet 0?
                if(getCurrentBet() == 0){
                    //changes whose turn it is
                    player.toggleIsTurn();
                    changeFirstPlayer();
                    getPlayersArray()[getPlayerTurnId()].toggleIsTurn();

                    return true;
                }
            }
        }
        //if not all of the above is true
        return false;
    }

    /**
     * checks if it is players turn, it's the betting-phase, if player is first bet,
     * and if player has the money they are trying to bet
     *
     * @param player is the player that is attempting the move
     *
     * @return true if draw is a legal move
     */
    public boolean legalCall(Player player){
        //vars to clean up code
        int callVal = getCurrentBet();
        //is it currently the bet phase?
        if(getCurrentPhase().equals("Betting-Phase")){
            //is it the players turn?
            if(getPlayerTurnId() == player.getTurnId()){
                //is the current bet greater than 0?
                if(getCurrentBet() > 0){
                    //is the current bet less than players current balance?
                    if(player.getBalance() >= callVal){
                        //commits the bet made
                        setPotBalance(getPotBalance() + callVal);
                        player.setLastBet(callVal);
                        player.setBalance(player.getBalance()-callVal);
                        //changes whose turn it is
                        player.toggleIsTurn();
                        changeFirstPlayer();
                        getPlayersArray()[getPlayerTurnId()].toggleIsTurn();

                        return true;
                    }
                }
            }
        }
        //if not all of the above is true
        return false;
    }

    /**
     * checks if it is players turn, it's the draw phase, and if cards have been selected to discard
     *
     * @param player is the player that is attempting the move
     *
     * @return true if draw is a legal
     */
    public boolean legalDraw(Player player){
        boolean cardsHaveBeenSelected = false;
        //is it currently the bet phase?
        if(getCurrentPhase().equals("Draw-Phase")){
            //is it the players turn?
            if(getPlayerTurnId() == player.getTurnId()){
                for(int i = 0; i < 4; i++){
                    if(player.getHand()[i].getIsSelected()){
                        cardsHaveBeenSelected = true;
                        player.getHand()[i] = null; //removes card from hand
                        player.getHand()[i] = draw(); //draws new card
                        player.getHand()[i].setIsDealt(true);
                    }
                }
                if(cardsHaveBeenSelected){
                    //changes whose turn it is
                    player.toggleIsTurn();
                    changeFirstPlayer();
                    getPlayersArray()[getPlayerTurnId()].toggleIsTurn();

                    return true;
                }
            }
        }
        //if not all of the above is true
        return false;
    }

    /**
     * checks if it is players turn, and if it is draw phase
     *
     * @param player is the player that is attempting the move i.e. pressed the "stand" button
     *
     * @return true if stand is a legal move
     */
    public boolean legalStand(Player player){
        //is it currently the bet phase?
        if(getCurrentPhase().equals("Draw-Phase")){
            //is it the players turn?
            if(getPlayerTurnId() == player.getTurnId()){
                return true;
            }
        }
        //if not all of the above is true
        return false;
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
        return this.deck.get(0);
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

    /** rotates between the player to be the first better */
    public void changeFirstPlayer(){
        this.playersTurnId++;
        if(this.playersTurnId == this.players.length){
            this.playersTurnId = 0;
        }
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
