package edu.up.cs301.SeasonsHigh;

/**
 * TODO: comment class
 */
public class Player extends SHState {

    private static final long serialVersionUID = 96L;

    //initialize variables
    private Card[] hand;
    private String name;
    private int lastBet;
    private int balance;
    private int turnId;
    private boolean isTurn;
    private boolean folded;
    private SHState gameState;

    /**
     * Constructor declares default values for base variables
     * @param playerName will be in format "Player " + 'a number between 0 and 2'
     * @param playerGSCopy is a copy of the goldenGS but excludes enemy players' hands
     */
    public Player(String playerName, SHState playerGSCopy, int initTurnId){
        this.name = playerName;
        this.gameState = playerGSCopy;
        this.balance = 250; //$250k or $0.25m (initial value may change)
        this.isTurn = false;
        this.folded = false;
        for(Card i: this.hand){ i = null; } //sets all Card obj in player hand to null
        this.turnId = initTurnId;
        this.hand = new Card[4];
    }

    public Player(Player orig){
        this.name = orig.name;
        this.gameState = orig.gameState;
        this.balance = orig.balance;
        this.isTurn = orig.isTurn;
        this.folded = orig.folded;
        for(int i = 0; i < orig.hand.length; i++){
            this.hand[i] = new Card(orig.hand[i]);
        }
        this.turnId = orig.turnId;
    }

    public void bet(int newBet){
        this.lastBet = newBet;
        this.balance = this.balance - newBet;
    }

    //set methods
    public void updateGSCopy(SHState copy){ this.gameState = copy; }

    public void toggleIsTurn(){ this.isTurn =! this.isTurn; }

    public void toggleFolded(){ this.folded =! this.folded; }

    public void setLastBet(int bet){ this.lastBet = bet; }

    public void setBalance(int value){ this.balance = value; }

    //get methods
    public int getBalance(){ return this.balance; }

    public int getLastBet(){ return this.lastBet; }

    public boolean getIsTurn(){ return this.isTurn; }

    public boolean getFolded(){ return this.folded; }

    public String getName(){ return this.name; }

    public Card[] getHand(){ return this.hand; }

    public int getTurnId(){return this.turnId;}

}