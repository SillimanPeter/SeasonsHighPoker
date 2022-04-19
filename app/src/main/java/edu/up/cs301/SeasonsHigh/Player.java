package edu.up.cs301.SeasonsHigh;

/**
 * TODO: comment class
 */
public class Player {

    private static final long serialVersionUID = 96L;

    //initialize variables
    public Card[] hand;
    private String name;
    private int lastBet;
    private int balance;
    private int turnId;
    private boolean isTurn;
    private boolean folded;
    private int currentBet;
    private boolean hasDrawnOrHeld;

    /**
     * Constructor declares default values for base variables
     * @param playerName will be in format "Player " + 'a number between 0 and 2'
     */
    public Player(String playerName, int initTurnId){
        this.name = playerName;
        this.balance = 250; //$250k or $0.25m (initial value may change)
        this.isTurn = false;
        this.folded = false;
        this.hasDrawnOrHeld = false;
        this.hand = new Card[4];
        for(int i = 0; i < hand.length; i++){
            hand[i] = new Card('c','b');
        } //sets all Card obj in player hand to card back
        this.turnId = initTurnId;
    }

    //copy constructor
    public Player(Player orig){
        this.name = orig.name;
        this.balance = orig.balance;
        this.isTurn = orig.isTurn;
        this.folded = orig.folded;
        this.turnId = orig.turnId;
        this.hasDrawnOrHeld = orig.hasDrawnOrHeld;
        this.hand = new Card[4];
        for(int i = 0; i < orig.hand.length; i++){
            this.hand[i] = new Card(orig.hand[i]);
        }
    }

    public void bet(int newBet){
        this.lastBet = newBet;
        this.balance -= newBet;
    }

    //set methods
    public void setIsTurn(boolean turn){ this.isTurn = turn; }

    public void setFolded(boolean fold){ this.folded = fold; }

    public void setLastBet(int bet){ this.lastBet = bet; }

    public void addBalance(int value){ this.balance += value; }

    public void setBalance(int value){ this.balance = value; }

    public void setCurrentBet(int value){ this.currentBet = value; }

    public void setHasDrawnOrHeld(boolean drawnOrHeld){ this.hasDrawnOrHeld = drawnOrHeld; }



    //get methods
    public boolean getHasDrawnOrHeld(){ return this.hasDrawnOrHeld; }

    public int getBalance(){ return this.balance; }

    public int getLastBet(){ return this.lastBet; }

    public boolean getIsTurn(){ return this.isTurn; }

    public boolean getFolded(){ return this.folded; }

    public String getName(){ return this.name; }

    public Card[] getHand(){ return this.hand; }

    public int getTurnId(){ return this.turnId; }

    public int getCurrentBet(){ return this.currentBet; }



}